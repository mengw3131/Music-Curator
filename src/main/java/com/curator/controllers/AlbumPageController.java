package com.curator.controllers;

import com.curator.models.Album;
import com.curator.models.AlbumSimple;
import com.curator.models.TrackSimple;
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

/**
 * Controller to album_page.fxml. Shows album and its tracks.
 */
public class AlbumPageController implements Initializable {
    private PlayerController playerController;
    private MainController mainController;
    private Album album;

    @FXML
    private VBox mainVBox;

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private ImageView albumImage;

    @FXML
    private Label albumName;

    @FXML
    private Label artistsNames;

    /**
     * Fetch and display tracks of the album
     * @param tracks tracks to be displayed
     */
    private void createTrackList(ArrayList<TrackSimple> tracks){
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
        for (TrackSimple track: tracks) {
            try {
                hbox = new FXMLLoader(getClass().getResource("/views/tracks_row.fxml")).load();

                ((Label)((AnchorPane)hbox.getChildren().get(0)).getChildren().get(0)).setText(track.getTrackName()); //set track name
                AnchorPane buttonsPane = (AnchorPane)(hbox.getChildren().get(2));
                ImageView playButton = (ImageView) buttonsPane.getChildren().get(0);
                ImageView heartButton = (ImageView) buttonsPane.getChildren().get(1);
                ImageView playlistButton = (ImageView) buttonsPane.getChildren().get(2);

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
                        if (event.getButton().equals(MouseButton.PRIMARY)){
                            if (event.getClickCount() == 2){
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

                //if click on heart icon,
                heartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        //TODO: IMPLEMENT
                        System.out.println("heart clicked");

                    }
                });

                //if click on playlist icon,
                playlistButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        //TODO: IMPLEMENT
                        System.out.println("playlist clicked");
                    }
                });

                //add to container
                mainVBox.getChildren().add(hbox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //add to container & bind display config
        mainController.mainPane.getChildren().add(topBorderPane);
        topBorderPane.prefHeightProperty().bind(mainController.mainPane.heightProperty());
        topBorderPane.prefWidthProperty().bind(mainController.mainPane.widthProperty());
        mainVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
    }


    /**
     * Initialize controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Convert AlbumSimple to Album(full), then display them on page
     * @param albumSimple album to be displayed on page
     */
    public void setAlbum(AlbumSimple albumSimple) {
        this.album = SpotifyTools.getAlbum(albumSimple.getAlbumID());
        setAlbum(this.album);
    }

    /**
     * Set Album (full) to be displayed on page
     * @param album album to be displayed on page
     */
    public void setAlbum(Album album) {
        this.album = album;
        albumImage.setImage(album.getImages().get(0));
        artistsNames.setText(album.getArtistsNames());
        albumName.setText(album.getName());
        createTrackList(this.album.getTracks());
    }

    /**
     * Injects mainController
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Injects playerController
     * @param playerController
     */
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
}
