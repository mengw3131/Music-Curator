package com.curator.views;

import com.curator.controllers.*;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TrackPane {
    private Pane pane;
    private NavbarController navbarController;
    private MainController mainController;
    private PlayerController playerController;
    private ItemScrollPane parentContainer;
    private int childIndex;



    public TrackPane(Track track, MainController mainController, NavbarController navbarController,
                     PlayerController playerController,
                     ItemScrollPane parentContainer, int childIndex
    ) {
        try {
            this.mainController = mainController;
            this.navbarController = navbarController;
            this.playerController = playerController;
            this.parentContainer = parentContainer;
            this.childIndex = childIndex;

            //=================================
            // GET CHILD NODES & ASSIGN CONTENT
            //=================================
            pane = new FXMLLoader(getClass().getResource("/views/track_pane.fxml")).load();
            ImageView trackImageView = (ImageView) pane.getChildren().get(0);
            Label trackNameLabel = (Label) pane.getChildren().get(1);
            Label trackArtistLabel = (Label) pane.getChildren().get(2);
            ImageView playButtonImageView = (ImageView) pane.getChildren().get(3);
            ImageView heartButtonImageView = (ImageView) pane.getChildren().get(4);
            ImageView addToPlaylistButtonImageView = (ImageView) pane.getChildren().get(5);
            ImageView dislikeButtonImageView = (ImageView) pane.getChildren().get(6);

            pane.setId(childIndex + "");

            if (track.getImage() != null) {
                trackImageView.setImage(track.getImage());
            }
            trackNameLabel.setText(track.getTrackName());
            trackArtistLabel.setText(track.getArtistsNames());
            playButtonImageView.setImage(Icons.PLAY);
            heartButtonImageView.setImage(Icons.HEART);
            addToPlaylistButtonImageView.setImage(Icons.COPY);
            dislikeButtonImageView.setImage(Icons.DISLIKE);

            //=================================
            // SET EVENTS HANDLER
            //=================================

            //Called when mouse enters the scrollPane
            pane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                trackImageView.setOpacity(0.2);

                setVisible(true, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView);

                setDisabled(false, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView);
            });

            //Called when mouse exits the scrollPane
            pane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                trackImageView.setOpacity(1);

                setVisible(false, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView);

                setDisabled(true, playButtonImageView, heartButtonImageView,
                        addToPlaylistButtonImageView, dislikeButtonImageView);
            });

            //Called when mouse exits the scrollPane
            playButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                playerController.setCurrentTrack(track);
                event.consume();
            });

            addToPlaylistButtonImageView.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown()) {

                    ContextMenu contextMenu = new ContextMenu();
                    for (String playlist_id : DBTools.getAllPlaylistIDs()) {
                        MenuItem menuItem = new MenuItem(DBTools.getPlaylistName(playlist_id));
                        menuItem.setId(playlist_id);
                        menuItem.setOnAction(event2 -> {
                            DBTools.storeTrackToPlaylist(track.getTrackID(), playlist_id);
                        });
                        contextMenu.getItems().add(menuItem);
                    }
                    contextMenu.show(addToPlaylistButtonImageView, event.getScreenX(), event.getScreenY());
                }
            });

            heartButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    event -> DBTools.storeUserPreferenceTracks(track.getTrackID(), true));

            dislikeButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    event -> {
                        DBTools.storeUserPreferenceTracks(track.getTrackID(), false);

                        //TODO: GET REPLACEMENT FROM THE RECOMMENDER

                        Track t = SpotifyTools.searchTracks("hello world", 1).get(0);
                        TrackPane replacement = new TrackPane(t, mainController,
                                navbarController, playerController, parentContainer, childIndex);
                        replaceAtTheEnd(replacement);
                        deleteSelf();
                    });

            trackNameLabel.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    event -> trackNameLabel.setStyle("-fx-underline: true"));

            trackNameLabel.addEventHandler(MouseEvent.MOUSE_EXITED,
                    event -> trackNameLabel.setStyle("-fx-underline: false"));

            trackNameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/album_page.fxml"));
                        try {
                            BorderPane borderPane = loader.load();
                            AlbumPageController albumPageController = loader.getController();
                            albumPageController.setControllers(mainController, navbarController, playerController);
                            albumPageController.setAlbum(track.getAlbum());

                            navbarController.addPage(borderPane);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            trackArtistLabel.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    event -> trackArtistLabel.setStyle("-fx-underline: true"));

            trackArtistLabel.addEventHandler(MouseEvent.MOUSE_EXITED,
                    event -> trackArtistLabel.setStyle("-fx-underline: false"));

            trackArtistLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                @Override
                public void handle(MouseEvent event) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/artist_page.fxml"));
                    try {
                        BorderPane borderPane = loader.load();
                        ArtistPageController artistPageController = loader.getController();
                        artistPageController.setControllers(mainController, navbarController, playerController);
                        artistPageController.setArtist(track.getArtists().get(0)); //only get first artist

                        navbarController.addPage(borderPane);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            pane.idProperty().addListener((observable, oldValue, newValue) -> {
                setChildIndex(Integer.valueOf(newValue));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    /**
     * Replace this trackPane with another trackPane at the end
     * @param trackPane new TrackPane to be set
     */
    private void replaceAtTheEnd(TrackPane trackPane) {
        HBox hBox = ((HBox)(parentContainer.getContent()));
        hBox.getChildren().add(trackPane.asPane());
    }

    /**
     * Delete this pane
     */
    private void deleteSelf(){
        HBox hBox = ((HBox)(parentContainer.getContent()));
        hBox.getChildren().remove(this.asPane());

        //update the indices of the other trackpanes in the same container
        ObservableList<Node> children = hBox.getChildren();
        for (int i = 0; i < children.size(); i++) {
            children.get(i).setId(i + "");
        }
    }

    /**
     * Return the Pane object of the track
     * @return
     */
    public Pane asPane() {
        return pane;
    }

    /**
     * Set nodes visibility
     * @param isVisible whether set to be visible or not
     * @param nodes the nodes to be set
     */
    private void setVisible(boolean isVisible, Node... nodes) {
        for (Node n : nodes) {
            n.setOpacity(isVisible ? 1 : 0);
        }
    }

    /**
     * Set nodes disabled property
     * @param isDisabled whether set to be disabled or not
     * @param nodes the nodes to be set
     */
    private void setDisabled(boolean isDisabled, Node... nodes) {
        for (Node n : nodes) {
            n.setDisable(isDisabled);
        }
    }
}
