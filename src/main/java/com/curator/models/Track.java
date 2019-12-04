package com.curator.models;

import java.util.ArrayList;

import com.curator.tools.SpotifyTools;
import com.curator.tools.YoutubeTools;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 * @author Luke, Isaac, Meng
 *         <p>
 *         This class stores the audio features of songs and allows the features
 *         to be accessed by other classes.
 */
public class Track {
	private ArrayList<Artist> artists; // the list of artists on the track
	private String artistsNames; // the list of artists on the track
    private Album album;
	private Image image;
	private Media media;
	private String trackName; // name of the song

	private int ranking = -1; //ranking

	private boolean analysisRan = false;

	// wrapper objects
	private AudioFeatures features;
	private com.wrapper.spotify.model_objects.specification.Track sTrack;

	/**
	 * Construct com.curator.models.Track object from wrapper's
	 * com.curator.models.Track object
	 *
	 * @param sTrack spotify wrapper's com.curator.models.Track object
	 */
	public Track(com.wrapper.spotify.model_objects.specification.Track sTrack) {

		this.sTrack = sTrack;
	}

	// ==========================================================================
	// TRACK META GETTERS & SETTERS
	// ==========================================================================

	/**
	 * @return trackID The Spotify song ID
	 */
	public String getTrackID() {
		return sTrack.getId();
	}

	/**
	 * @return name The name of the song
	 */
	public String getTrackName() {
		if (trackName == null) {
			setTrackName(sTrack.getName());
		}
		return trackName;
	}

	/**
	 * Set track name
	 *
	 * @param name track name
	 */
	public void setTrackName(String name) {
		this.trackName = name;
	}

	/**
	 * @return artists com.curator.models.Artist of the track
	 */
	public ArrayList<Artist> getArtists() {
		this.artists = SpotifyTools.toArtists(sTrack.getArtists());

		return artists;
	}

	/**
	 * Return the names of the artists, separated by comma
	 *
	 * @return comma separated string of the names of the artists
	 */
	public String getArtistsNames() {
		if (artistsNames == null) {
			setArtistsNames();
		}
		return artistsNames;
	}

	/**
	 * Set artistNames variable of the track. If none supplied, artists is
	 * sourced from Spotify.
	 *
	 * @param artistsNames names of the artists
	 */
	public void setArtistsNames(String... artistsNames) {
		if (artistsNames.length == 0) {
			StringBuilder sb = new StringBuilder();
			for (ArtistSimplified a : sTrack.getArtists()) {
				sb.append(a.getName()).append(", ");
			}
			this.artistsNames = sb.toString();
			this.artistsNames = getArtistsNames().substring(0,
					getArtistsNames().length() - 2); // removeFromPlaylists last ,

		} else {
			this.artistsNames = String.join(", ", artistsNames);
		}
	}

	/**
	 * @return album The object for the album that the song is on
	 */
	public Album getAlbum() {
		if (album == null){
			this.album = SpotifyTools.getAlbum(sTrack.getAlbum().getId());
		}
		return album;
	}

	/**
	 * Returns album name of current track
	 *
	 * @return album name of current track
	 */
	public String getAlbumName() {
		return sTrack.getAlbum().getName();
	}

	/**
	 * @return popularity The popularity measure of the song (0 - 100)
	 */
	public int getPopularity() {
		return sTrack.getPopularity();
	}

    public int getRanking(){ return ranking; }

	public void setRanking(int ranking){ this.ranking = ranking; }

	/**
	 * Return image of the track.
	 *
	 * @return Image object of the track.
	 */
	public Image getImage() {
		if (image == null) {
			setImage();
		}
		return image;
	}

	/**
	 * Set image of the track. If null supplied, image is sourced from Spotify.
	 *
	 * @param image image of the track
	 */
	public void setImage(Image... image) {
		if (image.length == 0) {
			this.image = SpotifyTools
					.toImage(sTrack.getAlbum().getImages()[0].getUrl());
		} else {
			this.image = image[0];
		}
	}

	/**
	 * Return Media object of the track.
	 *
	 * @return Media object of the track.
	 */
	public Media getMedia() {
		if (media == null) {
			setMedia();
		}
		return media;
	}

	/**
	 * Set Media object of the track
	 *
	 * @param media
	 */
	public void setMedia(Media... media) {
		if (media.length == 0) {
			this.media = YoutubeTools.getMusicFileFromQuery(
					YoutubeTools.createYoutubeQuery(this.getTrackName(),
							this.getArtistsNames()));
		} else {
			this.media = media[0];
		}
	}

	// ==========================================================================
	// AUDIO FEATURES GETTERS
	// ==========================================================================

	/**
	 * Set AudioFeatures object of the track
	 */
	private void setAudioFeatures() {
		this.features = SpotifyTools.getAudioFeatures(getTrackID());
		analysisRan = true;
	}

	/**
	 * @return acousticness The confidence measure (0.0 - 1.0) of whether the
	 *         song is acoustic
	 */
	public double getAcousticness() {
		if (this.features == null && !analysisRan){
			setAudioFeatures();
			return getAcousticness();
		} else if(this.features == null) {
		    return 0;
		}
		return features.getAcousticness();
	}

	/**
	 * @return danceability How danceable the song is (0.0 - 1.0)
	 */
	public double getDanceability() {
		if (this.features == null && !analysisRan){
			setAudioFeatures();
			return getDanceability();
		} else if(this.features == null) {
			return 0;
		}
		return features.getDanceability();
	}

	/**
	 * @return energy How loud/fast/noisy the song is (0.0 - 1.0)
	 */
	public double getEnergy() {
		if (this.features == null && !analysisRan){
			setAudioFeatures();
			return getEnergy();
		} else if(this.features == null) {
			return 0;
		}
		return features.getEnergy();
	}

	/**
	 * @return instrumentalness The confidence measure of whether the song as
	 *         vocals (0.0 - 1.0)
	 */
	public double getInstrumentalness() {
		if (this.features == null && !analysisRan){
			setAudioFeatures();
			return getInstrumentalness();
		} else if(this.features == null) {
			return 0;
		}
		return features.getInstrumentalness();
	}

	/**
	 * @return loudness Loudness of the song in decibels
	 */
	public double getLoudness() {
		if (this.features == null && !analysisRan){
			setAudioFeatures();
			return getLoudness();
		} else if(this.features == null) {
			return 0;
		}
		return features.getLoudness();
	}

	/**
	 * @return tempo Tempo of the song (Beats per minute)
	 */
	public double getTempo() {
		if (this.features == null && !analysisRan){
			setAudioFeatures();
			return getTempo();
		} else if(this.features == null) {
			return 0;
		}
		return features.getTempo();
	}

	/**
	 * @return valence The measure of the positiveness of a song (0.0 - 1.0)
	 */
	public double getValence() {
		if (this.features == null && !analysisRan){
			setAudioFeatures();
			return getValence();
		} else if(this.features == null) {
			return 0;
		}
		return features.getValence();
	}

	/**
	 * Returns the AudioFeatures object of the track
	 * @return
	 */
	public AudioFeatures getAudioFeatures() {
		if (!analysisRan){
			setAudioFeatures();
		}
		return features;
	}

	public String getAlbumId(){
		return sTrack.getAlbum().getId();
	}

	public String getArtistId(){
		return sTrack.getArtists()[0].getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Track)){
			return false;
		}
		return this.getTrackID().equals(((Track) obj).getTrackID());
	}

	@Override
	public String toString() {
		return "Track " + getTrackName() + " in album " + getAlbumName() + " by " +
				getArtistsNames() + " with id " + getTrackID();
	}
}
