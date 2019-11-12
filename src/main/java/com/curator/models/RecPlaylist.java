package com.curator.models;

import com.curator.models.Track;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class stores the recommended songs generated from an instance of
 *         running the recommender.
 *
 */
public class RecPlaylist {
	ArrayList<Track> trackList; // the list of songs in the playlist
	HashMap<Track, Double> userRecs; // stores the songs and similarity scores
									// generated from running one of the
									// recommenders

	// Constructor
	public RecPlaylist(ArrayList<Track> trackList,
			HashMap<Track, Double> userRecs) {
		this.trackList = trackList;
		this.userRecs = userRecs;
	}

	// Methods

	/**
	 * 
	 * @return trackList The list of song objects for the songs in the playlist
	 */
	public ArrayList<Track> getTrackList() {
		return trackList;
	}

	/**
	 * Allows the trackList to be edited.
	 * 
	 * @param trackList The list of song objects for the songs in the playlist
	 */
	public void setTrackList(ArrayList<Track> trackList) {
		this.trackList = trackList;
	}

	/**
	 * 
	 * @return userRecs The songs and similarity scores generated when the user
	 *         ran the recommender
	 */
	public HashMap<Track, Double> getUserRecs() {
		return userRecs;
	}

	/**
	 * Allows the user to provide input on whether they like or dislike a song
	 * on a playlist, then updates the playlist by removing that song (and other
	 * very similar songs) and replacing it with a new recommended song from
	 * userRecs or keeping the song and replacing other less similar songs with
	 * songs from userRecs that are more similar to the liked song.
	 * 
	 * @param userRecs  The songs and similarity scores generated when the user
	 *                  ran the recommender
	 * @param trackList The list of song objects for the songs in the playlist
	 */
	public void updatePlaylist() {
		// TODO
	}

}
