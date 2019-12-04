package com.curator.tools;

import com.curator.models.Artist;
import com.curator.models.Genre;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Class containing static utility methods for interfacing with Spotify wrapper's objects
 */
public class SpotifyTools {
    //TODO: secure spotify API keys, store in config file OR prompt login
    private static String clientId = "f9c190003d09495d9915681495281934";
    private static String clientSecret = "e63dcad355af406aa0cfc516427095ec";
    public static SpotifyApi api = new SpotifyApi.Builder().setAccessToken(getAccessToken(clientId, clientSecret)).build();

    public static int calls_count = 0;

    /**
     * Prevent instance creation
     */
    private SpotifyTools() {
    }

    /**
     * Given Genre, returns artists of that genre.
     * @param genre Genre object, e.g. Genre.POP
     * @param limit number of artists to fetch
     * @return ArrayList of artists of the genre
     */
    public static ArrayList<com.curator.models.Artist> getArtistByGenre(Genre genre, int limit){
        ArrayList<com.curator.models.Artist> artists = new ArrayList<>();
        try {
            com.wrapper.spotify.model_objects.specification.Artist[] sArtists =
                    api.searchItem("genre:" + genre,
                    ModelObjectType.ARTIST.getType()).limit(limit).build()
                    .execute().getArtists().getItems();

            for (int i = 0; i < sArtists.length; i++) {
                artists.add(new Artist(sArtists[i]));
            }
        } catch (SpotifyWebApiException | IOException exception){
            exception.printStackTrace();
        }

        calls_count++;
        System.out.println(calls_count + " getArtistByGenre()");
        return artists;
    }

    /**
     * Given a track, returns arrayList of tracks that are similar
     * @param trackId the trackId of the track to be compared
     * @return arrayList of similar tracks
     */
    public static ArrayList<com.curator.models.Track> getRelatedTracks(String trackId){
        Artist artist = getTrack(trackId).getArtists().get(0);
        ArrayList<Artist> relatedArtist = getRelatedArtists(artist.getArtistID());
        ArrayList<com.curator.models.Track> relatedTracks = new ArrayList<>();

        for (Artist a: relatedArtist) {
            relatedTracks.addAll(getArtistTopTracks(a.getArtistID()));
        }
        Collections.shuffle(relatedTracks);

        calls_count++;
        System.out.println(calls_count + " getRelatedTracks()");
        return relatedTracks;
    }

