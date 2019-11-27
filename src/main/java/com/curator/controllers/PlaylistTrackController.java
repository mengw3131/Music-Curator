package com.curator.controllers;

import com.curator.models.Playlist;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.curator.models.*;

public class PlaylistTrackController implements Initializable {
    private PlayerController playerController;
    private MainController mainController;
    private Playlist playlist;

    @FXML
    private VBox mainVBox;

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private ImageView playlistImage;

    @FXML
    private Label playlistName;

    /**
     * Fetch and display tracks of the playlist
     *
     * @param tracks tracks to be displayed
     */
    private void createTrackList(ArrayList<Track> tracks) {
        System.out.println("inside create track list");
        boolean flag = false;
        /*
          Nodes hierarchy

          HBox
           AnchorPane
             Label (track name)
           AnchorPane
           AnchorPane
             ImageView (play)
             ImageView (heart)
             ImageView (add to playlist)
         */
        HBox hbox;
        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            try {
                hbox = new FXMLLoader(getClass().getResource("/views/playlist_tracks_row.fxml")).load();

                ((Label) ((AnchorPane) hbox.getChildren().get(0)).getChildren().get(0)).setText(track.getTrackName()); //set track name
                AnchorPane buttonsPane = (AnchorPane) (hbox.getChildren().get(2));
                ImageView playButton = (ImageView) buttonsPane.getChildren().get(0);
                ImageView deleteButton = (ImageView) buttonsPane.getChildren().get(1);
                ImageView dotsButton = (ImageView) buttonsPane.getChildren().get(2);

                //initially hide and disable the action buttons (play, heart, add to playlist)
                buttonsPane.setOpacity(0);
                buttonsPane.setDisable(true);

                //alternate the background color of the track row using flag
                // white + light grey
                if (flag) {
                    hbox.setStyle("-fx-background-color: #e8e8e8");
                }
                flag = !flag;


                //when mouse enter the hbox, show action icons
                hbox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        buttonsPane.setOpacity(1);
                        buttonsPane.setDisable(false);
                    }
                });

                //when mouse exit the hbox
                hbox.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        buttonsPane.setOpacity(0);
                        buttonsPane.setDisable(true);
                    }
                });

                //if double click on track, play music
                hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton().equals(MouseButton.PRIMARY)) {
                            if (event.getClickCount() == 2) {
                                playerController.setCurrentTrack(SpotifyTools.getTrack(track.getTrackID()));
                            }
                        }
                    }
                });

                //if click on play icon, play music
                playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        playerController.setCurrentTrack(SpotifyTools.getTrack(track.getTrackID()));
                    }
                });
                final int x = i;

                //if click on heart icon,
                deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        delete(track);
                        System.out.println("delete clicked");

                    }
                });

                //if click on playlist icon,
                dotsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        //TODO: IMPLEMENT
                        System.out.println("dots clicked");
                    }
                });

                //add to container
                mainVBox.getChildren().add(hbox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Track track){
//        playlist.removeTrack(track);
        DBTools.removeTrackFromPlaylist(track, playlist);
        playlist = DBTools.getPlaylist(playlist.getId());

        //offset first two is image + playlist name
        mainVBox.getChildren().remove(2, 2 + playlist.getTracks().size() + 1);

        createTrackList(playlist.getTracks());
    }


    /**
     * Initialize controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
    }

    /**
     * Set Playlist (full) to be displayed on page
     *
     * @param playlist playlist to be displayed on page
     */
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        playlistImage.setImage(playlist.getImage());
        playlistName.setText(playlist.getName());
        createTrackList(this.playlist.getTracks());
    }

    /**
     * Injects mainController
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        //add to container & bind display config
        mainController.mainPane.getChildren().add(topBorderPane);
        topBorderPane.prefHeightProperty().bind(mainController.mainPane.heightProperty());
        topBorderPane.prefWidthProperty().bind(mainController.mainPane.widthProperty());
    }

    /**
     * Injects playerController
     *
     * @param playerController
     */
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
}
