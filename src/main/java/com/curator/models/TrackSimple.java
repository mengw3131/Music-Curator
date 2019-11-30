package com.curator.models;

import com.curator.tools.SpotifyTools;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import java.util.ArrayList;

/**
 * Simplified com.curator.models.Track object with no reference to album objects (to avoid circularity)
 */
public class TrackSimple {
    private ArrayList<Artist> artists;          // the list of artists on the album
    private String artistsString;               // the list of artists on the album
    private AudioFeatures features;             // only initialize when needed
    private com.wrapper.spotify.model_objects.specification.Track sTrack;  //wrapper's object

    /**
     * Constructs a TrackSimple object from wrapper's Track object.
     * @param sTrack
     */
    public TrackSimple(com.wrapper.spotify.model_objects.specification.Track sTrack){
        this.sTrack = sTrack;
    }


    /**
     * Get the Spotify's track ID of the track
     * @return Spotify's track ID of the track
     */
    public String getTrackID() {
        return sTrack.getId();
    }

    /**
     * Get the name of the Track
     * @return track's name
     */
    public String getTrackName() {
        return sTrack.getName();
    }

    /**
     * Get the popularity of the track
     * @return track's popularity
     */
    public int getPopularity() {
        return sTrack.getPopularity();
    }

    /**
     * Get the Artists of the track
     * @return ArrayList of Artist of the track
     */
    public ArrayList<Artist> getArtists() {
        if (artists == null){
            this.artists = SpotifyTools.toArtist(sTrack.getArtists());
        }
        return artists;
    }

    /**
     * Get the names of the artists of the track, comma separated
     * @return names of the artists of the track
     */
    public String getArtistsString() {
        if (artistsString == null){
            this.artistsString = SpotifyTools.toString(getArtists());
        }
        return artistsString;
    }

    /**
     * Duration (seconds) of the track
     * @return duration of the track in seconds
     */
    public int getDuration() {
        return sTrack.getDurationMs() / 1000;
    }

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

    /**
     *  Sets the AudioFeatures object of this Track.
     */
    private void setAudioFeatures() {
        this.features = SpotifyTools.getAudioFeatures(getTrackID());
    }
}
