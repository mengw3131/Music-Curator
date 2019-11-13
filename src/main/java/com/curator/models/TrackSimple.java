package com.curator.models;

import com.curator.tools.SpotifyTools;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;

import java.util.ArrayList;

/**
 * Simplified Track object with no reference to album objects (to avoid circularity)
 */
public class TrackSimple {
    private String trackID;                     // Spotify track ID
    private String name;                        // name of the song
    private int popularity;                     // the popularity of a song (0 - 100)
    private ArrayList<Artist> artists;          // the list of artists on the album

    private AudioFeatures features;
    private double acousticness;                // confidence measure (0.0 - 1.0) of whether the song is acoustic
    private double danceability;                // 0.0 - 1.0 how danceable the song is
    private double energy;                      // 0.0 - 1.0 measure of how fast/loud/noisy the song is
    private double instrumentalness;            // 0.0 - 1.0 confidence measure of whether a track
    private double loudness;                    // overall loudness of the track in decibels
    private double tempo;                       // tempo of the track (Beats per minute)
    private double valence;

    public TrackSimple(com.wrapper.spotify.model_objects.specification.Track sTrack){
        this.trackID = sTrack.getId();
        this.name = sTrack.getName();
        this.popularity =  sTrack.getPopularity();

		this.artists = SpotifyTools.toArtist(sTrack.getArtists());

        this.features = SpotifyTools.getAudioFeatures(sTrack);
        this.acousticness = features.getAcousticness();
        this.danceability = features.getDanceability();
        this.energy = features.getEnergy();
        this.instrumentalness = features.getInstrumentalness();
        this.loudness = features.getLoudness();
        this.tempo = features.getTempo();
        this.valence = features.getValence();
    }


    public String getTrackID() {
        return trackID;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public double getDanceability() {
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
}
