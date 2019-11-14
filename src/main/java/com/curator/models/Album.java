package com.curator.models;

import java.util.ArrayList;
import com.curator.tools.SpotifyTools;
import javafx.scene.image.Image;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 * This class stores the features of albums and allows the features to
 * be accessed by other classes.
 *
 *
 */
public class Album {
	private String albumType;              // album, single, or compilation
	private String albumID;                // The Spotify ID of the album
	private String name;                   // The name of the album
	private int popularity;                // popularity of the album (0-100) calculated with the the
	private String artistsString;    // the list of artists on the album
					                       // popularity of the album's songs
	ArrayList<Artist> artists;             // the list of artists on the album


	ArrayList<TrackSimple> tracks;               // the list of tracks on the album

	private ArrayList<Image> images;

	/**
	 * Constructor for com.curator.models.Album object from wrapper's com.curator.models.Album object
	 * @param sAlbum wrapper's com.curator.models.Album object
	 */
	public Album(com.wrapper.spotify.model_objects.specification.Album sAlbum) {
		this.albumType = sAlbum.getAlbumType().getType();
		this.albumID = sAlbum.getId();
		this.name = sAlbum.getName();

		this.popularity = sAlbum.getPopularity();

		this.artists = SpotifyTools.toArtist(sAlbum.getArtists());
		this.artistsString = SpotifyTools.toString(this.artists);
		this.tracks = SpotifyTools.toTrackSimple(sAlbum.getTracks().getItems());

		this.images = SpotifyTools.toImage(sAlbum.getImages());
	}

	/**
	 * 
	 * @return albumType com.curator.models.Album, single, or compilation
	 */
	public String getAlbumType() {
		return albumType;
	}

	/**
	 * 
	 * @return albumID The Spotify ID of the album
	 */
	public String getAlbumID() {
		return albumID;
	}

	/**
	 * 
	 * @return name The name of the album
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return popularity The popularity of the album (0-100) calculated with
	 *         the popularity of the album's songs
	 */
	public int getPopularity() {
		return popularity;
	}

	/**
	 * 
	 * @return artists The list of artist objects who play on the album
	 */
	public ArrayList<Artist> getArtists() {
		return artists;
	}

	/**
	 * 
	 * @return tracks The list of song objects on the album
	 */
	public ArrayList<TrackSimple> getTracks() {
		return tracks;
	}


	public ArrayList<Image> getImages() { return images; }

	public String getArtistsString() { return artistsString; }
}
