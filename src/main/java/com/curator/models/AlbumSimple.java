package com.curator.models;

import java.util.ArrayList;
import com.curator.tools.SpotifyTools;
import javafx.scene.image.Image;

/**
 * Simplified com.curator.models.Album object with no reference to tracks objects (to avoid circularity)
 */
public class AlbumSimple {

    private String albumID;                // The Spotify ID of the album
    private String name;                   // The name of the album
    private int popularity;                // popularity of the album (0-100) calculated with the the
    private String albumType;              // album, single, or compilation
    private ArrayList<Artist> artists;             // the list of artists on the album
    private String artistsString;    // the list of artists on the album
    private ArrayList<Image> images;

    public AlbumSimple(com.wrapper.spotify.model_objects.specification.Album sAlbum) {
        this.albumType = sAlbum.getAlbumType().getType();
        this.albumID = sAlbum.getId();
        this.name = sAlbum.getName();
        this.popularity = sAlbum.getPopularity();

        this.artists = SpotifyTools.toArtist(sAlbum.getArtists());
        this.artistsString = SpotifyTools.toString(this.artists);
        this.images = SpotifyTools.toImage(sAlbum.getImages());


    }

    public String getAlbumID() {
        return albumID;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getAlbumType() {
        return albumType;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public String getArtistsString() { return artistsString; }

    public ArrayList<Image> getImages() { return images; }

}
