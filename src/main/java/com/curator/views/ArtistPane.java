package com.curator.views;

import com.curator.controllers.*;
import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ArtistPane {
    private Pane pane;

    public ArtistPane(Artist artist, MainController mainController, NavbarController navbarController,
                     PlayerController playerController) {
        try {
            //=================================
            // GET CHILD NODES & ASSIGN CONTENT
            //=================================
            pane = new FXMLLoader(getClass().getResource("/views/artist_pane.fxml")).load();
            ImageView artistImageView              = (ImageView) pane.getChildren().get(0);
            Label artistNameLabel                  = (Label)     pane.getChildren().get(1);
            ImageView playButtonImageView          = (ImageView) pane.getChildren().get(3);
            ImageView heartButtonImageView         = (ImageView) pane.getChildren().get(4);
            ImageView addToPlaylistButtonImageView = (ImageView) pane.getChildren().get(5);
            ImageView dislikeButtonImageView       = (ImageView) pane.getChildren().get(6);

            if (artist.getImages().size() != 0){
                artistImageView.setImage(artist.getImages().get(0));
            }

            artistNameLabel                        .setText(artist.getName());
            playButtonImageView                    .setImage(Icons.PLAY);
            heartButtonImageView                   .setImage(Icons.HEART);
            addToPlaylistButtonImageView           .setImage(Icons.COPY);
            dislikeButtonImageView                 .setImage(Icons.DISLIKE);


            //=================================
            // SET EVENTS HANDLER
            //=================================

            //Called when mouse enters the scrollPane
            pane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                artistImageView.setOpacity(0.2);

                setVisible(true, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView);

                setDisabled(false,playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView );

            });

            //Called when mouse exits the scrollPane
            pane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                artistImageView.setOpacity(1);

                setVisible(false, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView);

                setDisabled(true, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView );
            });

            //Called when mouse exits the scrollPane
            playButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Track t = SpotifyTools.getTrack(artist.getTracks().get(0).getTrackID());
                playerController.setCurrentTrack(t);
                event.consume();
            });


            heartButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    event -> DBTools.storeUserPreferenceArtist(artist.getArtistID(), true));

            dislikeButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    event -> DBTools.storeUserPreferenceArtist(artist.getArtistID(), false));

            artistNameLabel.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    event -> artistNameLabel.setStyle("-fx-underline: true"));

            artistNameLabel.addEventHandler(MouseEvent.MOUSE_EXITED,
                    event -> artistNameLabel.setStyle("-fx-underline: false"));

            artistNameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/artist_page.fxml"));
                        try {
                            BorderPane borderPane = loader.load();
                            ArtistPageController artistPageController = loader.getController();
                            artistPageController.setControllers(mainController, navbarController, playerController);
                            artistPageController.setArtist(artist);

                            navbarController.addPage(borderPane);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
