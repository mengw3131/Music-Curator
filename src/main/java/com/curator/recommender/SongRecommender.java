package com.curator.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;

/**
 * @author Luke, Isaac, Meng
 * <p>
 * This class takes in a list of user-provided songs and creates a list
 * of similar songs as recommendations to the user. The genres of the
 * user-provided songs are used to narrow down the Spotify song
 * database. Then, each of the songs in the narrowed down pool are given
 * a similarity score. Lastly, the songs with the highest similarity
 * score are placed in the recommendation playlist.
 */
public class SongRecommender {
    private final ArrayList<Track> userLikes; // stores the user provided songs
    private final TreeMap<Double, Track> songPoolScored; // stores songs from the Spotify
    // database
    // and their similarity scores (ordered by similarity
    // score)
    private final ArrayList<Track> songPool = new ArrayList<>(); // stores the narrowed-down
    // pool of songs from

    private final ArrayList<AudioFeatures> audioFeaturesPool = new ArrayList<>(); // stores
    // the
    // narrowed-down

    // Spotify from the same id as the
    // user-provided songs
    private final HashMap<String, Double> userLikesMetrics; // stores the average song metrics
    // of all the songs in userLikes
    private final ArrayList<Track> userRecs = new ArrayList<>(); // stores the songs with the
    // best similarity
    // scores

    // Constructors
    public SongRecommender() {
        this.songPoolScored = new TreeMap<>();
        this.userLikesMetrics = new HashMap<>();
        this.userLikes = DBTools.getUserLikedSongs();
        this.runRecommender(75);
        DBTools.storeRecommendationTrack(userRecs);
    }

    public SongRecommender(ArrayList<Track> songInputs) {
        this.userLikes = songInputs;
        this.songPoolScored = new TreeMap<>();
        this.userLikesMetrics = new HashMap<>();

        this.runRecommender(75);

        DBTools.storeRecommendationTrack(userRecs);
    }

    // Methods

    /**
     * @return userLikes The ArrayList of Songs that the user has provided
     */
    public ArrayList<Track> getUserLikes() {
        return userLikes;
    }

    public HashMap<String, Double> getUserLikesMetrics() {
        return userLikesMetrics;
    }

    public ArrayList<Track> getUserRecs() {
        return userRecs;
    }

    /**
     * @return songPool The ArrayList of Songs from which recommendations will
     * be pulled
     */
    public ArrayList<Track> getSongPool() {
        return songPool;
    }

    /**
     * @return songPoolScored The HashMap of containing the songs from songPool
     * and their similarity scores
     */
    public TreeMap<Double, Track> getSongPoolScored() {
        return songPoolScored;
    }

    /**
     * Places the artists of the different songs in userLikes into an ArrayList.
     * Retrieves the related artists of the user-provided artists. Retrieves the
     * top songs of those artists. Places the results into the songPool
     * ArrayList.
     */
    private void searchByRelatedArtists() {
        ArrayList<Artist> userArtists = new ArrayList<>();
        List<ArrayList<Artist>> relatedArtists = new ArrayList<>();
        for (Track song : userLikes) {
            if (!userArtists.contains(song.getArtists().get(0))) {
                userArtists.add(song.getArtists().get(0));
            }
        }
        for (Artist artist : userArtists) {
            relatedArtists
                    .add(SpotifyTools.getRelatedArtists(artist.getArtistID()));
        }
        poolingSongs:
        for (ArrayList<Artist> list : relatedArtists) {
            int artistCount = 0;
            for (Artist artist : list) {
                int songCount = 0;
                for (Track song : SpotifyTools
                        .getArtistTopTracks(artist.getArtistID())) {
                    if (!songPool.contains(song)) {
                        songPool.add(song);
                        songCount++;
                    }
                    if (songCount > 7) {
                        break;
                    }
                    if (songPool.size() > 200) {
                        break poolingSongs;
                    }
                }
                artistCount++;
                if (artistCount > 7) {
                    break;
                }
            }
        }
        // after song pool is initialized, fill its corresponding audio feature
        fillAudioFeaturePool();
    }

    private void fillAudioFeaturePool() {
        int size = songPool.size();
        audioFeaturesPool.addAll(
                SpotifyTools.getSeveralAudioFeaturesFromTracks(songPool));
    }

