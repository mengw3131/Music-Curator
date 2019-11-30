package com.curator.views;

import com.curator.controllers.*;
import com.curator.models.*;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AlbumPane {
    private Pane pane;

    public AlbumPane(AlbumSimple album, MainController mainController, NavbarController navbarController,
                     PlayerController playerController) {
        this(SpotifyTools.getAlbum(album.getAlbumID()), mainController, navbarController, playerController);
    }

    public AlbumPane(Album album, MainController mainController, NavbarController navbarController,
                     PlayerController playerController) {
        try {
            //=================================
            // GET CHILD NODES & ASSIGN CONTENT
            //=================================
            pane = new FXMLLoader(getClass().getResource("/views/album_pane.fxml")).load();
            ImageView albumImageView               = (ImageView) pane.getChildren().get(0);
            Label albumNameLabel                   = (Label)     pane.getChildren().get(1);
            Label albumArtistLabel                 = (Label)     pane.getChildren().get(2);
            ImageView playButtonImageView          = (ImageView) pane.getChildren().get(3);
            ImageView heartButtonImageView         = (ImageView) pane.getChildren().get(4);
            ImageView addToPlaylistButtonImageView = (ImageView) pane.getChildren().get(5);
            ImageView dislikeButtonImageView       = (ImageView) pane.getChildren().get(6);

            if (album.getImages().size() != 0){
                albumImageView.setImage(album.getImages().get(0));
            }
            albumNameLabel                         .setText(album.getName());

            //TODO FIX to artists names
            albumArtistLabel                       .setText(album.getArtists().get(0).getName());
            playButtonImageView                    .setImage(Icons.PLAY);
            heartButtonImageView                   .setImage(Icons.HEART);
            addToPlaylistButtonImageView           .setImage(Icons.COPY);
            dislikeButtonImageView                 .setImage(Icons.DISLIKE);


            //=================================
            // SET EVENTS HANDLER
            //=================================

            //Called when mouse enters the scrollPane
            pane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                albumImageView.setOpacity(0.2);

                setVisible(true, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView);

                setDisabled(false,playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView );

            });

            //Called when mouse exits the scrollPane
            pane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                albumImageView.setOpacity(1);
                setVisible(false, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView);

                setDisabled(true, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView );
            });

            //Called when mouse exits the scrollPane
            playButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Album a = SpotifyTools.getAlbum(album.getAlbumID());
                Track t = SpotifyTools.getTrack(a.getTracks().get(0).getTrackID());
                playerController.setCurrentTrack(t);
                event.consume();
            });


            heartButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    event -> DBTools.storeUserPreferenceAlbum(album.getAlbumID(), true));

            dislikeButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    event -> DBTools.storeUserPreferenceAlbum(album.getAlbumID(), false));

            albumNameLabel.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    event -> albumNameLabel.setStyle("-fx-underline: true"));

            albumNameLabel.addEventHandler(MouseEvent.MOUSE_EXITED,
                    event -> albumNameLabel.setStyle("-fx-underline: false"));

            albumNameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/album_page.fxml"));
                        try {
                            BorderPane borderPane = loader.load();
                            AlbumPageController albumPageController = loader.getController();
                            albumPageController.setControllers(mainController, navbarController, playerController);
                            albumPageController.setAlbum(album);

                            navbarController.addPage(borderPane);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            albumArtistLabel.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    event -> albumArtistLabel.setStyle("-fx-underline: true"));

            albumArtistLabel.addEventHandler(MouseEvent.MOUSE_EXITED,
                    event -> albumArtistLabel.setStyle("-fx-underline: false"));

            albumArtistLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                @Override
                public void handle(MouseEvent event) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/artist_page.fxml"));
                    try {
                        BorderPane borderPane = loader.load();
                        ArtistPageController artistPageController = loader.getController();
                        artistPageController.setControllers(mainController, navbarController, playerController);
                        artistPageController.setArtist(album.getArtists().get(0)); //only get first artist

                        navbarController.addPage(borderPane);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pane asPane() {
        return pane;
    }

    private void setVisible(boolean isVisible, Node ... nodes){
        for (Node n:nodes) {
            n.setOpacity(isVisible ? 1 : 0);
        }
    }
    private void setDisabled(boolean isDisabled, Node ... nodes){
        for (Node n:nodes) {
            n.setDisable(isDisabled);
        }
    }
}
