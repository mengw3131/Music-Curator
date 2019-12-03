package com.curator.tools;

import java.util.ArrayList;

import com.curator.models.*;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 *         This class provides methods for interfacing with the Recommender
 *         objects.
 *
 */
public class RecTools {
	// TODO Still needed? because right now recommender object just pushes all
    // of its results to the database

    //Keeps all the current
    private static int INIT_SIZE = 150;
    private static int MIN_THRESHOLD = 30;
    private static ArrayList<Track> trackQueue = new ArrayList<>();
    private static ArrayList<Album> albumQueue = new ArrayList<>();
    private static ArrayList<Artist> artistQueue = new ArrayList<>();
    private static int trackQueueSize = 0;
    private static int albumQueueSize = 0;
    private static int artistQueueSize = 0;

    //Run this after the recommenders have stored the recommended items
    public void init(){
        replenishTrack();
        replenishAlbum();
        replenishAlbum();
    }

    private static void replenishTrack(){
        trackQueue.addAll(DBTools.getRecommendationTrack(INIT_SIZE));
        trackQueueSize = trackQueue.size();
    }

    /**
     * Fetch recommended artists from DB, and add it to the queue
     */
    private static void replenishAlbum(){
        albumQueue.addAll(DBTools.getRecommendationAlbum(INIT_SIZE));
        albumQueueSize = albumQueue.size();
    }

    /**
     * Fetch recommended artists from DB, and add it to the queue
     */
    private static void replenishArtist(){
        artistQueue.addAll(DBTools.getRecommendationArtist(INIT_SIZE));
        artistQueueSize = artistQueue.size();
    }

    /**
     * Pop ArrayList of Track object from the queue, max 50 per query
     */
    public static ArrayList<Track> popTracks(int qty){
        if (qty > 50){
            System.out.println("Too much! Max 50");
            return popTracks(50);
        }
        ArrayList<Track> tracks = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            tracks.add(popTrack());
        }
        return tracks;
    }

    /**
     * Pop ArrayList of Artist object from the queue, max 50 per query
     */
    public static ArrayList<Artist> popArtists(int qty){
        if (qty > 50){
            System.out.println("Too much! Max 50");
            return popArtists(50);
        }
        ArrayList<Artist> artists = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            artists.add(popArtist());
        }
        return artists;
    }

    /**
     * Pop ArrayList of Album object from the queue, max 50 per query
     */
    public static ArrayList<Album> popAlbums(int qty){
        if (qty > 50){
            System.out.println("Too much! Max 50");
            return popAlbums(50);
        }
        ArrayList<Album> albums = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            albums.add(popAlbum());
        }
        return albums;
    }

    /**
     * Pop a Track object from the queue
     */
    public static Track popTrack(){
        checkAndReplenishTrack();
        Track t = trackQueue.get(0);
        trackQueue.remove(0);

        trackQueueSize--;
        return t;
    }

    /**
     * Pop an Album object from the queue
     */
    public static Album popAlbum(){
        checkAndReplenishAlbum();
        Album a = albumQueue.get(0);
        albumQueue.remove(0);
        albumQueueSize--;
        return a;
    }

    /**
     * Pop an Artist object from the queue
     */
    public static Artist popArtist(){
        checkAndReplenishArtist();
        Artist a = artistQueue.get(0);
        artistQueue.remove(0);
        artistQueueSize--;
        return a;
    }

    /**
     * If the current artist stack size is below threshold, replenish artist
     */
    private static void checkAndReplenishArtist(){
        if (artistQueueSize < MIN_THRESHOLD){
            replenishArtist();
        }
    }

    /**
     * If the current album stack size is below threshold, replenish album
     */
    private static void checkAndReplenishAlbum(){
        if (albumQueueSize < MIN_THRESHOLD){
            replenishAlbum();
        }
    }

    /**
     * If the current track stack size is below threshold, replenish queue
     */
    private static void checkAndReplenishTrack(){
        if (trackQueueSize < MIN_THRESHOLD){
            replenishTrack();
        }
    }
}
