import com.curator.models.Album;
import com.curator.models.Artist;
import com.curator.models.Track;

import java.util.ArrayList;

public class UserProfile {
	String name; // name of the user
	ArrayList<Track> likedSongs; // a list of all songs the user has indicated
								// that they like
	ArrayList<Artist> likedArtists; // a list of all artists the user has
									// indicated that they like
	ArrayList<Album> likedAlbums; // a list of all albums the user has indicated
									// that they like
	ArrayList<String> likedGenres; // a list of all genres the user has
									// indicated that they like
	ArrayList<RecPlaylist> generatedPlaylists; // a list of all the playlists
												// (comprised of recommended
												// songs) that the user has
												// created

	// Constructor
	public UserProfile(String name) {
		this.name = name;
	}

	// Methods

	/**
	 * 
	 * @return name The name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * Allows the name of the user to be edited.
	 * 
	 * @param name The name of the user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return likedSongs The list of songs that a user has indicated that they
	 *         like
	 */
	public ArrayList<Track> getLikedSongs() {
		return likedSongs;
	}

	/**
	 * 
	 * @return likedArtists The list of artists that a user has indicated that
	 *         they like
	 */
	public ArrayList<Artist> getLikedArtists() {
		return likedArtists;
	}

	/**
	 * 
	 * @return likedAlbums The list of albums that a user has indicated that
	 *         they like
	 */
	public ArrayList<Album> getLikedAlbums() {
		return likedAlbums;
	}

	/**
	 * 
	 * @return likedGenres The list of genres that a user has indicated that
	 *         they like
	 */
	public ArrayList<String> getLikedGenres() {
		return likedGenres;
	}

	/**
	 * 
	 * @return generatedPlaylists The list of playlists that a user has
	 *         generated
	 */
	public ArrayList<RecPlaylist> getGeneratedPlaylists() {
		return generatedPlaylists;
	}

}
