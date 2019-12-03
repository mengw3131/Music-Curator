package com.curator.recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.DBTools;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class receives a user-provided list of artists and creates a
 *         list of recommended artists who are similar to the user-provided
 *         artists. Songs by the user-provided artists are used as the input to
 *         a com.curator.recommender.SongRecommender instance. The list of
 *         recommended songs from that class is used to create a list of the
 *         most recommended artists.
 * 
 */
public class ArtistRecommender {
	ArrayList<Artist> userArtistLikes; // stores the user provided artists
	ArrayList<Track> songInputs; // the most popular songs by the artists in
									// userArtistLikes
	ArrayList<Track> songRecs; // stores the top results from SongRecommender
								// run on songs by the user-provided artists
	HashMap<Artist, Integer> artistResults; // stores the artists of the songs
											// in songRecs as well as the number
											// of songs by that artist in
											// songRecs
	TreeMap<Integer, Artist> artistResultsRanked; // stores a sorted list of the
													// artists in artistResults,
													// sorted by number of songs
													// in songRecs
	ArrayList<Artist> userArtistRecs; // a list of the artists with the best
										// similarity scores

	// Constructor
	public ArtistRecommender(ArrayList<Artist> userArtistLikes) {
		this.userArtistLikes = userArtistLikes;
		this.artistResults = new HashMap<>();
		this.artistResultsRanked = new TreeMap<>(Collections.reverseOrder());
		this.userArtistRecs = new ArrayList<>();

		this.runRecommender();

		DBTools.storeRecommendationArtist(userArtistRecs);
	}

	// Methods

	/**
	 * 
	 * @return userArtistLikes The list of user-provided artists
	 */
	public ArrayList<Artist> getUserArtistLikes() {
		return userArtistLikes;
	}

	/**
	 * Allows the userArtistLikes array to be edited after the class instance
	 * has been constructed.
	 * 
	 * @param userArtistLikes
	 */
	public void setUserArtistLikes(ArrayList<Artist> userArtistLikes) {
		this.userArtistLikes = userArtistLikes;
	}

	/**
	 * 
	 * @return songPoolScored The list of recommended songs generated by running
	 *         an instance of com.curator.recommender.SongRecommender
	 */
	public ArrayList<Track> getSongRecs() {
		return songRecs;
	}

	/**
	 * 
	 * @return userArtistRecs The list of recommended artists and their
	 *         similarity scores
	 */
	public ArrayList<Artist> getUserArtistRecs() {
		return userArtistRecs;
	}

	/**
	 * Converts the list of user-liked artists into a list of the most popular
	 * songs by those artists.
	 * 
	 * @param userArtistLikes The list of user-provided artists
	 * @return songInputs The list of songs by the user-liked artists
	 */
	public void artistsToSongs() {
		for (Artist artist : userArtistLikes) {
			for (Track song : artist.getTracks()) {
				songInputs.add(song);
			}
		}
	}

	/**
	 * Takes the output of SongRecommender (songRecs) and creates a map of the
	 * artists of the tracks in songRecs to the number of tracks by that artist
	 * in songRecs.
	 */
	public void recSongsToArtists() {
		for (Track song : songRecs) {
			artistResults.computeIfPresent(song.getArtists().get(0),
					(key, val) -> val + 1);
			artistResults.putIfAbsent(song.getArtists().get(0), 1);
		}
		for (Map.Entry<Artist, Integer> entry : artistResults.entrySet()) {
			artistResultsRanked.put(entry.getValue(), entry.getKey());
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
		for (Map.Entry<Integer, Artist> entry : artistResultsRanked
				.entrySet()) {
			userArtistRecs.add(entry.getValue());
		}
	}

	/**
	 * Takes a list of songs from the user-provided artists and uses those songs
	 * as an input to com.curator.recommender.SongRecommender. Retrieves the
	 * song recommendations from com.curator.recommender.SongRecommender. Stores
	 * these results in userSongRecs. Calculates artist similarity scores based
	 * on the similarity scores of the songs. Creates a list of recommended
	 * artists based on the artists of the songs in the list of recommended
	 * songs.
	 * 
	 * @param userArtistLikes The list of user provided artists
	 * @param songRecs        The list of songs from
	 *                        com.curator.recommender.SongRecommender
	 * @param userArtistRecs  The list of recommended artists and their
	 *                        similarity scores
	 */
	public ArrayList<Artist> runRecommender() {
		artistsToSongs();
		SongRecommender songRecommender = new SongRecommender(songInputs);
		songRecs = songRecommender.runRecommender(100);
		recSongsToArtists();
		bestRecommendations();
		return userArtistRecs;
	}
}
