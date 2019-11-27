package com.curator.models;

import com.curator.tools.DBTools;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.UUID;

public class Playlist {
    private ArrayList<Track> tracks = new ArrayList<>();

    private String name;
    private Image image;
    private String id = UUID.randomUUID().toString().substring(0,8);

    public Playlist(String name){
        this.name = name;
    }

    public Playlist(String name,Track track){
        this.name = name;
        this.tracks.add(track);
    }

    public Playlist(String name, ArrayList<Track> tracks){
        this.name = name;
        this.tracks.addAll(tracks);
    }

    public Playlist(String name, ArrayList<Track> tracks, String id){
        this.id =  id;
        this.name = name;
        this.tracks.addAll(tracks);
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

    public String getId() {
        return id;
    }
}
