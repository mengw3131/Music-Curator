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
	double acousticness;
	String danceability;
	double energy;
	double instrumentalness;
	double loudness;
	double tempo;
	double valence;
	String trackID;
	String name;

	// Constructor
	Song(Object spotifySong) {
		this.acousticness = spotifySong.getAcousticness();
		this.danceability = spotifySong.getDanceability();
		this.energy = spotifySong.getEnergy();
		this.instrumentalness = spotifySong.getInstrumentalness();
		this.loudness = spotifySong.getLoudness();
		this.tempo = spotifySong.getTempo();
		this.valence = spotifySong.getValence();
		this.trackID = spotifySong.getId();
		this.name = spotifySong.getName();
	}

	// Methods

	public double getAcousticness() {
		return acousticness;
	}

	public String getDanceability() {
		return danceability;
	}

	public double getEnergy() {
		return energy;
	}

	public double getInstrumentalness() {
		return instrumentalness;
	}

	public double getLoudness() {
		return loudness;
	}

	public double getTempo() {
		return tempo;
	}

	public double getValence() {
		return valence;
	}

	public String getTrackID() {
		return trackID;
	}

	public String getName() {
		return name;
	}

}
