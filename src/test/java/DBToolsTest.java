import com.curator.models.Playlist;
import com.curator.tools.SpotifyTools;
import org.junit.jupiter.api.*;

import com.curator.tools.DBTools;
import com.curator.models.*;

import java.util.ArrayList;

class DBToolsTest {
    public static String USER_ID = "unit_test_12345";

    @BeforeAll
    public static void init(){
    }


    @BeforeEach
    public void reInit() {
        DBTools.initialize(USER_ID);
    }

    @AfterEach
    public void tearDown() {
        DBTools.removeUser();
    }

    @AfterAll
    public static void terminate(){
        DBTools.terminate();
    }

    @Test
    void testGetUserLikedSongs() {
        //String userID = "meng";
//		String trackID = "463564A";
//		Boolean like = true;

//		DBTools.storeUserPreferenceTracks(userID, trackID, like);
//		DBTools db = new DBTools();
//		ArrayList<Track> list = db.getUserLikedSongs("meng");
//		StringBuilder sb = new StringBuilder();
//		for (Track track: list) {
//			String track_id = track.getTrackID();
//			sb.append(track_id);
//		}
//		Assertions.assertEquals("463564A", sb.toArtistName());
//		fail("Not yet implemented");
    }

    @Test
    public void isNewUser_returnsTrueIfUserIsNew(){
        Assertions.assertTrue(DBTools.isNewUser());  //true, since user is deleted after each test
    }

    @Test
    public void isNewUser_returnsFalseIfIsNotNewUser(){
        DBTools.initialize("islong");
        Assertions.assertFalse(DBTools.isNewUser()); //islong logged in twice
        DBTools.initialize(USER_ID); //switch back, so islong is not removed
    }

    @Test
    public void getUserID_returnsCorrectUserID(){
        Assertions.assertEquals(USER_ID, DBTools.getUserId());
    }

    //TODO: FIX!
    @Test
    public void incrementLoginCount_incrementsLoginCount(){
        DBTools.incrementLoginCount();
        Assertions.assertEquals(2, DBTools.getLoginCount());
    }

    @Test
    public void getAllPlaylistIDs_returnsAllPlaylistIDs(){
        Playlist p1 = new Playlist("Playlist 1");
        Playlist p2 = new Playlist("Playlist 2");
        Playlist p3 = new Playlist("Playlist 3");
        DBTools.storePlaylist(p1);
        DBTools.storePlaylist(p2);
        DBTools.storePlaylist(p3);

        ArrayList<String> ids = DBTools.getAllPlaylistIDs();
        Assertions.assertEquals(3, ids.size());
        Assertions.assertTrue(ids.contains(p1.getId()));
        Assertions.assertTrue(ids.contains(p2.getId()));
        Assertions.assertTrue(ids.contains(p3.getId()));
    }

    @Test
    public void getAllPlaylists_returnsAllPlaylists(){
        Playlist p1 = new Playlist("Playlist 1");
        Playlist p2 = new Playlist("Playlist 2");
        Playlist p3 = new Playlist("Playlist 3");
        DBTools.storePlaylist(p1);
        DBTools.storePlaylist(p2);
        DBTools.storePlaylist(p3);

        ArrayList<Playlist> playlists = DBTools.getAllPlaylist();
        Assertions.assertEquals(3, playlists.size());

        Assertions.assertEquals(p1, playlists.get(0)); //playlist 0 is the empty playlist
        Assertions.assertEquals(p2, playlists.get(1));
        Assertions.assertEquals(p3, playlists.get(2));
    }


