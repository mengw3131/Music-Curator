
import com.curator.tools.SpotifyTools;
import com.curator.models.*;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

/*

 Test Coverage
 +---------------+-------------+------+-------+---------------+
 | Element       | Class, %    | Method, %    | Line %        |
 +---------------+-------------+------+-------+---------------+
 | SpotifyTools  | 100% (1/1)  | 90% (29/32)  | 67% (155/229) |
 +---------------+-------------+------+-------+---------------+

*/

/**
 * Unit tests for SpotifyTools class
 */
public class SpotifyToolsTest {
    public ArrayList<Track> sampleTrack = SpotifyTools.searchTracks("Jazz", 50);
    public ArrayList<String> sampleTrackIDs = SpotifyTools.toIdArrayList(sampleTrack);
    public ArrayList<Album> sampleAlbum = SpotifyTools.searchAlbums("Love", 20);
    public ArrayList<String> sampleAlbumIDs = SpotifyTools.toIdArrayList(sampleAlbum);
    public ArrayList<Artist> sampleArtist = SpotifyTools.searchArtists("John", 20);
    public ArrayList<String> sampleArtistIDs = SpotifyTools.toIdArrayList(sampleArtist);

    private void sleep() {
        try {
            System.out.println("Sleep for 2 second");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void giveAPIaBreak() {
        sleep();
    }

    @Test
    public void getArtist_returnsCorrectArtist(){
        Assertions.assertEquals(
                sampleArtist.get(0), SpotifyTools.getArtist(sampleArtist.get(0).getArtistID()));
    }

    @Test
    public void getAlbum_returnsCorrectAlbum(){
        Assertions.assertEquals(
                sampleAlbum.get(0), SpotifyTools.getAlbum(sampleAlbum.get(0).getAlbumID()));
    }

    @Test
    public void getAudioFeatures_returnsCorrectAudioFeatures(){
        Track track = sampleTrack.get(0);
        AudioFeatures expected = track.getAudioFeatures();
        Assertions.assertEquals(SpotifyTools.getAudioFeatures(track).getId(), expected.getId());

        try {
            com.wrapper.spotify.model_objects.specification.Track sTrack =
                   SpotifyTools.getApi().getTrack(track.getTrackID()).build().execute();

            Assertions.assertEquals(SpotifyTools.getAudioFeatures(sTrack).getId(),
                    expected.getId());
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getArtistTopTracks_returnsArtistTopTracks(){
        Artist artist= SpotifyTools.searchArtists("John Mayer", 1).get(0);

        ArrayList<String> trackNames = new ArrayList<>();
        for (Track track: SpotifyTools.getArtistTopTracks(artist.getArtistID())) {
            trackNames.add(track.getTrackName());
        }
        Assertions.assertTrue(trackNames.contains("New Light"));
        Assertions.assertTrue(trackNames.contains("Your Body Is a Wonderland"));
        Assertions.assertTrue(trackNames.contains("Slow Dancing in a Burning Room"));
        Assertions.assertTrue(trackNames.contains("Gravity"));
    }

    @Test
    public void getRelatedTracks_returnsRelatedTracks() {
        Track baseTrack = SpotifyTools.searchTracks("Billie Jean", 1).get(0);

        ArrayList<String> relatedTrackNames = new ArrayList<>();
        for (Track track: SpotifyTools.getRelatedTracks(baseTrack.getTrackID())) {
            relatedTrackNames.add(track.getTrackName());
        }
        Assertions.assertTrue(relatedTrackNames.contains("Human Nature"));
        Assertions.assertTrue(relatedTrackNames.contains("Thriller"));
        Assertions.assertTrue(relatedTrackNames.contains("Wanna Be Startin' Somethin'"));
    }

    @Test
    public void getArtistAlbums_returnsCorrectAlbums(){
        Artist artist = SpotifyTools.searchArtists("Bob Marley", 1).get(0);
        ArrayList<Album> albums = SpotifyTools.getArtistAlbums(artist.getArtistID(), 10);
        ArrayList<String> names = new ArrayList<>();
        for (Album album : albums) {
            names.add(album.getName());
        }
        Assertions.assertTrue(names.contains("B Is For Bob"));
        Assertions.assertTrue(names.contains("Chant Down Babylon"));
        Assertions.assertTrue(names.contains("Dreams Of Freedom"));
        Assertions.assertTrue(names.contains("Exodus 40"));
    }


    @Test
    public void getSeveralAudioFeatures_returnsCorrectAudioFeatures() {
        ArrayList<AudioFeatures> actual = SpotifyTools.getSeveralAudioFeaturesFromTrackIDs(
                new ArrayList<>(sampleTrackIDs.subList(0, 20)));

        ArrayList<AudioFeatures> expected = new ArrayList<>();
        for (String id : sampleTrackIDs.subList(0, 20)) {
            expected.add(SpotifyTools.getAudioFeatures(id));
        }

        final double DELTA = 0.1;
        for (int i = 0; i < 20; i++) {
            Assertions.assertEquals(expected.get(i).getAcousticness(), actual.get(i).getAcousticness(), DELTA);
            Assertions.assertEquals(expected.get(i).getAnalysisUrl(), actual.get(i).getAnalysisUrl());
            Assertions.assertEquals(expected.get(i).getDanceability(), actual.get(i).getDanceability(), DELTA);
            Assertions.assertEquals(expected.get(i).getEnergy(), actual.get(i).getEnergy(), DELTA);
            Assertions.assertEquals(expected.get(i).getId(), actual.get(i).getId());
            Assertions.assertEquals(expected.get(i).getDurationMs(), actual.get(i).getDurationMs());
            Assertions.assertEquals(expected.get(i).getInstrumentalness(), actual.get(i).getInstrumentalness(), DELTA);
            Assertions.assertEquals(expected.get(i).getKey(), actual.get(i).getKey());
            Assertions.assertEquals(expected.get(i).getLiveness(), actual.get(i).getLiveness(), DELTA);
            Assertions.assertEquals(expected.get(i).getLoudness(), actual.get(i).getLoudness(), DELTA);
            Assertions.assertEquals(expected.get(i).getMode(), actual.get(i).getMode());
            Assertions.assertEquals(expected.get(i).getSpeechiness(), actual.get(i).getSpeechiness(), DELTA);
            Assertions.assertEquals(expected.get(i).getTempo(), actual.get(i).getTempo(), DELTA);
            Assertions.assertEquals(expected.get(i).getTimeSignature(), actual.get(i).getTimeSignature());
            Assertions.assertEquals(expected.get(i).getTrackHref(), actual.get(i).getTrackHref());
            Assertions.assertEquals(expected.get(i).getType(), actual.get(i).getType());
            Assertions.assertEquals(expected.get(i).getUri(), actual.get(i).getUri());
            Assertions.assertEquals(expected.get(i).getValence(), actual.get(i).getValence());
        }
    }

    @Test
    public void getSeveralAudioFeaturesFromTracks_returnsCorrectAudioFeatures() {
        ArrayList<AudioFeatures> actual = SpotifyTools.getSeveralAudioFeaturesFromTracks(
                new ArrayList(sampleTrack.subList(0, 20)));

        ArrayList<AudioFeatures> expected = new ArrayList<>();
        for (String id : sampleTrackIDs.subList(0, 20)) {
            expected.add(SpotifyTools.getAudioFeatures(id));
        }

        final double DELTA = 0.1;
        for (int i = 0; i < 20; i++) {
            Assertions.assertEquals(expected.get(i).getAcousticness(), actual.get(i).getAcousticness(), DELTA);
            Assertions.assertEquals(expected.get(i).getAnalysisUrl(), actual.get(i).getAnalysisUrl());
            Assertions.assertEquals(expected.get(i).getDanceability(), actual.get(i).getDanceability(), DELTA);
            Assertions.assertEquals(expected.get(i).getEnergy(), actual.get(i).getEnergy(), DELTA);
            Assertions.assertEquals(expected.get(i).getId(), actual.get(i).getId());
            Assertions.assertEquals(expected.get(i).getDurationMs(), actual.get(i).getDurationMs());
            Assertions.assertEquals(expected.get(i).getInstrumentalness(), actual.get(i).getInstrumentalness(), DELTA);
            Assertions.assertEquals(expected.get(i).getKey(), actual.get(i).getKey());
            Assertions.assertEquals(expected.get(i).getLiveness(), actual.get(i).getLiveness(), DELTA);
            Assertions.assertEquals(expected.get(i).getLoudness(), actual.get(i).getLoudness(), DELTA);
            Assertions.assertEquals(expected.get(i).getMode(), actual.get(i).getMode());
            Assertions.assertEquals(expected.get(i).getSpeechiness(), actual.get(i).getSpeechiness(), DELTA);
            Assertions.assertEquals(expected.get(i).getTempo(), actual.get(i).getTempo(), DELTA);
            Assertions.assertEquals(expected.get(i).getTimeSignature(), actual.get(i).getTimeSignature());
            Assertions.assertEquals(expected.get(i).getTrackHref(), actual.get(i).getTrackHref());
            Assertions.assertEquals(expected.get(i).getType(), actual.get(i).getType());
            Assertions.assertEquals(expected.get(i).getUri(), actual.get(i).getUri());
            Assertions.assertEquals(expected.get(i).getValence(), actual.get(i).getValence());
        }
    }


    @Test
    public void getSeveralArtists_returnsCorrectArtists() {
        //Call method to be tested
        ArrayList<Artist> actual = SpotifyTools.getSeveralArtists(sampleArtistIDs);

        //Test equality
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertEquals(sampleArtist.get(i), actual.get(i));
        }
    }

    @Test
    public void getSeveralAlbums_returnsCorrectAlbums() {
        //Call method to be tested
        ArrayList<Album> actual = SpotifyTools.getSeveralAlbums(sampleAlbumIDs);

        //Test equality
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertEquals(sampleAlbum.get(i), actual.get(i));
        }
    }

    @Test
    public void getSeveralTracks_returnsCorrectTracks() {
        //Extract the trackIDs
        ArrayList<String> trackIDs = new ArrayList<>();
        for (Track track : sampleTrack) {
            trackIDs.add(track.getTrackID());
        }

        //Call method to be tested
        ArrayList<Track> resultTrack = SpotifyTools.getSeveralTracks(trackIDs);

        //Test equality
        for (int i = 0; i < resultTrack.size(); i++) {
            Assertions.assertEquals(sampleTrack.get(i), resultTrack.get(i));
        }
    }

    @Test
    public void toIdArray_returnsCorrectIds() {
        for (int i = 0; i < sampleAlbum.size(); i++) {
            Assertions.assertEquals(sampleAlbum.get(i).getAlbumID(), sampleAlbumIDs.get(i));
        }
        for (int i = 0; i < sampleArtist.size(); i++) {
            Assertions.assertEquals(sampleArtist.get(i).getArtistID(), sampleArtistIDs.get(i));
        }
        for (int i = 0; i < sampleTrack.size(); i++) {
            Assertions.assertEquals(sampleTrack.get(i).getTrackID(), sampleTrackIDs.get(i));
        }
    }

    @Test
    public void getArtistByGenre_returnCorrectArtists() {
        ArrayList<Artist> artists = SpotifyTools.getArtistByGenre(Genre.CLASSICAL, 10);
        ArrayList<String> names = new ArrayList<>();
        for (Artist artist : artists) {
            names.add(artist.getName());
        }
        Assertions.assertTrue(names.contains("Wolfgang Amadeus Mozart"));
        Assertions.assertTrue(names.contains("Johann Sebastian Bach"));
        Assertions.assertTrue(names.contains("Ludwig van Beethoven"));

        artists = SpotifyTools.getArtistByGenre(Genre.JAZZ, 10);
        names = new ArrayList<>();
        for (Artist artist : artists) {
            names.add(artist.getName());
        }
        Assertions.assertTrue(names.contains("Nat King Cole"));
        Assertions.assertTrue(names.contains("Ella Fitzgerald"));
        Assertions.assertTrue(names.contains("Louis Armstrong"));
    }

    @Test
    public void getRelatedArtists_returnCorrectArtists() {
        String mozartID = "4NJhFmfw43RLBLjQvxDuRS";
        ArrayList<Artist> artists = SpotifyTools.getRelatedArtists(mozartID);

        ArrayList<String> names = new ArrayList<>();
        for (Artist artist : artists) {
            names.add(artist.getName());
        }
        Assertions.assertTrue(names.contains("Johann Sebastian Bach"));
        Assertions.assertTrue(names.contains("Ludwig van Beethoven"));
        Assertions.assertTrue(names.contains("Frédéric Chopin"));
        Assertions.assertTrue(names.contains("George Frideric Handel"));
    }

    @Test
    public void toTracks_returnsCorrectConversion() {
        Album album = SpotifyTools.searchAlbums("Hotel California", 1).get(0);
        ArrayList<Track> expected = album.getTracks();

        try {
            TrackSimplified[] tsArr =
                    SpotifyTools.getApi().getAlbum(album.getAlbumID()).build()
                            .execute().getTracks().getItems();
            ArrayList<Track> actual = SpotifyTools.toTracks(tsArr);


            for (int i = 0; i < expected.size(); i++) {
                Assertions.assertEquals(expected.get(i), actual.get(i));
            }
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toArtists_returnsCorrectConversion() {
        try {
            Album album = SpotifyTools.searchAlbums("Back in Black", 1).get(0);
            ArrayList<Artist> expected = album.getArtists();

            ArtistSimplified[] asArr = SpotifyTools.getApi()
                    .getAlbum(album.getAlbumID()).build().execute().getArtists();
            ArrayList<Artist> actual = SpotifyTools.toArtists(asArr);

            for (int i = 0; i < expected.size(); i++) {
                Assertions.assertEquals(expected.get(i), actual.get(i));
            }
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSeveralAlbums_returnsCorrectAlbumsForLargeQuantity() {
        ArrayList<String> ids = new ArrayList<>();
        ids.addAll(SpotifyTools.toIdArrayList(SpotifyTools.searchAlbums("My", 50)));
        ids.addAll(SpotifyTools.toIdArrayList(SpotifyTools.searchAlbums("Love", 50)));
        ids.addAll(SpotifyTools.toIdArrayList(SpotifyTools.searchAlbums("You", 50)));
        ids.addAll(SpotifyTools.toIdArrayList(SpotifyTools.searchAlbums("She", 50)));
        ids.addAll(SpotifyTools.toIdArrayList(SpotifyTools.searchAlbums("Why", 50)));

        ArrayList<Album> albums = SpotifyTools.getSeveralAlbums(ids);
        for (Album album: albums) {
            Assertions.assertTrue(album.isInitialized());
        }
    }
}
