package com.curator.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;

/**
 * 
 * @author Luke, Isaac, Meng
 *
 *         This class takes in a list of user-provided songs and creates a list
 *         of similar songs as recommendations to the user. The genres of the
 *         user-provided songs are used to narrow down the Spotify song
 *         database. Then, each of the songs in the narrowed down pool are given
 *         a similarity score. Lastly, the songs with the highest similarity
 *         score are placed in the recommendation playlist.
 */
public class SongRecommender {
	ArrayList<Track> userLikes; // stores the user provided songs
	TreeMap<Double, Track> songPoolScored; // stores songs from the Spotify
											// database
	// and their similarity scores (ordered by similarity
	// score)
	ArrayList<Track> songPool; // stores the narrowed-down pool of songs from
								// Spotify from the same genre as the
								// user-provided songs
	HashMap<String, Double> userLikesMetrics; // stores the average song metrics
												// of all the songs in userLikes
	ArrayList<Track> userRecs; // stores the songs with the best similarity
								// scores

	// Constructor
	public SongRecommender(ArrayList<Track> songInputs) {
		this.userLikes = songInputs;
		this.songPoolScored = new TreeMap<>();
		this.userLikesMetrics = new HashMap<>();

		this.runRecommender(25);

		DBTools.storeRecommendationTrack(userRecs);
	}

	// Methods

	/**
	 * 
	 * @return userLikes The ArrayList of Songs that the user has provided
	 */
	public ArrayList<Track> getUserLikes() {
		return userLikes;
	}

	public HashMap<String, Double> getUserLikesMetrics() {
		return userLikesMetrics;
	}

	public ArrayList<Track> getUserRecs() {
		return userRecs;
	}

	/**
	 * 
	 * @return songPool The ArrayList of Songs from which recommendations will
	 *         be pulled
	 */
	public ArrayList<Track> getSongPool() {
		return songPool;
	}

	/**
	 * 
	 * @return songPoolScored The HashMap of containing the songs from songPool
	 *         and their similarity scores
	 */
	public TreeMap<Double, Track> getSongPoolScored() {
		return songPoolScored;
	}

	/**
	 * Places the artists of the different songs in userLikes into an ArrayList.
	 * Retrieves the related artists of the user-provided artists. Retrieves the
	 * top songs of those artists. Places the results into the songPool
	 * ArrayList.
	 * 
	 * @param userLikes The list of user-liked songs
	 * @param songPool  The list of songs that will be ranked by similarity
	 *                  score
	 */
	public void searchByRelatedArtists() {
		ArrayList<Artist> userArtists = new ArrayList<>();
		List<ArrayList<Artist>> relatedArtists = new ArrayList<ArrayList<Artist>>();
		for (Track song : userLikes) {
			userArtists.add(song.getArtists().get(0));
		}
		for (Artist artist : userArtists) {
			relatedArtists
					.add(SpotifyTools.getRelatedArtists(artist.getArtistID()));
		}
		for (ArrayList<Artist> list : relatedArtists) {
			for (Artist artist : list) {
				for (Track song : SpotifyTools
						.getArtistTopTracks(artist.getArtistID())) {
					songPool.add(song);
				}

			}
		}
	}

	// add a "recommender tools" thing in tools

