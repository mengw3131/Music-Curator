package com.curator.tools;

import com.curator.models.AlbumSimple;
import com.curator.models.Artist;
import com.curator.models.TrackSimple;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

//import static com.wrapper.spotify.SpotifyApi.getAccessToken;


/**
 * Class containing static utility methods for interfacing with Spotify wrapper's objects
 */
public class SpotifyTools {
    //TODO: secure spotify API keys, store in config file OR prompt login
    private static String clientId = "f9c190003d09495d9915681495281934";
    private static String clientSecret = "0608411b8e5b49e6b157504daccfbbcd";
    public static SpotifyApi api = new SpotifyApi.Builder().setAccessToken(getAccessToken(clientId, clientSecret)).build();

    /**
     * Prevent instance creation
     */
    private SpotifyTools() {
    }


    /**
     * Return api instance
     *
     * @return wrapper's SpotifyApi instance
     */
    public static SpotifyApi getApi() {
        return api;
    }

    /**
     * Given array of wrapper's object TrackSimplified, convert to ArrayList of com.com.curator.com.curator.models.TrackSimple objects
     *
     * @param sTracks array of wrapper's object TrackSimplified
     * @return arrayList of com.com.curator.com.curator.models.TrackSimple objects
     */
    public static ArrayList<TrackSimple> toTrackSimple(TrackSimplified[] sTracks) {
        ArrayList<TrackSimple> trackSimple = new ArrayList<>();
        for (TrackSimplified sTrack : sTracks) {
            trackSimple.add(toTrackSimple(sTrack));
        }
        return trackSimple;
    }

