package com.curator.object_models;


import javafx.scene.image.Image;

//TODO: deprecate in favor of Track
//TODO: else, use builder pattern

/**
 * Song object with attributes. Song has reference to its audio file through Sound object.
 *
 */
public class Song {

    //attributes
    private String name;
    private String time;
    private String artist;
    private String album;
    private String genre;
    private int length;

    private Sound sound;
    private String filename;
    private Image cover;

    /**
     * Song object constructor
     * @param filename
     * @param name
     * @param time
     * @param artist
     * @param album
     * @param genre
     */
    public Song(String filename, String name, String time, String artist, String album, String genre){
        this.filename = filename;
        this.cover = new Image(getClass().getResourceAsStream("/icons/musical-note.png"));
        this.sound = new Sound(filename);

        this.name = name;
        this.time = time;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.length = sound.length();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFilename() { return filename; }

    public Sound getSound() { return sound; }

    public Image getCover() { return cover; }

    public int getLength() { return length; }
}