	/**
	 * Calculates the average song feature scores for the user-liked songs.
	 * 
	 * @param userLikes        The ArrayList of Songs that the user has provided
	 * @param userLikesMetrics The map containing the average feature score of
	 *                         all the songs in userRecs
	 */
	public void averageUserLikesMetrics() {
		double userLikesSize = userLikes.size();
		double acousticScore = 0;
		double danceScore = 0;
		double energyScore = 0;
		double instrumentalScore = 0;
		double loudScore = 0;
		double tempoScore = 0;
		double valenceScore = 0;
		for (Track song : userLikes) {
			acousticScore += song.getAcousticness();
			danceScore += song.getDanceability();
			energyScore += song.getEnergy();
			instrumentalScore += song.getInstrumentalness();
			loudScore += song.getLoudness();
			tempoScore += song.getTempo();
			valenceScore += song.getValence();
		}
		acousticScore /= userLikesSize;
		danceScore /= userLikesSize;
		energyScore /= userLikesSize;
		instrumentalScore /= userLikesSize;
		loudScore /= userLikesSize;
		tempoScore /= userLikesSize;
		valenceScore /= userLikesSize;
		userLikesMetrics.put("Acousticness", acousticScore);
		userLikesMetrics.put("Danceability", danceScore);
		userLikesMetrics.put("Energy", energyScore);
		userLikesMetrics.put("Instrumentalness", instrumentalScore);
		userLikesMetrics.put("Loudness", loudScore);
		userLikesMetrics.put("Tempo", tempoScore);
		userLikesMetrics.put("Valence", valenceScore);
	}

	/**
	 * 
	 * Generates a similarity score for a song compared to the metrics in
	 * averageUserLikesMetrics. A percentage difference calculation is performed
	 * for each feature. The percentage differences are then summed in a
	 * weighted manner to yield the overall track similarity score.
	 * 
	 * @param averageUserLikesMetrics The map containing the average feature
	 *                                score of all the songs in userRecs
	 */
	public double generateSimilarityScore(Track song) {
		double similarityScore;
		double acousticScore = (song.getAcousticness()
				- userLikesMetrics.get("Acousticness"))
				/ ((song.getAcousticness()
						+ userLikesMetrics.get("Acousticness")) / 2);
		double danceScore = (song.getDanceability()
				- userLikesMetrics.get("Danceability"))
				/ ((song.getDanceability()
						+ userLikesMetrics.get("Danceability")) / 2);
		double energyScore = (song.getEnergy() - userLikesMetrics.get("Energy"))
				/ ((song.getEnergy() + userLikesMetrics.get("Energy")) / 2);
		double instrumentalScore = (song.getInstrumentalness()
				- userLikesMetrics.get("Instrumentalness"))
				/ ((song.getInstrumentalness()
						+ userLikesMetrics.get("Instrumentalness")) / 2);
		double loudScore = (song.getLoudness()
				- userLikesMetrics.get("Loudness"))
				/ ((song.getLoudness() + userLikesMetrics.get("Loudness")) / 2);
		double tempoScore = (song.getTempo() - userLikesMetrics.get("Tempo"))
				/ ((song.getTempo() + userLikesMetrics.get("Tempo")) / 2);
		double valenceScore = (song.getValence()
				- userLikesMetrics.get("Valence"))
				/ ((song.getValence() + userLikesMetrics.get("Valence")) / 2);

		similarityScore = (acousticScore * 0.16) + (danceScore * 0.17)
				+ (energyScore * 0.20) + (instrumentalScore * 0.11)
				+ (loudScore * 0.10) + (tempoScore * 0.11)
				+ (valenceScore * 0.15);
		return similarityScore;
	}

	/**
	 * Loads the tracks from songPool into a TreeMap with the similarity score
	 * for the track.
	 * 
	 * @param songPool The list of songs that will be ranked by similarity score
	 * @param songRecs The TreeMap containing songs and their similarity scores
	 */
	public void loadRecScores() {
		for (Track song : songPool) {
			songPoolScored.put(generateSimilarityScore(song), song);
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
	public void bestRecommendations(int listSize) {
		int count = 0;
		for (Map.Entry<Double, Track> entry : songPoolScored.entrySet()) {
			if (count >= listSize) {
				break;
			}
			userRecs.add(entry.getValue());
			count++;
		}
	}

	public ArrayList<Track> runRecommender(int listSize) {
		averageUserLikesMetrics();
		searchByRelatedArtists();
		loadRecScores();
		bestRecommendations(listSize);
		return userRecs;
	}
}
