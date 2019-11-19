package com.curator.models;

import com.curator.tools.SpotifyTools;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;

import java.util.ArrayList;

/**
 * Simplified com.curator.models.Track object with no reference to album objects (to avoid circularity)
 */
public class TrackSimple {
    private String trackID;                     // Spotify track ID
    private String trackName;                        // trackName of the song
    private int popularity;                     // the popularity of a song (0 - 100)
    private ArrayList<Artist> artists;          // the list of artists on the album
    private String artistsString;               // the list of artists on the album
    private AudioFeatures features;             // only initialize when needed
    private int duration;

    public TrackSimple(com.wrapper.spotify.model_objects.specification.Track sTrack){
        this.trackID = sTrack.getId();
        this.trackName = sTrack.getName();
        this.popularity =  sTrack.getPopularity();

		this.artists = SpotifyTools.toArtist(sTrack.getArtists());
        this.artistsString = SpotifyTools.toString(this.artists);

        this.duration = sTrack.getDurationMs() / 1000;
    }


    public String getTrackID() {
        return trackID;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getPopularity() {
        return popularity;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public String getArtistsString() { return artistsString; }


    /**
     * @return acousticness The confidence measure (0.0 - 1.0) of whether the song is acoustic
     */
    public double getAcousticness() {
        if (this.features == null) {
            setAudioFeatures();
        }
        return features.getAcousticness();
    }

    /**
     * @return danceability How danceable the song is (0.0 - 1.0)
     */
    public double getDanceability() {
        if (this.features == null) {
            setAudioFeatures();
        }
        return features.getDanceability();
    }

    /**
     * @return energy How loud/fast/noisy the song is (0.0 - 1.0)
     */
    public double getEnergy() {
        if (this.features == null) {
            setAudioFeatures();
        }
        return features.getEnergy();
    }

    /**
     * @return instrumentalness The confidence measure of whether the song as
     * vocals (0.0 - 1.0)
     */
    public double getInstrumentalness() {
        if (this.features == null) {
            setAudioFeatures();
        }
        return features.getInstrumentalness();
    }

    /**
     * @return loudness Loudness of the song in decibels
     */
    public double getLoudness() {
        if (this.features == null) {
            setAudioFeatures();
        }
        return features.getLoudness();
    }

    /**
     * @return tempo Tempo of the song (Beats per minute)
     */
    public double getTempo() {
        if (this.features == null) {
            setAudioFeatures();
        }
        return features.getTempo();
    }

    /**
     * @return valence The measure of the positiveness of a song (0.0 - 1.0)
     */
    public double getValence() {
        if (this.features == null) {
            setAudioFeatures();
        }
        return features.getValence();
    }


    private void setAudioFeatures() {
        this.features = SpotifyTools.getAudioFeatures(trackID);
    }
}
