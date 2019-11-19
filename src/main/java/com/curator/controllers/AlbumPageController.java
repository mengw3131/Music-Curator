package com.curator.controllers;

import com.curator.models.Album;
import com.curator.models.AlbumSimple;
import com.curator.models.Track;
import com.curator.models.TrackSimple;
import com.curator.tools.SpotifyTools;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
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

public class AlbumPageController implements Initializable {
    PlayerController playerController;
    MainController mainController;
    Album album;

    @FXML
    VBox mainVBox;

    @FXML
    BorderPane topBorderPane;

    @FXML
    ScrollPane mainScrollPane;

    @FXML
    ImageView albumImage;

    @FXML
    Label albumName;

    @FXML
    Label artistsNames;

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
                hbox = new FXMLLoader(getClass().getResource("/views/test.fxml")).load();
                //set track name
                ((Label)((AnchorPane)hbox.getChildren().get(0)).getChildren().get(0)).setText(track.getTrackName());
                AnchorPane buttonsPane = (AnchorPane)(hbox.getChildren().get(2));
                ImageView playButton = (ImageView) buttonsPane.getChildren().get(0);
                ImageView heartButton = (ImageView) buttonsPane.getChildren().get(1);
                ImageView playlistButton = (ImageView) buttonsPane.getChildren().get(2);

                buttonsPane.setOpacity(0);
                buttonsPane.setDisable(true);

                if (flag){
                    hbox.setStyle("-fx-background-color: #e8e8e8");
                    flag = !flag;
                } else {
                    flag = !flag;
                }

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
                        System.out.println("heart clicked");

                    }
                });

                //if click on playlist icon,
                playlistButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("playlist clicked");
                    }
                });

                mainVBox.getChildren().add(hbox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mainController.hideAllMainPane();
        mainController.mainPane.getChildren().add(topBorderPane);
        topBorderPane.prefHeightProperty().bind(mainController.mainPane.heightProperty());
        topBorderPane.prefWidthProperty().bind(mainController.mainPane.widthProperty());
        mainVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setAlbum(AlbumSimple album) {
        this.album = SpotifyTools.getAlbum(album.getAlbumID());
        setAlbum(this.album);
    }

    public void setAlbum(Album album) {
        this.album = album;
        albumImage.setImage(album.getImages().get(0));
        artistsNames.setText(album.getArtistsString());
        albumName.setText(album.getName());
        createTrackList(this.album.getTracks());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
//        this.mainController.mainPane.getChildren().clear();
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
}
