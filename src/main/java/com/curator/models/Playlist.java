package com.curator.models;

import com.curator.tools.SpotifyTools;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Playlist {
    private ArrayList<Track> tracks = new ArrayList<>();


    private String name;
    private Image image;

    public Playlist(String name){
        this.name = name;
    }

    public Playlist(String name, Track track){
        this.name = name;
        tracks.add(track);
    }

    public Playlist(String name, ArrayList<Track> tracks){
        this.name = name;
        tracks.addAll(tracks);
    }

    public Image getImage() {
        if (tracks.size() != 0){
            this.image = tracks.get(0).getAlbum().getImages().get(0);
        } else {
            this.image = new Image("/icons/musical-note.png");
        }
        return image;
    }

    public void setImage(Image image){
        this.image = image;

    }

    public ArrayList<Track> getTracks(){
        return tracks;
    }

    public void addTrack(Track track){
        tracks.add(track);
    }

    public void addTracks(ArrayList<Track> tracks){
        tracks.addAll(tracks);
    }

    public void removeTrack(Track track){
        tracks.remove(track);
    }

    public void rename(String newName){
        this.name = newName;
    }

    public String getName() {
        return name;
    }
}
