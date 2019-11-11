package com.curator;

import java.util.ArrayList;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class stores the features of albums and allows the features to
 *         be accessed by other classes.
 *
 */
public class Album {
	String albumType;
	String albumID;
	String name;
	int popularity;
	ArrayList<Artist> artists;
	ArrayList<Song> tracks;

	// Constructor
	Album(Object spotifyAlbum) {
		this.albumType = spotifyAlbum.getAlbumType();
		this.albumID = spotifyAlbum.getId();
		this.name = spotifyAlbum.getName();
		this.popularity = spotifyAlbum.getPopularity();
		this.artists = spotifyAlbum.getArtists();
		this.tracks = spotifyAlbum.getTracks();
	}

	// Methods

	public String getAlbumType() {
		return albumType;
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

	public ArrayList<Artist> getArtists() {
		return artists;
	}

	public ArrayList<Song> getTracks() {
		return tracks;
	}

}
