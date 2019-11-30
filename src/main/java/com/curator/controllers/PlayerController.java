package com.curator.controllers;

import com.curator.models.*;

import com.curator.views.Icons;
import javafx.concurrent.Task;
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
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

//TODO:
//Next, prev, shuffle functionality

/**
 * Controller to player.fxml
 */
public class PlayerController implements Initializable {
    private boolean isPlaying;
    private MediaPlayer mediaPlayer;
    private Track currentTrack;

    @FXML
    private HBox player;

    @FXML
    private Button playButton;

    @FXML
    private Slider songSlider;

    @FXML
    private Slider volumeSlider;

    @FXML
    private AnchorPane nowPlayingPane;

    @FXML
    private Label songNameLabel;

    @FXML
    private Label artistNameLabel;

    @FXML
    private ImageView songCoverImageView;

    @FXML
    private Label startDurationLabel;

    @FXML
    private Label endDurationLabel;


    /**
     * Triggered when Play button in the player is clicked
     *
     * @param event - Mouse click event
     */
    @FXML
    public void handlePlayButtonAction(ActionEvent event) {
        if (currentTrack != null) {
            isPlaying = !isPlaying;
            if (isPlaying) {
                setPlayButtonImage(new Image("/icons/other/pause.png"));
                mediaPlayer.play();
                setNowPlayingPane();
            } else {
                setPlayButtonImage(new Image("/icons/other/play.png"));
                mediaPlayer.pause();
            }
        }
    }

    /**
     * Set current track to be the new track
     *
     * @param track
     */
    public void setCurrentTrack(Track track) {
        this.isPlaying = false;
        if (this.currentTrack != null) {
            mediaPlayer.stop();
        }
        setNowPlayingPaneToLoading();
        currentTrack = track;

        //config media player in background thread
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                mediaPlayer = new MediaPlayer(currentTrack.getMedia());

                //when Media obj in mediaPlayer is ready
                mediaPlayer.setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        songSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                        startDurationLabel.setText("0:00");
                        endDurationLabel.setText(formatTime((int) mediaPlayer.getTotalDuration().toSeconds()));
                        initMediaPlayerProperty();
                        playButton.fire();
                    }
                });
                return null;
            }
        };
        new Thread(task).start();
    }

    /**
     * TODO: make long song/artist name automatically scroll right to left OR use ellipsis
     * <p>
     * Updates Now Playing pane with current information (on bottom right corner of the app)
     */
    public void setNowPlayingPane() {
        songCoverImageView.setImage(currentTrack.getImage());
        songNameLabel.setText(currentTrack.getTrackName());
        artistNameLabel.setText(currentTrack.getArtistsNames());
    }

    /**
     * Set Now Playing Pane to loading
     */
    public void setNowPlayingPaneToLoading() {
        songCoverImageView.setImage(Icons.MUSICAL_NOTE);
        songNameLabel.setText("Loading track...");
        artistNameLabel.setText("Please wait");
    }

    /**
     * Trigerred when the volume slider position is changed
     */
    @FXML
    public void changeVolume() {
        volumeSlider.valueProperty().addListener(
                (observable, oldVal, newVal) -> {
                    if (mediaPlayer != null){
                        mediaPlayer.setVolume(newVal.doubleValue());
                    }
                }
        );
    }

    /**
     * Change the play button image, e.g. play to pause
     *
     * @param image - new image
     */
    private void setPlayButtonImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        playButton.setGraphic(imageView);
    }

    /**
     * Initialize com.curator.controllers.PlayerController
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSliderProperty();
        initVolumeSliderProperty();
    }

    /**
     * Format duration to mm:ss format
     *
     * @param duration duration of track
     * @return formatted string
     */
    private String formatTime(int duration) {
        if (duration % 60 < 10) {
            return duration / 60 + ":0" + duration % 60;
        }
        return duration / 60 + ":" + duration % 60;
    }

    /**
     * Sets default songSlider behavior
     */
    public void initSliderProperty() {
        songSlider.setMin(0.0);
        songSlider.setValue(0.0);
        songSlider.setOnMousePressed(event -> mediaPlayer.seek(Duration.seconds(songSlider.getValue())));
        songSlider.setOnMouseReleased(event -> mediaPlayer.seek(Duration.seconds(songSlider.getValue())));
        songSlider.setOnMouseDragged(event -> mediaPlayer.seek(Duration.seconds(songSlider.getValue())));
    }

    /**
     * Sets default volumeSlider behavior
     */
    private void initVolumeSliderProperty() {
        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);
        volumeSlider.setValue(0.5); //set dummy, getting current OS volume is complicated
    }

    /**
     *  Sets default volumeSlider behavior
     */
    private void initMediaPlayerProperty() {
        //make the song slider val to listen to media player progress
        mediaPlayer.currentTimeProperty().addListener(((observable, oldValue, newValue) -> {
            songSlider.setValue(mediaPlayer.getCurrentTime().toSeconds());
            startDurationLabel.setText(formatTime((int) mediaPlayer.getCurrentTime().toSeconds()));
        }));

        //when music finishes
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.seconds(0));
            mediaPlayer.stop();
            setPlayButtonImage(new Image("/icons/other/play.png"));
        });
    }
}
