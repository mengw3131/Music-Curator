package com.curator;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class stores the audio features of songs and allows the features
 *         to be accessed by other classes.
 *
 */
public class Song {
	double acousticness; // confidence measure (0.0 - 1.0) of whether the song
							// is acoustic
	String danceability; // 0.0 - 1.0 how danceable the song is
	double energy; // 0.0 - 1.0 measure of how fast/loud/noisy the song is
	double instrumentalness; // 0.0 - 1.0 confidence measure of whether a track
								// contains vocals
	double loudness; // overall loudness of the track in decibels
	double tempo; // tempo of the track (Beats per minute)
	double valence; // 0.0 - 1.0 describing the musical positiveness
	String trackID; // Spotify track ID
	String name; // name of the song
	Artist artist; // the artist of the song
	Album album; // the album of the song
	int popularity; // the popularity of a song (0 - 100)

	// Constructor
	public Song(Object spotifySong) {
		this.acousticness = spotifySong.getAcousticness();
		this.danceability = spotifySong.getDanceability();
		this.energy = spotifySong.getEnergy();
		this.instrumentalness = spotifySong.getInstrumentalness();
		this.loudness = spotifySong.getLoudness();
		this.tempo = spotifySong.getTempo();
		this.valence = spotifySong.getValence();
		this.trackID = spotifySong.getId();
		this.name = spotifySong.getName();
		this.artist = spotifySong.getArtist();
		this.album = spotifySong.getAlbum();
	}

	// Methods

	/**
	 * 
	 * @return acousticness The confidence measure (0.0 - 1.0) of whether the
	 *         song is acoustic
	 */
	public double getAcousticness() {
		return acousticness;
	}

	/**
	 * 
	 * @return danceability How danceable the song is (0.0 - 1.0)
	 */
	public String getDanceability() {
		return danceability;
	}

	/**
	 * 
	 * @return energy How loud/fast/noisy the song is (0.0 - 1.0)
	 */
	public double getEnergy() {
		return energy;
	}

	/**
	 * 
	 * @return instrumentalness The confidence measure of whether the song as
	 *         vocals (0.0 - 1.0)
	 */
	public double getInstrumentalness() {
		return instrumentalness;
	}

	/**
	 * 
	 * @return loudness Loudness of the song in decibels
	 */
	public double getLoudness() {
		return loudness;
	}

	/**
	 * 
	 * @return tempo Tempo of the song (Beats per minute)
	 */
	public double getTempo() {
		return tempo;
	}

	/**
	 * 
	 * @return valence The measure of the positiveness of a song (0.0 - 1.0)
	 */
	public double getValence() {
		return valence;
	}

	/**
	 * 
	 * @return trackID The Spotify song ID
	 */
	public String getTrackID() {
		return trackID;
	}

	/**
	 * 
	 * @return name The name of the song
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return artist The object for the artist that plays the song
	 */
	public Artist getArtist() {
		return artist;
	}

	/**
	 * 
	 * @return album The object for the album that the song is on
	 */
	public Album getAlbum() {
		return album;
	}

	/**
	 * 
	 * @return popularity The popularity measure of the song (0 - 100)
	 */
	public int getPopularity() {
		return popularity;
	}

}
