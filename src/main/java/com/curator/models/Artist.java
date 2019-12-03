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
	private ArrayList<String> genres;
	private ArrayList<Image> images;
	private ArrayList<Track> tracks;
	private com.wrapper.spotify.model_objects.specification.Artist sArtist;

	private int ranking;

	/**
	 * Construct com.curator.models.Artist object from wrapper's com.curator.models.Artist object
	 * @param sArtist spotify wrapper's com.curator.models.Artist object
	 */
	public Artist(com.wrapper.spotify.model_objects.specification.Artist sArtist) {
		this.sArtist = sArtist;
	}

	/**
	 *
	 * @return genres The list of the artist's genres
	 */
	public ArrayList<String> getGenres() {
	    if (this.genres.size() == 0){
			this.genres = new ArrayList<>(Arrays.asList(sArtist.getGenres()));
		}
		return genres;
	}

	/**
	 *
	 * @return artistID The artist's Spotify ID
	 */
	public String getArtistID() {
		return sArtist.getId();
	}

	/**
	 * 
	 * @return name The name of the artist
	 */
	public String getName() {
		return sArtist.getName();
	}

	/**
	 * 
	 * @return popularity The popularity of the artist
	 */
	public int getPopularity() {
		return sArtist.getPopularity();
	}

	/**
	 * Get the images of the Artist
	 * @return ArrayList of Image of the Artist
	 */
	public ArrayList<Image> getImages() {
		if (this.images == null){
			this.images = SpotifyTools.toImage(sArtist.getImages());
		}
		return images; }

	/**
	 * Get the top tracks by this Artist
	 * @return ArrayList of top Tracks by the Artist
	 */
	public ArrayList<Track> getTracks(){
		if (tracks == null){
			this.tracks = SpotifyTools.getArtistTopTracks(getArtistID());
		}
		return this.tracks;
	}

	public int getRanking(){ return ranking; }

	public void setRanking(int ranking){ this.ranking = ranking; }
}
