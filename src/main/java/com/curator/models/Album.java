package com.curator.models;

import java.util.ArrayList;
import com.curator.tools.SpotifyTools;
import javafx.scene.image.Image;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 * This class stores the features of albums and allows the features to
 * be accessed by other classes.
 *
 */
public class Album {
	private ArrayList<Track> tracks; // the list of tracks on the album
	private ArrayList<Artist> artists;     // the list of artists on the album
	private String artistsNames;           // the list of artists' name on the album
	private ArrayList<Image> images;

	private int ranking = -1;

	private com.wrapper.spotify.model_objects.specification.Album sAlbum;    //wrapper's object
	/**
	 * Constructor for com.curator.models.Album object from wrapper's com.curator.models.Album object
	 * @param sAlbum wrapper's com.curator.models.Album object
	 */
	public Album(com.wrapper.spotify.model_objects.specification.Album sAlbum) {
	    this.sAlbum = sAlbum;
	}

	/**
	 * 
	 * @return albumType com.curator.models.Album, single, or compilation
	 */
	public String getAlbumType() {
		return sAlbum.getAlbumType().toString();
	}

	/**
	 * 
	 * @return albumID The Spotify ID of the album
	 */
	public String getAlbumID() {
		return sAlbum.getId();
	}

	/**
	 * 
	 * @return name The name of the album
	 */
	public String getName() {
		return sAlbum.getName();
	}

	/**
	 * 
	 * @return popularity The popularity of the album (0-100) calculated with
	 *         the popularity of the album's songs
	 */
	public int getPopularity() {
		return sAlbum.getPopularity();
	}

	/**
	 * 
	 * @return artists The list of artist objects who play on the album
	 */
	public ArrayList<Artist> getArtists() {
	    if (artists == null){
			this.artists = SpotifyTools.toArtists(sAlbum.getArtists());
		}
		return artists;
	}

	/**
	 *
	 * @return tracks The list of song objects on the album
	 */
	public ArrayList<Track> getTracks() {
		if (tracks == null){
			this.tracks = SpotifyTools.toTracks(sAlbum.getTracks().getItems());
		}
		return tracks;
	}

	/**
	 * @return arrayList of images of the album
	 */
	public ArrayList<Image> getImages() {
		if (images == null){
			this.images = SpotifyTools.toImage(sAlbum.getImages());
		}
		return images;
	}

	/**
	 * @return the names of the artists of the album
	 */
	public String getArtistsNames() {
		if (artistsNames == null){
			this.artistsNames = SpotifyTools.toArtistName(sAlbum.getArtists());
		}
		return artistsNames;
	}

	public int getRanking(){ return ranking; }

	public void setRanking(int ranking){ this.ranking = ranking; }


	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Album)){
			return false;
		}
		return this.getAlbumID().equals(((Album) obj).getAlbumID());

	}

	@Override
	public String toString() {
		return "Album " + getName() + " by " + getArtistsNames() + " with id " + getAlbumID();
	}
}
