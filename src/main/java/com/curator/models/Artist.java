package com.curator.models;

import com.curator.tools.SpotifyTools;
import javafx.scene.image.Image;

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

	private ArrayList<Image> images;

	/**
	 * Construct com.curator.models.Artist object from wrapper's com.curator.models.Artist object
	 * @param sArtist spotify wrapper's com.curator.models.Artist object
	 */
	public Artist(com.wrapper.spotify.model_objects.specification.Artist sArtist) {
		this.genres = new ArrayList<String>(Arrays.asList(sArtist.getGenres()));
		this.artistID = sArtist.getId();
		this.name = sArtist.getName();
		this.popularity = sArtist.getPopularity();
		this.images = SpotifyTools.toImage(sArtist.getImages());
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


	public ArrayList<Image> getImages() { return images; }
}
