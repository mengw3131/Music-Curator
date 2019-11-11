package com.curator;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class stores the features of artists and allows the features to
 *         be accessed by other classes.
 *
 */
public class Artist {
	String genres;
	String artistID;
	String name;
	int popularity;

	// Constructor
	Artist(Object spotifyArtist) {
		this.genre = spotifyArtist.getGenre();
		this.artistID = spotifyArtist.getId();
		this.name = spotifyArtist.getName();
		this.popularity = spotifyArtist.getPopularity();
	}

	// Methods

	public String getGenres() {
		return genres;
	}

	public String getArtistID() {
		return artistID;
	}

	public String getName() {
		return name;
	}

	public int getPopularity() {
		return popularity;
	}

}
