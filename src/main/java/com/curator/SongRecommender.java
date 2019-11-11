package com.curator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Luke, Isaac, Meng
 *
 *         This class takes in a list of user-provided songs and creates a list
 *         of similar songs as recommendations to the user. The genres of the
 *         user-provided songs are used to narrow down the Spotify song
 *         database. Then, each of the songs in the narrowed down pool are given
 *         a similarity score. Lastly, the songs with the highest similarity
 *         score are placed in the recommendation list.
 */
public class SongRecommender {
	ArrayList<Song> userLikes;
	ArrayList<Song> recommendationPool;
	HashMap<Song, Double> userRecs;
	ArrayList<Song> songPool;

	// Constructor
	SongRecommender(ArrayList<Song> song_inputs) {
		this.userLikes = song_inputs;
	}

	// Methods

	public ArrayList<Song> getUserLikes() {
		return userLikes;
	}

	public ArrayList<Song> getSongPool() {
		return songPool;
	}

	public HashMap<Song, Double> getUserRecs() {
		return userRecs;
	}

	public ArrayList<Song> getRecommendationPool() {
		return recommendationPool;
	}

	/**
	 * Places the genres of the different songs in userLikes into a
	 * comma-separated string. Inputs the string into the Spotify Seed Searcher.
	 * Places the results into the songPool ArrayList.
	 * 
	 * @param userLikes The list of user-liked songs
	 * @param songPool  The list of songs that will be ranked by similarity
	 *                  score
	 */
	public void searchByGenres() {

	}

	/**
	 * Calculates the average song feature score for the user-liked songs.
	 * Generates a similarity score for each of the songs in songPool and places
	 * the song and its score into the HashMap userRecs.
	 * 
	 * @param songPool The list of songs that will be ranked by similarity score
	 * @param userRecs The HashMap containing songs and their similarity scores
	 */
	public void generateSimilarityScore() {

	}

	/**
	 * Ranks the songs in userRecs according to their similarity scores.
	 * Displays the top-ranking 15 songs in the HashMap to the user.
	 * 
	 * @param userRecs The HashMap containing songs and their similarity scores
	 */
	public void displayRecommendations() {

	}
}
