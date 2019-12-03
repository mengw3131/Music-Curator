
import com.curator.tools.SpotifyTools;
import com.wrapper.spotify.SpotifyApi;
import com.curator.models.*;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Unit tests for SpotifyTool class
 */
public class SpotifyToolsTest {
    SpotifyApi api = SpotifyTools.getApi();

//    @Test
//    public void toTrackSimple_returnsCorrectTracks(){
//        String albumID = "4sSXylKcBB3p47VfrBJlfK";
//
//        com.wrapper.spotify.model_objects.specification.TrackSimplified[] sTrackSimplifiedArr;
//        ArrayList<TrackSimple> trackSimpleArr;
//
//        try {
//            sTrackSimplifiedArr = api.getAlbumsTracks(albumID).build().execute().getItems();
//
//            //convert to com.curator.models.TrackSimple
//            trackSimpleArr = SpotifyTools.toTrackSimple(sTrackSimplifiedArr);
//
//            for (int i = 0; i < sTrackSimplifiedArr.length; i++) {
//                TrackSimplified sTrack = sTrackSimplifiedArr[i];
//                TrackSimple trackSimple = trackSimpleArr.get(i);
//
//                Assertions.assertEquals(sTrack.getId(), trackSimple.getTrackID());
//                Assertions.assertEquals(sTrack.getName(), trackSimple.getTrackName());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SpotifyWebApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void toArtists_returnsCorrectArtists(){
//        String albumID = "7D1El2JyMMcaJv0OhWKoDH";
//
//        com.wrapper.spotify.model_objects.specification.ArtistSimplified[] sArtistSimplifiedArr;
//        ArrayList<com.curator.models.Artist> artistArr;
//
//        try {
//            sArtistSimplifiedArr = api.getAlbum(albumID).build().execute().getArtists();
//
//            //convert to com.com.curator.models.Artist
//            artistArr = SpotifyTools.toArtist(sArtistSimplifiedArr);
//
//            for (int i = 0; i < sArtistSimplifiedArr.length; i++) {
//                ArtistSimplified sArtistSimplified = sArtistSimplifiedArr[i];
//                com.curator.models.Artist artist = artistArr.get(i);
//
//                Assertions.assertEquals(sArtistSimplified.getId(), artist.getArtistID());
//                Assertions.assertEquals(sArtistSimplified.getName(), artist.getName());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SpotifyWebApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void toAlbumSimple_returnsCorrectAlbums(){
//        String artistID = "5Jzy4kcxChcbcL2Z6QQRUI";
//
//        com.wrapper.spotify.model_objects.specification.AlbumSimplified[] sAlbumSimplifiedArr;
//        ArrayList<com.curator.models.AlbumSimple> albumSimpleArr;
//
//        try {
//            sAlbumSimplifiedArr = api.getArtistsAlbums(artistID).build().execute().getItems();
//
//            //convert to com.com.curator.models.AlbumSimple
//            albumSimpleArr = SpotifyTools.toAlbumSimple(sAlbumSimplifiedArr);
//
//            for (int i = 0; i < sAlbumSimplifiedArr.length; i++) {
//                AlbumSimplified sAlbumSimplified = sAlbumSimplifiedArr[i];
//                com.curator.models.AlbumSimple albumSimple = albumSimpleArr.get(i);
//
//                Assertions.assertEquals(sAlbumSimplified.getId(), albumSimple.getAlbumID());
//                Assertions.assertEquals(sAlbumSimplified.getName(), albumSimple.getName());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SpotifyWebApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getAudioFeatures_returnsCorrectAudioFeatures(){
//        String trackID = "41e2LhE1nFYh41vIteVA5u";
//        try {
//            AudioFeatures audioFeatures = api.getAudioFeaturesForTrack(trackID).build().execute();
//
//            //com.curator.models.Track constructor contains calls to getAudioFeatures()
//            Track track = new Track(api.getTrack(trackID).build().execute());
//
//            Assertions.assertEquals(audioFeatures.getAcousticness(), track.getAcousticness(),1);
//            Assertions.assertEquals(audioFeatures.getDanceability(), track.getDanceability(),1);
//            Assertions.assertEquals(audioFeatures.getEnergy(), track.getEnergy(),1);
//            Assertions.assertEquals(audioFeatures.getInstrumentalness(), track.getInstrumentalness(),1);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SpotifyWebApiException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
