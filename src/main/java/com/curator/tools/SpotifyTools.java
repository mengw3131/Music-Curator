package com.curator.tools;

import com.curator.models.Artist;
import com.curator.models.Genre;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class containing static utility methods for interfacing with Spotify wrapper's objects
 */
public class SpotifyTools {
    private static final String clientId = "f9c190003d09495d9915681495281934";
    private static final String clientSecret = "e63dcad355af406aa0cfc516427095ec";
    private static final SpotifyApi api = new SpotifyApi.Builder().setAccessToken(getAccessToken(clientId, clientSecret)).build();
    private static int calls_count = 0;

    private final static Logger LOGGER = getLogger();


    /**
     * Prevent instance creation
     */
    private SpotifyTools() {
    }

    //=====================================
    // API PROPERTIES
    //=====================================


    /**
     * Return api instance
     *
     * @return wrapper's SpotifyApi instance
     */
    public static SpotifyApi getApi() {
        return api;
    }

    /**
     * Get access token given clientId and clientSecret
     *
     * @param clientId Spotify clientId
     * @param clientSecret Spotify clientSecret
     * @return access token
     */
    private static String getAccessToken(String clientId, String clientSecret) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            return clientCredentials.getAccessToken();
        } catch (SpotifyWebApiException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Logger getLogger() {
        Logger logger = Logger.getLogger(SpotifyTools.class.getName());
//        logger.setLevel(Level.INFO);
        logger.setLevel(Level.OFF); ///turn off loggers
        return logger;
    }

    //=====================================
    // SEARCH METHODS
    //=====================================

    /**
     * Given query and limit, search and return results as arrayList of Track
     *
     * @param query track name
     * @param limit # of desired results, max 50
     * @return arrayList of Track
     */
    public static ArrayList<com.curator.models.Track> searchTracks(String query, int limit) {
        ArrayList<com.curator.models.Track> trackArr = new ArrayList<>();
        try {
            for (Track track : api.searchTracks(query).limit(limit).build().execute().getItems()) {
                trackArr.add(new com.curator.models.Track(track));
            }
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " searchTrack(), limit: " + limit);
        return trackArr;
    }

    /**
     * Given query string and limit, search and return results as arrayList of Artist
     *
     * @param query artist's name
     * @param limit # of desired result, max 50
     * @return arrayList of Artist
     */
    public static ArrayList<Artist> searchArtists(String query, int limit) {
        ArrayList<Artist> artistArr = new ArrayList<>();
        try {
            for (com.wrapper.spotify.model_objects.specification.Artist artist :
                    api.searchArtists(query).limit(limit).build().execute().getItems()) {
                artistArr.add(new Artist(artist));
            }
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " searchArtists(), limit: " + limit);
        return artistArr;
    }

    /**
     * Given query string and limit, search and return results as arrayList of Album
     *
     * @param query string query, e.g. Rondo Alla Turca Mozart
     * @param limit # of desired results, max 50
     * @return arrayList of Album
     */
    public static ArrayList<com.curator.models.Album> searchAlbums(String query, int limit) {
        ArrayList<com.curator.models.Album> albums = new ArrayList<>();
        try {
            ArrayList<String> ids = toIdArrayList(
                    api.searchAlbums(query).limit(limit).build().execute().getItems());
            albums = getSeveralAlbums(ids);
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " searchAlbums(), limit: " + limit);
        return albums;
    }

    //===================================================================
    //===================================================================
    // GETTERS
    //===================================================================
    //===================================================================

    /**
     * Given Spotify artistID, return Artist object
     *
     * @param artistID artistID of the artist
     * @return com.curator.models.Artist object
     */
    public static com.curator.models.Artist getArtist(String artistID) {
        com.curator.models.Artist artist = null;
        try {
            artist = new Artist(api.getArtist(artistID).build().execute());
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " getArtist()");
        return artist;
    }


    /**
     * Given Spotify albumID, return Album object
     *
     * @param albumID albumID of the album
     * @return com.curator.models.Album object
     */
    public static com.curator.models.Album getAlbum(String albumID) {
        com.curator.models.Album album = null;
        try {
            album = new com.curator.models.Album(api.getAlbum(albumID).build().execute());
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " getAlbum()");
        return album;
    }

    /**
     * Given Spotify trackID, return Track object
     *
     * @param trackID trackID of the track
     * @return com.curator.models.Track object
     */
    public static com.curator.models.Track getTrack(String trackID) {
        com.curator.models.Track track = null;
        try {
            track = new com.curator.models.Track(api.getTrack(trackID).build().execute());
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " getTrack()");
        return track;
    }

    /**
     * Given wrapper's object com.curator.models.Track, get AudioFeatures object
     *
     * @param track wrapper's object com.curator.models.Track
     * @return AudioFeatures object corresponding to the wrapper's com.curator.models.Track object
     */
    public static AudioFeatures getAudioFeatures(Track track) {
        return getAudioFeatures(track.getId());
    }

    /**
     * Given com.curator.models.Track object, get its AudioFeatures object
     *
     * @param track com.curator.models.Track object
     * @return AudioFeatures object of the com.curator.models.Track object
     */
    public static AudioFeatures getAudioFeatures(com.curator.models.Track track) {
        return getAudioFeatures(track.getTrackID());
    }

    /**
     * Given Spotify track id, return its AudioFeatures object
     *
     * @param sTrackId Spotify track id
     * @return AudioFeatures object of track with sTrackId
     */
    public static AudioFeatures getAudioFeatures(String sTrackId) {
        AudioFeatures af = null;
        try {
            af = api.getAudioFeaturesForTrack(sTrackId).build().execute();
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " getAudioFeatures()");
        return af;
    }

    /**
     * Given artistID, return top tracks by artist
     *
     * @param artistID spotify's artist ID
     * @return arrayList of top Tracks by artist
     */
    public static ArrayList<com.curator.models.Track> getArtistTopTracks(String artistID) {
        ArrayList<com.curator.models.Track> tracks = new ArrayList<>();
        try {
            for (Track track : api.getArtistsTopTracks(artistID, CountryCode.US).build().execute()) {
                tracks.add(new com.curator.models.Track(track));
            }
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count
                + " getArtistTopTracks(), size: " + tracks.size());
        return tracks;
    }

    /**
     * Given Genre, returns artists of that genre.
     *
     * @param genre Genre object, e.g. Genre.POP
     * @param limit number of artists to fetch
     * @return ArrayList of artists of the genre
     */
    public static ArrayList<com.curator.models.Artist> getArtistByGenre(Genre genre, int limit) {
        ArrayList<com.curator.models.Artist> artists = new ArrayList<>();
        try {
            com.wrapper.spotify.model_objects.specification.Artist[] sArtists =
                    api.searchItem("genre:" + genre,
                            ModelObjectType.ARTIST.getType()).limit(limit).build()
                            .execute().getArtists().getItems();

            for (int i = 0; i < sArtists.length; i++) {
                artists.add(new Artist(sArtists[i]));
            }
        } catch (SpotifyWebApiException | IOException exception) {
            exception.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count
                + " getArtistByGenre(), size: " + artists.size());
        return artists;
    }

    /**
     * Given a track, returns arrayList of tracks that belongs to the same album
     *
     * @param trackId the trackId of the track to be compared
     * @return arrayList of similar tracks
     */
    public static ArrayList<com.curator.models.Track> getRelatedTracks(String trackId) {
        return getTrack(trackId).getAlbum().getTracks();
    }

    /**
     * Given artists with artistId, returns related artists
     *
     * @param artistId artistsId of the artist to be searched
     * @return ArrayList of related artists
     */
    public static ArrayList<Artist> getRelatedArtists(String artistId) {
        ArrayList<Artist> artists = new ArrayList<>();
        try {
            for (com.wrapper.spotify.model_objects.specification.Artist artist :
                    api.getArtistsRelatedArtists(artistId).build().execute()) {
                artists.add(new Artist(artist));
            }
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }

        LOGGER.info("sAPI Call #" + ++calls_count
                + " getRelatedArtists(), size: " + artists.size());
        return artists;
    }

    /**
     * Given artistID, return arrayList of albums by artist
     *
     * @param artistID spotify's artistID
     * @param limit    # of albums desired, max 20
     * @return arrayList of albums
     */
    public static ArrayList<com.curator.models.Album> getArtistAlbums(String artistID, int limit) {
        ArrayList<String> ids = null;
        try {
            ids = toIdArrayList(api.getArtistsAlbums(artistID).build().execute().getItems());
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " getArtistAlbums(), limit: " + limit);
        assert ids != null;
        return getSeveralAlbums(ids);
    }

    /**
     * Given ArrayList of multiple albumIDs, return ArrayList of Album objects in single call
     *
     * @param albumIDs ArrayList of the albumIDs of the albums
     * @return ArrayList of Album objects
     */
    public static ArrayList<com.curator.models.Album> getSeveralAlbums(ArrayList<String> albumIDs) {
        ArrayList<com.curator.models.Album> albums = new ArrayList<>();
        if (albumIDs.size() == 0){
            return albums;
        }
        int size = albumIDs.size();
        final int MAX_BUFFER = 20;

        if (size <= MAX_BUFFER){
            try {
                LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralAlbums(), size : " + size);
                com.wrapper.spotify.model_objects.specification.Album[] albumArr
                        = api.getSeveralAlbums(toIdArray(albumIDs)).build().execute();
                for (com.wrapper.spotify.model_objects.specification.Album album: albumArr) {
                    albums.add(new com.curator.models.Album(album));
                }
            } catch (IOException | SpotifyWebApiException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < size; i += MAX_BUFFER) {
                if (i + MAX_BUFFER < size) {
                    LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralTracks(), size : " + MAX_BUFFER);
                    albums.addAll(SpotifyTools.getSeveralAlbums(new ArrayList<>(albumIDs.subList(i, i + MAX_BUFFER))));
                } else {
                    LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralTracks(), size : " + (size - i));
                    albums.addAll(SpotifyTools.getSeveralAlbums(new ArrayList<>(albumIDs.subList(i, size))));
                    break;
                }
            }
        }
        return albums;
    }

    /**
     * Given ArrayList of multiple trackIDs, return ArrayList of Track objects in single call
     *
     * @param trackIDs ArrayList of the trackIDs of the tracks
     * @return ArrayList of Track objects
     */
    public static ArrayList<com.curator.models.Track> getSeveralTracks(ArrayList<String> trackIDs) {
        ArrayList<com.curator.models.Track> tracks = new ArrayList<>();
        if (trackIDs.size() == 0){
            return tracks;
        }
        int size = trackIDs.size();
        final int MAX_BUFFER = 50;

        if (size <= MAX_BUFFER){
            try {
                LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralTracks(), size : " + size);
                com.wrapper.spotify.model_objects.specification.Track[] trackArr
                        = api.getSeveralTracks(toIdArray(trackIDs)).build().execute();
                for (com.wrapper.spotify.model_objects.specification.Track track: trackArr) {
                    tracks.add(new com.curator.models.Track(track));
                }
            } catch (IOException | SpotifyWebApiException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < size; i += MAX_BUFFER) {
                if (i + MAX_BUFFER < size) {
                    LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralTracks(), size : " + MAX_BUFFER);
                    tracks.addAll(SpotifyTools.getSeveralTracks(new ArrayList<>(trackIDs.subList(i, i + MAX_BUFFER))));
                } else {

                    LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralTracks(), size : " + (size - i));
                    tracks.addAll(SpotifyTools.getSeveralTracks( new ArrayList<>(trackIDs.subList(i, size))));
                    break;
                }
            }
        }
        return tracks;
    }

    /**
     * Given ArrayList of multiple artistIDs, return ArrayList of Artist objects in single call
     *
     * @param artistsIDs ArrayList of the artistIDs of the albums
     * @return ArrayList of Artist objects
     */
    public static ArrayList<com.curator.models.Artist> getSeveralArtists(ArrayList<String> artistsIDs) {
        ArrayList<Artist> artists = new ArrayList<>();
        if (artistsIDs.size() == 0){
            return artists;
        }
        int size = artistsIDs.size();
        final int MAX_BUFFER = 50;

        if (size <= MAX_BUFFER){
            try {
                LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralArtists(), size : " + size);
                com.wrapper.spotify.model_objects.specification.Artist[] artistArr
                        = api.getSeveralArtists(toIdArray(artistsIDs)).build().execute();
                for (com.wrapper.spotify.model_objects.specification.Artist artist: artistArr) {
                    artists.add(new com.curator.models.Artist(artist));
                }
            } catch (IOException | SpotifyWebApiException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < size; i += MAX_BUFFER) {
                if (i + MAX_BUFFER < size) {
                    LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralArtists(), size : " + MAX_BUFFER);
                    artists.addAll(SpotifyTools.getSeveralArtists( new ArrayList<>(artistsIDs.subList(i, i + MAX_BUFFER))));
                } else {
                    LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralArtists(), size : " + (size - i));
                    artists.addAll(SpotifyTools.getSeveralArtists(new ArrayList<>(artistsIDs.subList(i, size))));
                    break;
                }
            }
        }
        return artists;
    }

    /**
     * Given ArrayList of Tracks, return ArrayList of the corresponding AudioFeatures
     *
     * @param tracks ArrayList of the tracks
     * @return ArrayList of AudioFeatures of the tracks
     */
    public static ArrayList<AudioFeatures> getSeveralAudioFeaturesFromTracks(ArrayList<com.curator.models.Track> tracks) {
        return getSeveralAudioFeaturesFromTrackIDs(toIdArray(tracks));
    }

    /**
     * Given ArrayList of trackIDs, return ArrayList of the corresponding AudioFeatures
     *
     * @param trackIDs the trackIDs of the tracks
     * @return ArrayList of AudioFeatures of the tracks with trackIDs
     */
    public static ArrayList<AudioFeatures> getSeveralAudioFeaturesFromTrackIDs(ArrayList<String> trackIDs) {
        return getSeveralAudioFeaturesFromTrackIDs(toIdArray(trackIDs));
    }

    /**
     * Given string array of trackIDs, return ArrayList of th corresponding AudioFeatures
     *
     * @param trackIDs the trackIDs of the tracks
     * @return ArrayList of AudioFeatures of the tracks with trackIDs
     */
    private static ArrayList<AudioFeatures> getSeveralAudioFeaturesFromTrackIDs(String[] trackIDs) {
        ArrayList<AudioFeatures> afArr = new ArrayList<>();
        ArrayList<String> ids = toIdArrayList(trackIDs);
        int size = ids.size();

        if (size <= 100){
            try {
                LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralAudioFeaturesFromTrackIDs(), size : " + size);
                afArr = new ArrayList<>(Arrays.asList(api.getAudioFeaturesForSeveralTracks(trackIDs)
                        .build().execute()));
            } catch (IOException | SpotifyWebApiException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < size; i += 100) {
                if (i + 100 < size) {
                    LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralAudioFeaturesFromTrackIDs(), size : " + 100);
                    afArr.addAll(SpotifyTools.getSeveralAudioFeaturesFromTrackIDs(
                            new ArrayList<>(ids.subList(i, i + 100))));
                } else {
                    LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralAudioFeaturesFromTrackIDs(), size : " + (size - i));
                    afArr.addAll(SpotifyTools.getSeveralAudioFeaturesFromTrackIDs(
                            new ArrayList<>(ids.subList(i, size))));
                    break;
                }
            }
        }
        LOGGER.info("sAPI Call #" + ++calls_count + " getSeveralAudioFeaturesFromTrackIDs(), size : " + size);
        return afArr;
    }

    //===================================================================
    //===================================================================
    // OBJECT MODELS CONVERSION METHODS
    //===================================================================
    //===================================================================

    /**
     * Convert ArrayList of TrackSimplified object to ArrayList of Track
     *
     * @param tsArr ArrayList of TrackSimplified object to be converted
     * @return the corresponding ArrayList of Track
     */
    public static ArrayList<com.curator.models.Track> toTracks(TrackSimplified[] tsArr) {
        return getSeveralTracks(toIdArrayList(tsArr));
    }

    /**
     * Given array of wrapper's object ArtistSimplified, convert to ArrayList of com.curator.models.Artist objects
     *
     * @param sArtists array of wrapper's object ArtistSimplified
     * @return ArrayList of com.curator.models.Artist objects
     */
    public static ArrayList<Artist> toArtists(ArtistSimplified[] sArtists) {
        return getSeveralArtists(toIdArrayList(sArtists));
    }

    /**
     * Returns JavaFX Image object given url. Uses background thread.
     *
     * @param url address of image resource
     * @return JavaFX Image object
     */
    public static javafx.scene.image.Image toImage(String url) {
        //process image in background thread
        return new javafx.scene.image.Image(url, true);
    }

    /**
     * Returns JavaFX Image object given url
     *
     * @param images ArrayList of wrapper's Image object
     * @return ArrayList of javafx.scene.image.Image
     */
    public static ArrayList<javafx.scene.image.Image> toImage (Image[] images) {
        ArrayList<javafx.scene.image.Image> imageArr = new ArrayList<>();
        for (Image image : images) {
            imageArr.add(toImage(image.getUrl()));
        }
        return imageArr;
    }

    /**
     * Given an array of artists, return the comma-separated strings of the names of the artists
     *
     * @param artistArr com.wrapper.spotify.model_objects.specification.ArtistSimplified
     * @return comma-separated strings of the names of the artists
     */
    public static String toArtistName(ArtistSimplified[] artistArr) {
        StringBuilder sb = new StringBuilder();
        for (ArtistSimplified artist : artistArr) {
            sb.append(artist.getName()).append(", ");
        }
        String res = sb.toString();
        return res.substring(0, res.length() - 2); //exclude last separator
    }

    /**
     * Given ArrayList of Tracks, Albums, Artists, or ids,
     * return the string array of the ids of the objects
     *
     * @param items ArrayList of Tracks, Albums, Artists, or ids (String)
     * @return the string array of the ids of the objects
     */
    private static ArrayList<String> toIdArrayList(Object[] items) {
        int size = items.length;
        ArrayList<String> ids = new ArrayList<>();
        if (size == 0) {
            return ids;
        }
        if (items[0] instanceof com.curator.models.Track) {
            for (int i = 0; i < size; i++) {
                ids.add(((com.curator.models.Track) items[i]).getTrackID());
            }
        } else if (items[0] instanceof com.curator.models.Album) {
            for (int i = 0; i < size; i++) {
                ids.add(((com.curator.models.Album) items[i]).getAlbumID());
            }
        } else if (items[0] instanceof com.curator.models.Artist) {
            for (int i = 0; i < size; i++) {
                ids.add(((com.curator.models.Artist) items[i]).getArtistID());
            }
        } else if (items[0] instanceof String) {
            for (int i = 0; i < size; i++) {
                ids.add(((String) items[i]));
            }
        } else if (items[0] instanceof AlbumSimplified) {
            for (int i = 0; i < size; i++) {
                ids.add(((AlbumSimplified) items[i]).getId());
            }
        } else if (items[0] instanceof TrackSimplified) {
            for (int i = 0; i < size; i++) {
                ids.add(((TrackSimplified) items[i]).getId());
            }
        } else if (items[0] instanceof ArtistSimplified) {
            for (int i = 0; i < size; i++) {
                ids.add(((ArtistSimplified) items[i]).getId());
            }
        }
        return ids;
    }

    /**
     * Given ArrayList of Tracks, Albums, Artists, or ids,
     * return the string array of the ids of the objects
     *
     * @param items ArrayList of Tracks, Albums, Artists, or ids (String)
     * @return the string array of the ids of the objects
     */
    private static String[] toIdArray(ArrayList items) {
        int size = items.size();
        String[] ids = new String[size];
        if (items.size() == 0) {
            return new String[]{};
        }
        if (items.get(0) instanceof com.curator.models.Track) {
            for (int i = 0; i < size; i++) {
                ids[i] = ((com.curator.models.Track) items.get(i)).getTrackID();
            }
        } else if (items.get(0) instanceof com.curator.models.Album) {
            for (int i = 0; i < size; i++) {
                ids[i] = ((com.curator.models.Album) items.get(i)).getAlbumID();
            }
        } else if (items.get(0) instanceof com.curator.models.Artist) {
            for (int i = 0; i < size; i++) {
                ids[i] = ((com.curator.models.Artist) items.get(i)).getArtistID();
            }
        } else if (items.get(0) instanceof String) {
            for (int i = 0; i < size; i++) {
                ids[i] = ((String) items.get(i));
            }
        } else if (items.get(0) instanceof AlbumSimplified) {
            for (int i = 0; i < size; i++) {
                ids[i] = ((AlbumSimplified) items.get(i)).getId();
            }
        } else if (items.get(0) instanceof TrackSimplified) {
            for (int i = 0; i < size; i++) {
                ids[i] = ((TrackSimplified) items.get(i)).getId();
            }
        } else if (items.get(0) instanceof ArtistSimplified) {
            for (int i = 0; i < size; i++) {
                ids[i] = ((ArtistSimplified) items.get(i)).getId();
            }
        }
        return ids;
    }

    /**
     * Given ArrayList of Tracks, Albums, Artists, or ids,
     * return the arrayList of the ids of the objects
     *
     * @param items ArrayList of Tracks, Albums, Artists, or ids (String)
     * @return the string array of the ids of the objects
     */
    public static ArrayList toIdArrayList(ArrayList items) {
        return new ArrayList<>(Arrays.asList(toIdArray(items)));
    }
}
