package com.curator.tools;

import java.util.ArrayList;

import com.curator.models.Album;
import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.recommender.AlbumRecommender;
import com.curator.recommender.ArtistRecommender;
import com.curator.recommender.SongRecommender;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class provides methods for interfacing with the Recommender
 *         objects.
 *
 */
public class RecTools {

	// prevents instance creation
	private RecTools() {
	}

	// Methods

	/**
	 * A method to run the Song Recommender on the user liked songs in the
	 * database. Stores recommendations in the user's recommendation database.
	 */
	public static void runSongRecommender() {
		new SongRecommender();
	}

	/**
	 * A method to run the Song Recommender on a user provided list of liked
	 * songs. Stores recommendations in the user's recommendation database.
	 */
	public static void runSongRecommender(ArrayList<Track> songInputs) {
		new SongRecommender(songInputs);
	}

	/**
	 * A method to run the Artist Recommender on the user liked artist in the
	 * database. Stores recommendations in the user's recommendation database.
	 */
	public static void runArtistRecommender() {
		new ArtistRecommender();
	}

	/**
	 * A method to run the Artist Recommender on a user provided list of liked
	 * artists. Stores recommendations in the user's recommendation database.
	 */
	public static void runArtistRecommender(ArrayList<Artist> artistInputs) {
		new ArtistRecommender(artistInputs);
	}

	/**
	 * A method to run the Album Recommender on the user liked albums in the
	 * database. Stores recommendations in the user's recommendation database.
	 */
	public static void runAlbumRecommender() {
		new AlbumRecommender();
	}

	/**
	 * A method to run the Album Recommender on a user provided list of liked
	 * albums. Stores recommendations in the user's recommendation database.
	 */
	public static void runAlbumRecommender(ArrayList<Album> albumInputs) {
		new AlbumRecommender(albumInputs);
	}

}