    @Test
    public void isTrackExistInPlaylist_returnsTrueIfTrackExistsInPlaylist(){
        Track track = SpotifyTools.searchTracks("Rondo Alla Turca", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        DBTools.storePlaylist(playlist);
        Assertions.assertTrue(DBTools.isTrackExistInPlaylist(track, playlist));
        Assertions.assertTrue(DBTools.isTrackExistInPlaylist(track.getTrackID(), playlist.getId()));
    }

    @Test
    public void isTrackExistInPlaylist_returnsFalseIfTrackDoesNotExistsInPlaylist(){
        Track track = SpotifyTools.searchTracks("Four Seasons", 1).get(0);
        Track otherTrack = SpotifyTools.searchTracks("Stairway to Heaven", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        DBTools.storePlaylist(playlist);
        Assertions.assertFalse(DBTools.isTrackExistInPlaylist(otherTrack, playlist));
        Assertions.assertFalse(DBTools.isTrackExistInPlaylist(otherTrack.getTrackID(), playlist.getId()));
    }

    @Test
    public void isTrackExistInPlaylist_returnsFalseIfPlaylistDoesNotExist(){
        Track track = SpotifyTools.searchTracks("Sleep Away", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        //DBTools.storePlaylist(playlist); //playlist was never stored
        Assertions.assertFalse(DBTools.isTrackExistInPlaylist(track, playlist));
        Assertions.assertFalse(DBTools.isTrackExistInPlaylist(track.getTrackID(), playlist.getId()));
    }

    @Test
    public void isPlaylistExist_returnsTrueIfPlaylistExists(){
        Track track = SpotifyTools.searchTracks("Metallica", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        DBTools.storePlaylist(playlist);
        Assertions.assertTrue(DBTools.isPlaylistExist(playlist));
        Assertions.assertTrue(DBTools.isPlaylistExist(playlist.getId()));
    }

    @Test
    public void isPlaylistExist_returnsFalseIfPlaylistDoesNotExists(){
        Track track = SpotifyTools.searchTracks("Träumerei", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        //DBTools.storePlaylist(playlist);   //playlist was never stored
        Assertions.assertFalse(DBTools.isPlaylistExist(playlist));
        Assertions.assertFalse(DBTools.isPlaylistExist(playlist.getId()));
    }

    @Test
    public void removeTrackFromPlaylist_removesTrackIfExistsInPlaylist() {
        Track track = SpotifyTools.searchTracks("Hello", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        DBTools.storePlaylist(playlist);
        DBTools.removeTrackFromPlaylist(track, playlist); //playlist is empty now

        Assertions.assertFalse(DBTools.isTrackExistInPlaylist(track, playlist));
    }

    @Test
    public void removeTrackFromPlaylist_removesNothingIfTrackDoesNotExistsInPlaylist() {
        Track track = SpotifyTools.searchTracks("I Fall to Pieces", 1).get(0);
        Track trackToRemove = SpotifyTools.searchTracks("Sweet Child O' Mine", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        DBTools.storePlaylist(playlist);
        DBTools.removeTrackFromPlaylist(trackToRemove, playlist); //deletes nothing

        Assertions.assertTrue(DBTools.isTrackExistInPlaylist(track, playlist));
    }

    @Test
    public void removeTrackFromPlaylist_removesNothingIfPlaylistDoesNotExist() {
        Track track = SpotifyTools.searchTracks("Hey Jude", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        //DBTools.storePlaylist(playlist); //playlist was never stored
        DBTools.removeTrackFromPlaylist(track, playlist); //deletes nothing

        Assertions.assertFalse(DBTools.isTrackExistInPlaylist(track, playlist));
    }

    @Test
    public void removePlaylist_removesPlaylistIfPlaylistExists() {
        Track track = SpotifyTools.searchTracks("Blizzard of Ozz", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        DBTools.storePlaylist(playlist);  //stores playlist
        DBTools.removePlaylist(playlist); //deletes playlist
        Assertions.assertFalse(DBTools.isPlaylistExist(playlist));
    }

    @Test
    public void removePlaylist_removesNothingIfPlaylistDoesNotExists() {
        Track track = SpotifyTools.searchTracks("Blizzard of Ozz", 1).get(0);
        Playlist playlist = new Playlist("My Playlist", track);
        DBTools.storePlaylist(playlist);  //stores playlist
        DBTools.removePlaylist(playlist); //deletes playlist
        Assertions.assertFalse(DBTools.isPlaylistExist(playlist));
    }

    @Test
    public void storeTracksToPlaylist_storesTracksToPlaylistIfPlaylistExistsAndTracksNeverAdded(){
        ArrayList<Track> tracks = SpotifyTools.searchTracks("Love", 5);
        Playlist playlist = new Playlist("My Playlist");
        DBTools.storePlaylist(playlist);                       //stores playlist
        DBTools.storeTracksToPlaylist(tracks, playlist);
        for (Track track:tracks) {
            Assertions.assertTrue(DBTools.isTrackExistInPlaylist(track, playlist));
        }
    }

    @Test
    public void storeTracksToPlaylist_storesNothingIfPlaylistDoesNotExist(){
        ArrayList<Track> tracks = SpotifyTools.searchTracks("Love", 5);
        Playlist playlist = new Playlist("My Playlist");
        //DBTools.storePlaylist(playlist);    //playlist was never stored
        DBTools.storeTracksToPlaylist(tracks, playlist);
        for (Track track:tracks) {
            Assertions.assertFalse(DBTools.isTrackExistInPlaylist(track, playlist));
        }
    }
}
