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
	String albumType; // album, single, or compilation
	String albumID; // The Spotify ID of the album
	String name; // The name of the album
	int popularity; // popularity of the album (0-100) calculated with the the
					// popularity of the album's songs
	ArrayList<Artist> artists; // the list of artists on the album
	ArrayList<Song> tracks; // the list of tracks on the album

	// Constructor
	public Album(Object spotifyAlbum) {
		this.albumType = spotifyAlbum.getAlbumType();
		this.albumID = spotifyAlbum.getId();
		this.name = spotifyAlbum.getName();
		this.popularity = spotifyAlbum.getPopularity();
		this.artists = spotifyAlbum.getArtists();
		this.tracks = spotifyAlbum.getTracks();
	}

	// Methods

	/**
	 * 
	 * @return albumType Album, single, or compilation
	 */
	public String getAlbumType() {
		return albumType;
	}

	/**
	 * 
	 * @return albumID The Spotify ID of the album
	 */
	public String getAlbumID() {
		return albumID;
	}

	/**
	 * 
	 * @return name The name of the album
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return popularity The popularity of the album (0-100) calculated with
	 *         the popularity of the album's songs
	 */
	public int getPopularity() {
		return popularity;
	}

	/**
	 * 
	 * @return artists The list of artist objects who play on the album
	 */
	public ArrayList<Artist> getArtists() {
		return artists;
	}

	/**
	 * 
	 * @return tracks The list of song objects on the album
	 */
	public ArrayList<Song> getTracks() {
		return tracks;
	}

}