    /**
     * Given artists with artistId, returns related artists
     * @param artistId artistsId of the artist to be searched
     * @return ArrayList of related artists
     */
    public static ArrayList<Artist> getRelatedArtists(String artistId){
        ArrayList<Artist> artists = new ArrayList<>();
        try {
            for (com.wrapper.spotify.model_objects.specification.Artist artist:
            api.getArtistsRelatedArtists(artistId).build().execute()) {
                artists.add(new Artist(artist));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }


        calls_count++;
        System.out.println(calls_count + " getRelatedArtists()");
        return artists;
    }


    /**
     * Return api instance
     *
     * @return wrapper's SpotifyApi instance
     */
    public static SpotifyApi getApi() {
        return api;
    }


    public static ArrayList<com.curator.models.Track> toTracks(TrackSimplified[] tsArr){
        ArrayList<com.curator.models.Track> tracks = new ArrayList<>();
        for (TrackSimplified ts: tsArr) {
            tracks.add(getTrack(ts.getId()));
        }


        return tracks;
    }

    /**
     * Given wrapper's object ArtistSimplified, convert to com.curator.models.Artist object
     *
     * @param sArtist wrapper's object ArtistSimplified
     * @return com.curator.models.Artist object of
     */
    public static com.curator.models.Artist toArtist(ArtistSimplified sArtist) {
        try {

            calls_count++;
            System.out.println(calls_count + " toArtist()");
            return new Artist(api.getArtist(sArtist.getId()).build().execute());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given array of wrapper's object ArtistSimplified, convert to ArrayList of com.curator.models.Artist objects
     *
     * @param sArtists array of wrapper's object ArtistSimplified
     * @return ArrayList of com.curator.models.Artist objects
     */
    public static ArrayList<Artist> toArtist(ArtistSimplified[] sArtists) {
        ArrayList<Artist> artists = new ArrayList<>();
        for (ArtistSimplified sArtist : sArtists) {
            artists.add(toArtist(sArtist));
        }


        return artists;
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
        try {
            calls_count++;
            System.out.println(calls_count + " getAudioFeatures()");
            return api.getAudioFeaturesForTrack(sTrackId).build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Given Spotify artistID, return Artist object
     *
     * @param artistID artistID of the artist
     * @return com.curator.models.Artist object
     */
    public static com.curator.models.Artist getArtist(String artistID) {
        try {
            calls_count++;
            System.out.println(calls_count + " getArtist()");
            return new Artist(api.getArtist(artistID).build().execute());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Given Spotify albumID, return Album object
     *
     * @param albumID albumID of the album
     * @return com.curator.models.Album object
     */
    public static com.curator.models.Album getAlbum(String albumID) {
        try {

            calls_count++;
            System.out.println(calls_count + " getAlbum()");
            return new com.curator.models.Album(api.getAlbum(albumID).build().execute());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Given Spotify trackID, return Track object
     *
     * @param trackID trackID of the track
     * @return com.curator.models.Track object
     */
    public static com.curator.models.Track getTrack(String trackID) {
        try {

            calls_count++;
            System.out.println(calls_count + " getTrack()");
            return new com.curator.models.Track(api.getTrack(trackID).build().execute());
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
     * Given query and limit, search and return results as arrayList of Track
     *
     * @param query track name
     * @param limit # of desired results, max 50
     * @return arrayList of Track
     */
    public static ArrayList<com.curator.models.Track> searchTracks(String query, int limit) {
        ArrayList<com.curator.models.Track> trackArr = new ArrayList<>();
        try {
            Paging<Track> tracks_paging =
                    api.searchTracks(query).limit(limit).build().execute();

            for (Track track : tracks_paging.getItems()) {
                trackArr.add(new com.curator.models.Track(track));
            }


            calls_count++;
            System.out.println(calls_count + " searchTrack()");
            return trackArr;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }


        calls_count++;
        return null;
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
            Paging<com.wrapper.spotify.model_objects.specification.Artist> artists_paging =
                    api.searchArtists(query).limit(limit).build().execute();

            for (com.wrapper.spotify.model_objects.specification.Artist artist : artists_paging.getItems()) {
                artistArr.add(new Artist(artist));
            }


            calls_count++;
            System.out.println(calls_count + " searchArtists()");
            return artistArr;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }


        calls_count++;
        return null;
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
            Paging<AlbumSimplified> albumsSimplified_paging = api.searchAlbums(query).limit(limit).build().execute();
            for (AlbumSimplified sAlbumSimplified : albumsSimplified_paging.getItems()) {
                albums.add(getAlbum(sAlbumSimplified.getId()));
            }


            calls_count++;
            System.out.println(calls_count + " searchAlbums()");
            return albums;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }


        calls_count++;
        return null;
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
            for (Track track :
                    api.getArtistsTopTracks(artistID, CountryCode.US).build().execute()) {
                tracks.add(new com.curator.models.Track(track));
            }


            calls_count++;
            System.out.println(calls_count + " getArtistTopTracks()");
            return tracks;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }


        calls_count++;
        return null;
    }

    /**
     * Given artistID, return arrayList of albums by artist
     *
     * @param artistID spotify's artistID
     * @param limit    # of albums desired, max 20
     * @return arrayList of albums
     */
    public static ArrayList<com.curator.models.Album> getArtistAlbums(String artistID, int limit) {
        ArrayList<com.curator.models.Album> albums = new ArrayList<>();

        try {
            for (AlbumSimplified album
                    : api.getArtistsAlbums(artistID).build().execute().getItems()) {
                albums.add(getAlbum(album.getId()));
            }


            calls_count++;
            System.out.println(calls_count + " getArtistAlbums()");
            return albums;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        }


        calls_count++;
        return null;
    }


        /**
         * Returns JavaFX Image object given url. Uses background thread.
         * @param url address of image resource
         * @return JavaFX Image object
         */
        public static javafx.scene.image.Image toImage (String url){
            //process image in background thread
            return new javafx.scene.image.Image(url, true);
        }

        /**
         * Returns JavaFX Image object given url
         * @param images ArrayList of wrapper's Image object
         * @return ArrayList of javafx.scene.image.Image
         */
        public static ArrayList<javafx.scene.image.Image> toImage
        (Image[]images){
            ArrayList<javafx.scene.image.Image> imageArr = new ArrayList<>();
            for (Image image : images) {
                imageArr.add(toImage(image.getUrl()));
            }
            return imageArr;
        }

        /**
         * Given an array of artists, return the comma-separated strings of the names of the artists
         * @param artistArr array of com.curator.models.Artist
         * @return comma-separated strings of the names of the artists
         */
        public static String toString (ArrayList <Artist> artistArr) {
            StringBuilder sb = new StringBuilder();
            for (Artist artist : artistArr) {
                sb.append(artist.getName()).append(", ");
            }
            String res = sb.toString();
            return res.substring(0, res.length() - 3); //exclude last separator
        }

        /**
         * Given an array of artists, return the comma-separated strings of the names of the artists
         * @param artistArr com.wrapper.spotify.model_objects.specification.ArtistSimplified
         * @return comma-separated strings of the names of the artists
         */
        public static String toString (ArtistSimplified[]artistArr){
            StringBuilder sb = new StringBuilder();
            for (ArtistSimplified artist : artistArr) {
                sb.append(artist.getName()).append(", ");
            }
            String res = sb.toString();
            return res.substring(0, res.length() - 2); //exclude last separator
        }
    }
