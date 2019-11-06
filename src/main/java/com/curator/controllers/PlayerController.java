package com.curator.controllers;

import com.curator.object_models.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML
    HBox player;

    @FXML
    Button playButton;

    @FXML
    Slider songSlider;

    @FXML
    Slider volumeSlider;

    @FXML
    AnchorPane nowPlayingPane;

    @FXML
    Label songNameLabel;

    @FXML
    Label artistNameLabel;

    @FXML
    ImageView songCoverImageView;

    @FXML
    Label startDurationLabel;

    @FXML
    Label endDurationLabel;


    Song currentSong;
    boolean playState;
    MainController mainController;

    @FXML
    public void handlePlayButtonAction(ActionEvent event) {
        if (currentSong != null) {
            playState = !playState;

            if (playState) {
                Image pause = new Image(getClass().getResourceAsStream("/icons/other/pause.png"));
                ImageView imageView = new ImageView(pause);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                playButton.setGraphic(imageView);

                currentSong.getSound().play();
                setNowPlayingPane();
            } else {
                Image play = new Image(getClass().getResourceAsStream("/icons/other/play.png"));
                ImageView imageView = new ImageView(play);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                playButton.setGraphic(imageView);

                currentSong.getSound().pause();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);
        volumeSlider.setValue(0.5); //set starting point
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void setCurrentSong(Song song) {
        this.playState = false;

        if (this.currentSong != null) {
            this.currentSong.getSound().stop();
            this.currentSong = song;
            this.playButton.fire();
        } else {
            this.currentSong = song;
            this.playButton.fire();
        }

        this.endDurationLabel.setText(
                currentSong.getLength() / 60 + ":" + currentSong.getLength() % 60);

        setNowPlayingPane();
        System.out.println(song.getLength());
    }

    public void setNowPlayingPane() {
        songCoverImageView.setImage(currentSong.getCover());

        songNameLabel.setText(currentSong.getName());
        artistNameLabel.setText(currentSong.getArtist());
    }

    @FXML
    public void changeVolume() {
        volumeSlider.valueProperty().addListener(
                (observable, oldVal, newVal) -> {
                    currentSong.getSound().setVolume(newVal.floatValue());
                }
        );
    }
}
