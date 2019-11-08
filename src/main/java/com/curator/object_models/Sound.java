package com.curator.object_models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * Interface class for audio file.
 *
 */
public class Sound {
    private Media media;
    private MediaPlayer mediaPlayer;
    private String path;

    /**
     * Constructor to Sound object
     * @param path
     */
    public Sound(String path){
        this.path = path;
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    /**
     * Play audio file
     */
    public void play(){
        mediaPlayer.play();
    }

    /**
     * Stop audio file
     */
    public void stop(){
        mediaPlayer.stop();
    }

    /**
     * Pause audio file
     */
    public void pause(){
        mediaPlayer.pause();
    }

    /**
     * Return length of audio file in seconds
     * @return length of audio file in seconds
     */
    public int length(){
        return (int) mediaPlayer.getTotalDuration().toSeconds();
    }




    //TODO: fix volume slider in player + check logic/math
    /**
     * Set volume to be input volume. Volume range 0.0 - 1.0
     * @param volume
     */
    public void setVolume(float volume){
        mediaPlayer.setVolume(volume);
    }

    /**
     * Return current volume, range 0.0 - 1.0
     * @return current volume
     */
    public float getVolume(){
        return (float) mediaPlayer.getVolume();
    }

    /**
     * Return audio file path associated with Sound object
     * @return audio file path
     */
    public String getPath() {
        return path;
    }
}
