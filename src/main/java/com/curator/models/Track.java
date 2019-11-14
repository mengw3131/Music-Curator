import com.curator.tools.SpotifyTools;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;

import java.util.ArrayList;

/**
 * 
 * @author Luke, Isaac, Meng
 * 
 * This class stores the audio features of songs and allows the features
 * to be accessed by other classes.
 *
 */
public class Track {
	private String trackID;                     // Spotify track ID
	private String name;                        // name of the song

	private ArrayList<Artist> artists;    // the list of artists on the album
	private AlbumSimple album;                  // the album of the song

	private int popularity;                     // the popularity of a song (0 - 100)

	private AudioFeatures features;
	private double acousticness;                // confidence measure (0.0 - 1.0) of whether the song is acoustic
	private double danceability;                // 0.0 - 1.0 how danceable the song is
	private double energy;                      // 0.0 - 1.0 measure of how fast/loud/noisy the song is
	private double instrumentalness;            // 0.0 - 1.0 confidence measure of whether a track
	private double loudness;                    // overall loudness of the track in decibels
	private double tempo;                       // tempo of the track (Beats per minute)
	private double valence;                     // 0.0 - 1.0 describing the musical positiveness

	/**
	 * Construct Track object from wrapper's Track object
	 * @param sTrack spotify wrapper's Track object
	 */
	public Track(com.wrapper.spotify.model_objects.specification.Track sTrack) {
		this.trackID = sTrack.getId();
		this.name = sTrack.getName();
		this.popularity = sTrack.getPopularity();

		this.album = SpotifyTools.toAlbumSimple(sTrack.getAlbum());
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

	/**
	 * @return acousticness The confidence measure (0.0 - 1.0) of whether the song is acoustic
	 */
	public double getAcousticness() {
		return acousticness;
	}

	/**
	 * 
	 * @return danceability How danceable the song is (0.0 - 1.0)
	 */
	public double getDanceability() {
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
	 * @return artists com.com.curator.Artist of the track
	 */
	public ArrayList<Artist> getArtists() { return artists; }

	/**
	 * 
	 * @return album The object for the album that the song is on
	 */
	public AlbumSimple getAlbum() {
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