    /**
     * Calculates the average song feature scores for the user-liked songs.
     *
     */
    private void averageUserLikesMetrics() {
        double userLikesSize = userLikes.size();
        double acousticScore = 0;
        double danceScore = 0;
        double energyScore = 0;
        double instrumentalScore = 0;
        double loudScore = 0;
        double tempoScore = 0;
        double valenceScore = 0;

        for (AudioFeatures af : SpotifyTools.getSeveralAudioFeaturesFromTracks(userLikes)) {
            acousticScore += af.getAcousticness();
            danceScore += af.getDanceability();
            energyScore += af.getEnergy();
            instrumentalScore += af.getInstrumentalness();
            loudScore += af.getLoudness();
            tempoScore += af.getTempo();
        }

        acousticScore /= userLikesSize;
        danceScore /= userLikesSize;
        energyScore /= userLikesSize;
        instrumentalScore /= userLikesSize;
        loudScore /= userLikesSize;
        tempoScore /= userLikesSize;
        valenceScore /= userLikesSize;
        userLikesMetrics.put("Acousticness", acousticScore);
        userLikesMetrics.put("Danceability", danceScore);
        userLikesMetrics.put("Energy", energyScore);
        userLikesMetrics.put("Instrumentalness", instrumentalScore);
        userLikesMetrics.put("Loudness", loudScore);
        userLikesMetrics.put("Tempo", tempoScore);
        userLikesMetrics.put("Valence", valenceScore);
    }

    /**
     * Generates a similarity score for a song compared to the metrics in
     * averageUserLikesMetrics. A percentage difference calculation is performed
     * for each feature. The percentage differences are then summed in a
     * weighted manner to yield the overall track similarity score.
     *
     */
    private double generateSimilarityScore(AudioFeatures af) {
        double similarityScore;
        double acousticScore = (af.getAcousticness()
                - userLikesMetrics.get("Acousticness"))
                / ((af.getAcousticness() + userLikesMetrics.get("Acousticness"))
                / 2);
        double danceScore = (af.getDanceability()
                - userLikesMetrics.get("Danceability"))
                / ((af.getDanceability() + userLikesMetrics.get("Danceability"))
                / 2);
        double energyScore = (af.getEnergy() - userLikesMetrics.get("Energy"))
                / ((af.getEnergy() + userLikesMetrics.get("Energy")) / 2);
        double instrumentalScore = (af.getInstrumentalness()
                - userLikesMetrics.get("Instrumentalness"))
                / ((af.getInstrumentalness()
                + userLikesMetrics.get("Instrumentalness")) / 2);
        double loudScore = (af.getLoudness() - userLikesMetrics.get("Loudness"))
                / ((af.getLoudness() + userLikesMetrics.get("Loudness")) / 2);
        double tempoScore = (af.getTempo() - userLikesMetrics.get("Tempo"))
                / ((af.getTempo() + userLikesMetrics.get("Tempo")) / 2);
        double valenceScore = (af.getValence()
                - userLikesMetrics.get("Valence"))
                / ((af.getValence() + userLikesMetrics.get("Valence")) / 2);

        similarityScore = (acousticScore * 0.16) + (danceScore * 0.17)
                + (energyScore * 0.20) + (instrumentalScore * 0.11)
                + (loudScore * 0.10) + (tempoScore * 0.11)
                + (valenceScore * 0.15);
        return similarityScore;
    }

    /**
     * Loads the tracks from songPool into a TreeMap with the similarity score
     * for the track.
     *
     */
    private void loadRecScores() {
        for (int i = 0; i < songPool.size(); i++) {
            songPoolScored.put(generateSimilarityScore(audioFeaturesPool.get(i)), songPool.get(i));
        }
    }

    /**
     * Iterates through the TreeMap songPoolScored to return the songs with the
     * best (lowest value) similarity scores.
     *
     */
    private void bestRecommendations(int listSize) {
        int count = 0;
        for (Map.Entry<Double, Track> entry : songPoolScored.entrySet()) {
            if (count >= listSize) {
                break;
            }
            userRecs.add(entry.getValue());
            count++;
        }
    }

    private void runRecommender(int listSize) {
        averageUserLikesMetrics();
        searchByRelatedArtists();
        loadRecScores();
        bestRecommendations(listSize);
    }
}
