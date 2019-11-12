package com.curator.models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Luke, Isaac, Meng
 * 
 * This class stores the features of artists and allows the features to
 * be accessed by other classes.
 */
public class Artist {
	private ArrayList<String> genres;      // the list of genres of an artist
	private String artistID;               // the Spotify ID number for the artist
	private String name;                   // name of the artist
	private int popularity;                // popularity of the artist (0-100) calculated from the
					                       // popularity of all the artist's songs

	/**
	 * Construct Artist object from wrapper's Artist object
	 * @param sArtist spotify wrapper's Artist object
	 */
	public Artist(com.wrapper.spotify.model_objects.specification.Artist sArtist) {
		this.genres = new ArrayList<String>(Arrays.asList(sArtist.getGenres()));
		this.artistID = sArtist.getId();
		this.name = sArtist.getName();
		this.popularity = sArtist.getPopularity();
	}

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
