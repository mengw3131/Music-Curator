package com.curator.recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.curator.models.Album;
import com.curator.models.Track;
import com.curator.tools.DBTools;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class receives a user-provided list of albums and creates a list
 *         of recommended albums that are similar to the user-provided albums.
 *         Songs on the user-provided albums are used as the input to a
 *         com.curator.recommender.SongRecommender instance. The list of
 *         recommended songs from that class is used to create a list of the
 *         most recommended albums.
 * 
 */
public class AlbumRecommender {
	ArrayList<Album> userAlbumLikes = new ArrayList<>(); // stores the user
															// provided albums
	ArrayList<Track> songInputs = new ArrayList<>(); // the songs on the albums
														// in userAlbumLikes
	ArrayList<Track> songRecs = new ArrayList<>(); // stores the top results
													// from SongRecommender
	// run on songs by the user-provided albums
	HashMap<Album, Integer> albumResults; // stores the albums of the
											// songs
	// in songRecs as well as the number
	// of songs on that album in
	// songRecs
	TreeMap<Integer, Album> albumResultsRanked; // stores a sorted list of
												// the
	// albums in albumResults,
	// sorted by number of songs in
	// songRecs
	ArrayList<Album> userAlbumRecs; // a list of the albums with the best
	// similarity scores

	// Constructors
	public AlbumRecommender() {
		this.albumResults = new HashMap<>();
		this.albumResultsRanked = new TreeMap<>(Collections.reverseOrder());
		this.userAlbumRecs = new ArrayList<>();

		this.userAlbumLikes = DBTools.getUserLikedAlbum();

		this.runRecommender();

		DBTools.storeRecommendationAlbum(userAlbumRecs);
	}

	public AlbumRecommender(ArrayList<Album> userAlbums) {
		this.albumResults = new HashMap<>();
		this.albumResultsRanked = new TreeMap<>(Collections.reverseOrder());
		this.userAlbumRecs = new ArrayList<>();

		this.userAlbumLikes = userAlbums;

		this.runRecommender();

		DBTools.storeRecommendationAlbum(userAlbumRecs);
	}

	// Special constructor to use during user creation
	public AlbumRecommender(ArrayList<Track> songResults, boolean newUser) {
		this.albumResults = new HashMap<>();
		this.albumResultsRanked = new TreeMap<>(Collections.reverseOrder());
		this.userAlbumRecs = new ArrayList<>();

		this.songRecs = songResults;

		this.recSongsToAlbums();
		this.bestRecommendations();

		DBTools.storeRecommendationAlbum(userAlbumRecs);
	}

	// Methods

	/**
	 * 
	 * @return userAlbumLikes The list of recommended albums and their
	 *         similarity scores
	 */
	public ArrayList<Album> getUserAlbumLikes() {
		return userAlbumLikes;
	}

	/**
	 * Allows userAlbumLikes to be edited after construction of the class
	 * instance.
	 * 
	 * @param userAlbumLikes The list of user-provided albums
	 */
	public void setUserAlbumLikes(ArrayList<Album> userAlbumLikes) {
		this.userAlbumLikes = userAlbumLikes;
	}

	/**
	 * 
	 * @return userAlbumRecs The list of recommended albums and their similarity
	 *         scores
	 */
	public ArrayList<Album> getUserAlbumRecs() {
		return userAlbumRecs;
	}

	/**
	 * Converts the list of user-liked albums into a list of the tracks on the
	 * albums
	 * 
	 * @param userAlbumsLikes The list of user-provided albums
	 * @param songInputs      The list of songs on the user-liked albums
	 */
	public void albumsToSongs() {
		for (Album album : userAlbumLikes) {
			for (Track song : album.getTracks()) {
				songInputs.add(song);
			}
		}
	}

	/**
	 * Takes the output of SongRecommender (songRecs) and creates a map of the
	 * albums of the tracks in songRecs to the number of tracks on that album in
	 * songRecs.
	 */
	public void recSongsToAlbums() {
		for (Track song : songRecs) {
			albumResults.computeIfPresent(song.getAlbum(),
					(key, val) -> val + 1);
			albumResults.putIfAbsent(song.getAlbum(), 1);
		}
		for (Map.Entry<Album, Integer> entry : albumResults.entrySet()) {
			albumResultsRanked.put(entry.getValue(), entry.getKey());
		}
	}

	/**
	 * Iterates through the TreeMap songPoolScored to return the songs with the
	 * best (lowest value) similarity scores.
	 * 
	 * @param songRecs The TreeMap containing songs and their similarity scores
	 * @param userRecs The ArrayList containing the songs with the best
	 *                 similarity scores
	 */
	public void bestRecommendations() {
		for (Map.Entry<Integer, Album> entry : albumResultsRanked.entrySet()) {
			userAlbumRecs.add(entry.getValue());
		}
	}

	/**
	 * Takes a list of songs from the user-provided albums and uses those songs
	 * as an input to com.curator.recommender.SongRecommender. Retrieves the
	 * song recommendations from com.curator.recommender.SongRecommender. Stores
	 * these results in songRecs. Creates a list of recommended albums based on
	 * the albums of the songs in the list of recommended songs.
	 * 
	 */
	public ArrayList<Album> runRecommender() {
		albumsToSongs();
		SongRecommender songRecommender = new SongRecommender(songInputs);
		songRecs = songRecommender.runRecommender(25);
		recSongsToAlbums();
		bestRecommendations();
		return userAlbumRecs;
	}
}
