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

//TODO:
//Current track playing position slider
//Fix duration e.g. 6:7 vs 6:07
//Next, prev, shuffle functionality
//Volume slider starting point
//When music finishes playing, change play button image back

/**
 * Controller to player.fxml
 */
public class PlayerController implements Initializable {
    Song currentSong;
    boolean isPlaying;
    MainController mainController;

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

    /**
     * Triggered when Play button in the player is clicked
     *
     * @param event - Mouse click event
     */
    @FXML
    public void handlePlayButtonAction(ActionEvent event) {
        if (currentSong != null) {
            isPlaying = !isPlaying;

            if (isPlaying) {
                setPlayButtonImage(new Image("/icons/other/pause.png"));
                currentSong.getSound().play();
                setNowPlayingPane();
            } else {
                setPlayButtonImage(new Image("/icons/other/play.png"));
                currentSong.getSound().pause();
            }
        }
    }

    /**
     * Initialize PlayerController
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //TODO: get system volume level at start instead of using dummy number
        //volume slider config
        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);
        volumeSlider.setValue(0.5); //set starting point
    }


    /**
     * Inject parent/container controller
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * TODO: change params to wrapper.Track instead of Song object
     * TODO: double check the logic + corner cases
     *
     * Set current song to be the new song
     * @param song
     */
    public void setCurrentSong(Song song) {
        this.isPlaying = false;

        //stop previous song
        if (this.currentSong != null) { this.currentSong.getSound().stop(); }

        this.currentSong = song;
        this.playButton.fire(); //play music

        this.endDurationLabel.setText( currentSong.getLength() / 60 + ":" + currentSong.getLength() % 60);

        setNowPlayingPane();
    }

    /**
     * TODO: make long song/artist name automatically scroll right to left OR use ellipsis
     *
     * Updates Now Playing pane with current information (on bottom right corner of the app)
     *
     */
    public void setNowPlayingPane() {
        songCoverImageView.setImage(currentSong.getCover());
        songNameLabel.setText(currentSong.getName());
        artistNameLabel.setText(currentSong.getArtist());
    }

    /**
     * TODO: testing + smoothing
     *
     * Trigerred when the volume slider position is changed
     */
    @FXML
    public void changeVolume() {
        volumeSlider.valueProperty().addListener(
                (observable, oldVal, newVal) -> {
                    currentSong.getSound().setVolume(newVal.floatValue());
                }
        );
    }


    /**
     * Change the play button image, e.g. play to pause
     *
     * @param image - new image
     */
    private void setPlayButtonImage(Image image) {
        //change play button to image in filename
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        playButton.setGraphic(imageView);
    }
}
