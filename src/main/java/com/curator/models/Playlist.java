package com.curator.models;

import com.curator.views.Icons;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Playlist represents collections of Tracks.
 * Each playlist has a unique 8-digit ID. CRUD methods supported.
 */
public class Playlist {
    private String id = UUID.randomUUID().toString().substring(0,8);
    private ArrayList<Track> tracks = new ArrayList<>();
    private String name;
    private Image image;

    /**
     * Constructs an empty playlist
     * @param name name of the playlist
     */
    public Playlist(String name){
        this.name = name;
    }

    /**
     * Constructs a Playlist with a single track
     * @param name name of the playlist
     * @param track the track to be added in the playlist
     */
    public Playlist(String name,Track track){
        this.name = name;
        this.tracks.add(track);
    }

    /**
     * Constructs a Playlist with multiple tracks
     * @param name name of the playlist
     * @param tracks the tracks to be added in the playlist
     */
    public Playlist(String name, ArrayList<Track> tracks){
        this.name = name;
        this.tracks.addAll(tracks);
    }

    /**
     * Constructs a Playlist with multiple tracks and custom id
     * @param name
     * @param tracks
     * @param id
     */
    public Playlist(String name, ArrayList<Track> tracks, String id){
        this.id =  id;
        this.name = name;
        this.tracks.addAll(tracks);
    }

    /**
     * Get the image of the  the playlist.
     * @return the album cover of the first track in the playlist, or a musical note.
     */
    public Image getImage() {
        if (tracks.size() != 0){
            this.image = tracks.get(0).getAlbum().getImages().get(0);
        } else {
            this.image = Icons.MUSICAL_NOTE;
        }
        return image;
    }

    /**
     * Sets the image of the playlist
     * @param image the new image of the playlist
     */
    public void setImage(Image image){
        this.image = image;
    }

    /**
     * Get the tracks in the playlist
     * @return ArrayList of Tracks in the playlist
     */
    public ArrayList<Track> getTracks(){
        return tracks;
    }

    /**
     * Add a single track to the playlist
     * @param track track to be added
     */
    public void addTrack(Track track){
        tracks.add(track);
    }

    /**
     * Add multiple tracks to the playlist
     * @param tracks ArrayList of Track to be added
     */
    public void addTracks(ArrayList<Track> tracks){
        tracks.addAll(tracks);
    }

    /**
     * Remove a track from the playlist
     * @param track the track to be removed from the playlist
     */
    public void removeTrack(Track track){
        tracks.remove(track);
    }

    /**
     * Rename playlist
     * @param newName playlist's new name
     */
    public void rename(String newName){
        this.name = newName;
    }

    /**
     * Get the name of the playlist
     * @return the name of the playlist
     */
    public String getName() {
        return name;
    }

    /**
     * Get the ID of the playlist
     * @return the playlist's ID
     */
    public String getId() {
        return id;
    }
}
