package com.curator.models;

import java.util.ArrayList;
import com.curator.tools.SpotifyTools;
import javafx.scene.image.Image;

/**
 * Simplified com.curator.models.Album object with no reference to tracks objects (to avoid circularity)
 */
public class AlbumSimple {
    private ArrayList<Artist> artists;
    private String artistsNames;
    private ArrayList<Image> images;

    private com.wrapper.spotify.model_objects.specification.Album sAlbum;  //wrapper's object

    /**
     * Constructor for AlbumSimple object from wrapper's com.curator.models.Album object
     * @param sAlbum wrapper's com.curator.models.Album object
     */
    public AlbumSimple(com.wrapper.spotify.model_objects.specification.Album sAlbum) {
        this.sAlbum = sAlbum;
    }

    /**
     * Get Spotify's Album ID of the this object
     * @return Spotify's Album ID of the this object
     */
    public String getAlbumID() {
        return sAlbum.getId();
    }

    /**
     * Get Album's name
     * @return album's name
     */
    public String getName() {
        return sAlbum.getName();
    }

    /**
     * Get Album's popularity
     * @return Album's popularity
     */
    public int getPopularity() {
        return sAlbum.getPopularity();
    }

    /**
     * Get Album's type
     * @return Album's type
     */
    public String getAlbumType() {
        return sAlbum.getAlbumType().toString();
    }

    /**
     * Get the artists of the album
     * @return ArrayList of Artist object of the Album
     */
    public ArrayList<Artist> getArtists() {
        if (this.artists == null) {
            this.artists = SpotifyTools.toArtist(sAlbum.getArtists());
        }
        return this.artists;
    }

    /**
     * Get the names of the artists of the album
     * @return names of the artists of the album, separated by comma
     */
    public String getArtistsNames() {
        if (artistsNames == null) {
            this.artistsNames = SpotifyTools.toString(this.artists);
        }
        return artistsNames;
    }

    /**
     * Get album's cover images
     * @return ArrayList of Image object of the Album
     */
    public ArrayList<Image> getImages() {
        if (this.images == null){
            this.images = SpotifyTools.toImage(sAlbum.getImages());
        }
        return images;
    }
}
