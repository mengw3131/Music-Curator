package com.curator;

import java.util.ArrayList;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class stores the features of artists and allows the features to
 *         be accessed by other classes.
 *
 */
public class Artist {
	ArrayList<String> genres; // the list of genres of an artist
	String artistID; // the Spotify ID number for the artist
	String name; // name of the artist
	int popularity; // popularity of the artist (0-100) calculated from the
					// popularity of all the artist's songs

	// Constructor
	public Artist(Object spotifyArtist) {
		this.genre = spotifyArtist.getGenre();
		this.artistID = spotifyArtist.getId();
		this.name = spotifyArtist.getName();
		this.popularity = spotifyArtist.getPopularity();
	}

	// Methods

	/**
	 * 
	 * @return genres The list of the artist's genres
	 */
	public ArrayList<String> getGenres() {
		return genres;
	}

	/**
	 * 
	 * @return artistID The artist's Spotify ID
	 */
	public String getArtistID() {
		return artistID;
	}

	/**
	 * 
	 * @return name The name of the artist
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return popularity The popularity of the artist
	 */
	public int getPopularity() {
		return popularity;
	}

}