    /**
     * Given wrapper's TrackSimplified object, convert to com.com.curator.com.curator.models.TrackSimple objects
     *
     * @param sTrack wrapper's TrackSimplified object
     * @return com.com.curator.com.curator.models.TrackSimple objects
     */
    public static TrackSimple toTrackSimple(TrackSimplified sTrack) {
        try {
            return new TrackSimple(api.getTrack(sTrack.getId()).build().execute());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given wrapper's object ArtistSimplified, convert to com.com.curator.com.curator.models.Artist object
     *
     * @param sArtist wrapper's object ArtistSimplified
     * @return com.com.curator.com.curator.models.Artist object of
     */
    public static com.curator.models.Artist toArtist(ArtistSimplified sArtist) {
        try {
            return new Artist(api.getArtist(sArtist.getId()).build().execute());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given array of wrapper's object ArtistSimplified, convert to ArrayList of com.com.curator.com.curator.models.Artist objects
     *
     * @param sArtists array of wrapper's object ArtistSimplified
     * @return ArrayList of com.com.curator.com.curator.models.Artist objects
     */
    public static ArrayList<com.curator.models.Artist> toArtist(ArtistSimplified[] sArtists) {
        ArrayList<com.curator.models.Artist> artists = new ArrayList<>();
        for (com.wrapper.spotify.model_objects.specification.ArtistSimplified sArtist : sArtists) {
            artists.add(toArtist(sArtist));
        }
        return artists;
    }

    /**
     * Given array of wrapper's object AlbumSimplified, convert to ArrayList of com.com.curator.com.curator.models.Album objects
     *
     * @param sAlbums array of wrapper's object AlbumSimplified
     * @return ArrayList of com.com.curator.com.curator.models.Album objects
     */
    public static ArrayList<com.curator.models.AlbumSimple> toAlbumSimple(AlbumSimplified[] sAlbums) {
        ArrayList<com.curator.models.AlbumSimple> albums = new ArrayList<>();
        for (com.wrapper.spotify.model_objects.specification.AlbumSimplified sAlbum : sAlbums) {
            albums.add(toAlbumSimple(sAlbum));
        }
        return albums;
    }

    /**
     * Given wrapper's object AlbumSimplified, convert to com.com.curator.com.curator.models.Album object
     *
     * @param sAlbum wrapper's object AlbumSimplified
     * @return com.com.curator.com.curator.models.Album object
     */
    public static com.curator.models.AlbumSimple toAlbumSimple(AlbumSimplified sAlbum) {
        try {
            return new AlbumSimple(api.getAlbum(sAlbum.getId()).build().execute());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given wrapper's object com.curator.models.Track, get AudioFeatures object
     *
     * @param track wrapper's object com.curator.models.Track
     * @return AudioFeatures object corresponding to the wrapper's com.curator.models.Track object
     */
    public static AudioFeatures getAudioFeatures(com.wrapper.spotify.model_objects.specification.Track track) {
        return getAudioFeatures(track.getId());
    }

    /**
     * Given com.com.curator com.curator.models.Track object, get its AudioFeatures object
     *
     * @param track com.curator.models.Track object
     * @return AudioFeatures object of the com.curator.models.Track object
     */
    public static AudioFeatures getAudioFeatures(com.curator.models.Track track) {
        return getAudioFeatures(track.getTrackID());
    }

    /**
     * Given com.com.curator com.curator.models.Track object, get its AudioFeatures object
     *
     * @param trackSimple com.com.curator com.curator.models.TrackSimple object
     * @return AudioFeatures object of the com.curator.models.Track object
     */
    public static AudioFeatures getAudioFeatures(TrackSimple trackSimple) {
        return getAudioFeatures(trackSimple.getTrackID());
    }


    /**
     * Given Spotify track id, return its AudioFeatures object
     *
     * @param sTrackId Spotify track id
     * @return AudioFeatures object of track with sTrackId
     */
    public static AudioFeatures getAudioFeatures(String sTrackId) {
        try {
            return api.getAudioFeaturesForTrack(sTrackId).build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Get access token given clientId and clientSecret
     *
     * @param clientId
     * @param clientSecret
     * @return access token
     */
    public static String getAccessToken(String clientId, String clientSecret) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            return clientCredentials.getAccessToken();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Get arrays of tracks given query and limit (max 50)
     *
     * @param query
     * @param limit
     * @return track arrays
     */
    public static ArrayList<com.curator.models.Track> searchTracks(String query, int limit) {
        ArrayList<com.curator.models.Track> trackArr = new ArrayList<>();
        try {
            Paging<com.wrapper.spotify.model_objects.specification.Track> tracks_paging =
                    api.searchTracks(query).limit(limit).build().execute();
            for (com.wrapper.spotify.model_objects.specification.Track track: tracks_paging.getItems()) {
                trackArr.add(new com.curator.models.Track(track));
            }
            return trackArr;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<com.curator.models.Artist> searchArtists(String query, int limit) {
        ArrayList<com.curator.models.Artist> artistArr = new ArrayList<>();
        try {
            Paging<com.wrapper.spotify.model_objects.specification.Artist> artists_paging =
                    api.searchArtists(query).limit(limit).build().execute();

            for (com.wrapper.spotify.model_objects.specification.Artist artist: artists_paging.getItems()) {
                artistArr.add(new Artist(artist));
            }
            return artistArr;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<AlbumSimple> searchAlbums(String query, int limit) {
        ArrayList<AlbumSimple> albumSimpleArr = new ArrayList<>();
        try {
            Paging<AlbumSimplified> albumsSimplified_paging = api.searchAlbums(query).limit(limit).build().execute();
            for (AlbumSimplified sAlbumSimplified: albumsSimplified_paging.getItems()) {
                albumSimpleArr.add(toAlbumSimple(sAlbumSimplified));
            }
            return albumSimpleArr;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Returns JavaFX Image object given url
     * @param url address of image resource
     * @return JavaFX Image object
     */
    public static javafx.scene.image.Image toImage(String url){
        try {
            return SwingFXUtils.toFXImage(ImageIO.read(new URL(url)), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns JavaFX Image object given url
     * @param images ArrayList of wrapper's Image object
     * @return ArrayList of javafx.scene.image.Image
     */
    public static ArrayList<javafx.scene.image.Image> toImage(com.wrapper.spotify.model_objects.specification.Image[] images){
        ArrayList<javafx.scene.image.Image> imageArr = new ArrayList<>();
        for (Image image: images) {
            imageArr.add(toImage(image.getUrl()));
        }
        return imageArr;
    }

    public static String toString(ArrayList<Artist> artistArr){
        StringBuilder sb = new StringBuilder();
        for (Artist artist: artistArr) {
            sb.append(artist.getName()).append(", ");
        }
        String res = sb.toString();
        return res.substring(0,res.length() - 3); //exclude last separator
    }
}
