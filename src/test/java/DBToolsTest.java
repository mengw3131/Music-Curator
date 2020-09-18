import com.curator.tools.SpotifyTools;
import org.junit.jupiter.api.*;

import com.curator.tools.DBTools;
import com.curator.models.*;


import java.util.ArrayList;

/*

 Test Coverage
 +---------------+-------------+------+-------+---------------+
 | Element       | Class, %    | Method, %    | Line, %       |
 +---------------+-------------+------+-------+---------------+
 | DBTools       | 100% (1/1)  | 100% (53/58)  | 100% (479/479) |
 +---------------+-------------+------+-------+---------------+
*/

class DBToolsTest {
    public static final String USER_ID = "unit_test_12345";

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
    public void isNewUser_returnsTrueIfUserIsNew(){
        Assertions.assertTrue(DBTools.isNewUser());  //true, since user is deleted after each test
    }

    @Test
    public void isNewUser_returnsFalseIfIsNotNewUser(){
        DBTools.initialize("islong");
        DBTools.addNewUser();
        Assertions.assertFalse(DBTools.isNewUser()); //islong logged in twice
        DBTools.initialize(USER_ID); //switch back, so islong is not removed
    }

    @Test
    public void getUserID_returnsCorrectUserID(){
        Assertions.assertEquals(USER_ID, DBTools.getUserId());
    }

    @Test
    public void incrementLoginCount_incrementsLoginCount(){
    	DBTools.addNewUser();
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
    
    @Test
	void testAddNewUser() {
    	DBTools.addNewUser();
        Assertions.assertEquals(1, DBTools.getLoginCount());
	}


	@Test
	void testGetUserLikedSongs() {
		DBTools.storeUserPreferenceTracks("703WlQONsXodIABGbGvF6M", true);
		ArrayList<Track> list = DBTools.getUserLikedSongs();
		StringBuilder sb = new StringBuilder();
		for (Track track: list) {
			String track_id = track.getTrackID();
			sb.append(track_id);
		}
		Assertions.assertEquals("703WlQONsXodIABGbGvF6M", sb.toString());
		
	}

	@Test
	void testGetUserLikedArtists() {
		DBTools.storeUserPreferenceArtist("04gDigrS5kc9YWfZHwBETP", true);
		ArrayList<Artist> list = DBTools.getUserLikedArtists();
		StringBuilder sb = new StringBuilder();
		for (Artist artist: list) {
			String artist_id = artist.getArtistID();
			sb.append(artist_id);
		}
		Assertions.assertEquals("04gDigrS5kc9YWfZHwBETP", sb.toString());
	}

	@Test
	void testGetUserLikedAlbum() {
		DBTools.storeUserPreferenceAlbum("3LM5q1ozRtvCt0Wf3fqsp7", true);
		ArrayList<Album> list = DBTools.getUserLikedAlbum();
		StringBuilder sb = new StringBuilder();
		for (Album album: list) {
			String album_id = album.getAlbumID();
			sb.append(album_id);
		}
		Assertions.assertEquals("3LM5q1ozRtvCt0Wf3fqsp7", sb.toString());
	}

	@Test
	void testGetUserDislikedSongs() {
		DBTools.storeUserPreferenceTracks("703WlQONsXodIABGbGvF6M", false);
		ArrayList<Track> list = DBTools.getUserDislikedSongs();
		StringBuilder sb = new StringBuilder();
		for (Track track: list) {
			String track_id = track.getTrackID();
			sb.append(track_id);
		}
		Assertions.assertEquals("703WlQONsXodIABGbGvF6M", sb.toString());
	}

	@Test
	void testGetUserDislikedArtists() {
		DBTools.storeUserPreferenceArtist("04gDigrS5kc9YWfZHwBETP", false);
		ArrayList<Artist> list = DBTools.getUserDislikedArtists();
		StringBuilder sb = new StringBuilder();
		for (Artist artist: list) {
			String artist_id = artist.getArtistID();
			sb.append(artist_id);
		}
		Assertions.assertEquals("04gDigrS5kc9YWfZHwBETP", sb.toString());
	}

	@Test
	void testGetUserDislikedAlbum() {
		DBTools.storeUserPreferenceAlbum("3LM5q1ozRtvCt0Wf3fqsp7", false);
		ArrayList<Album> list = DBTools.getUserDislikedAlbum();
		StringBuilder sb = new StringBuilder();
		for (Album album: list) {
			String album_id = album.getAlbumID();
			sb.append(album_id);
		}
		Assertions.assertEquals("3LM5q1ozRtvCt0Wf3fqsp7", sb.toString());
	}

	

	@Test
	void testGetPlaylist() {
		 Playlist p4 = new Playlist("Playlist getPlaylist");
		 String id = p4.getId();
		 DBTools.storePlaylist(p4);
		 Assertions.assertEquals(p4, DBTools.getPlaylist(id));
	}

	@Test
	void testGetTracksFromPlaylistID() {
		Playlist p4 = new Playlist("getTracksFromPlaylistID");
		String t1 = "5zhuWncJsBKrQ1HhmAKNAg";
		String t2 = "1Mse9NKBbEASi50CQ4aYhr";
		String t3 = "2s7oaW721ExcKoyxzDWD1D";
		DBTools.storePlaylist(p4);
		String playlistId = p4.getId();
		DBTools.storeTrackToPlaylist(t1, playlistId);
		DBTools.storeTrackToPlaylist(t2, playlistId);
		DBTools.storeTrackToPlaylist(t3, playlistId);
		ArrayList<Track> tracks = DBTools.getTracksFromPlaylistID(playlistId);
        assert tracks != null;
        Assertions.assertEquals(t1, tracks.get(0).getTrackID());
		Assertions.assertEquals(t2, tracks.get(1).getTrackID());
		Assertions.assertEquals(t3, tracks.get(2).getTrackID());
		
	}

	@Test
	void testGetTracksIDsFromPlaylist() {
		Playlist p4 = new Playlist("getTracksIDsFromPlaylist");
		String t1 = "5zhuWncJsBKrQ1HhmAKNAg";
		String t2 = "1Mse9NKBbEASi50CQ4aYhr";
		String t3 = "2s7oaW721ExcKoyxzDWD1D";
		DBTools.storePlaylist(p4);
		String playlistId = p4.getId();
		DBTools.storeTrackToPlaylist(t1, playlistId);
		DBTools.storeTrackToPlaylist(t2, playlistId);
		DBTools.storeTrackToPlaylist(t3, playlistId);
		ArrayList<String> tracks = DBTools.getTracksIDsFromPlaylist(p4);
        assert tracks != null;
        Assertions.assertEquals(t1, tracks.get(0));
		Assertions.assertEquals(t2, tracks.get(1));
		Assertions.assertEquals(t3, tracks.get(2));
		
	}

	

	@Test
	void testRenamePlaylist() {
		Playlist p4 = new Playlist("PlaylistName");
		DBTools.storePlaylist(p4);
		String playlistId = p4.getId();
		DBTools.renamePlaylist("PlaylistNewName", playlistId);
		Assertions.assertEquals("PlaylistNewName", DBTools.getPlaylistName(playlistId));
		
	}

	@Test
	void testStorePlaylistMeta() {
		Playlist p4 = new Playlist("PlaylistName");
		DBTools.storePlaylistMeta(p4);
		Assertions.assertEquals("PlaylistName", p4.getName());
	}

	@Test
	void testGetPlaylistName() {
		Playlist p4 = new Playlist("PlaylistName");
		DBTools.storePlaylist(p4);
		String playlistId = p4.getId();
		Assertions.assertEquals("PlaylistName", DBTools.getPlaylistName(playlistId));
	}

	@Test
	void testGetRecommendationTrack() {
		Track t1 = SpotifyTools.getTrack("5zhuWncJsBKrQ1HhmAKNAg");
		Track t2 = SpotifyTools.getTrack("1Mse9NKBbEASi50CQ4aYhr");
		Track t3 = SpotifyTools.getTrack("2s7oaW721ExcKoyxzDWD1D");
		ArrayList<Track> storedTracks = new ArrayList<>();
		storedTracks.add(t1);
		storedTracks.add(t2);
		storedTracks.add(t3);
		DBTools.storeRecommendationTrack(storedTracks);
		ArrayList<Track> list = DBTools.getRecommendationTrack(3);
		Assertions.assertEquals(t1, list.get(0));
		Assertions.assertEquals(t2, list.get(1));
		Assertions.assertEquals(t3, list.get(2));
	}

	@Test
	void testGetRecommendationAlbum() {
		Album t1 = SpotifyTools.getAlbum("2OpnKgmVYPEN2GldgBponI");
		Album t2 = SpotifyTools.getAlbum("5oxH8IwrnqlG5t4nDngDV1");
		Album t3 = SpotifyTools.getAlbum("1QlwdrB0YycnoWOH1JqCqh");
		ArrayList<Album> storedAlbums = new ArrayList<>();
		storedAlbums.add(t1);
		storedAlbums.add(t2);
		storedAlbums.add(t3);
		DBTools.storeRecommendationAlbum(storedAlbums);
		ArrayList<Album> list = DBTools.getRecommendationAlbum(3);
		Assertions.assertEquals(t1, list.get(0));
		Assertions.assertEquals(t2, list.get(1));
		Assertions.assertEquals(t3, list.get(2));
	}

	@Test
	void testGetRecommendationArtist() {
		Artist t1 = SpotifyTools.getArtist("6qqNVTkY8uBg9cP3Jd7DAH");
		Artist t2 = SpotifyTools.getArtist("00FQb4jTyendYWaN8pK0wa");
		Artist t3 = SpotifyTools.getArtist("66CXWjxzNUsdJxJ2JdwvnR");
		ArrayList<Artist> storedArtists = new ArrayList<>();
		storedArtists.add(t1);
		storedArtists.add(t2);
		storedArtists.add(t3);
		DBTools.storeRecommendationArtist(storedArtists);
		ArrayList<Artist> list = DBTools.getRecommendationArtist(3);
		Assertions.assertEquals(t1, list.get(0));
		Assertions.assertEquals(t2, list.get(1));
		Assertions.assertEquals(t3, list.get(2));
	}

	@Test
	void testIsTrackExistInRecTable() {
		Track t1 = SpotifyTools.getTrack("5zhuWncJsBKrQ1HhmAKNAg");
		Track t2 = SpotifyTools.getTrack("1Mse9NKBbEASi50CQ4aYhr");
		Track t3 = SpotifyTools.getTrack("2s7oaW721ExcKoyxzDWD1D");
		ArrayList<Track> storedTracks = new ArrayList<>();
		storedTracks.add(t1);
		storedTracks.add(t2);
		DBTools.storeRecommendationTrack(storedTracks);
		Assertions.assertTrue(DBTools.isTrackExistInRecTable(t1));
		Assertions.assertFalse(DBTools.isTrackExistInRecTable(t3));
	}

	@Test
	void testIsAlbumExistInRecTable() {
		Album t1 = SpotifyTools.getAlbum("2OpnKgmVYPEN2GldgBponI");
		Album t2 = SpotifyTools.getAlbum("5oxH8IwrnqlG5t4nDngDV1");
		Album t3 = SpotifyTools.getAlbum("1QlwdrB0YycnoWOH1JqCqh");
		ArrayList<Album> storedAlbums = new ArrayList<>();
		storedAlbums.add(t1);
		storedAlbums.add(t2);
		DBTools.storeRecommendationAlbum(storedAlbums);
		Assertions.assertTrue(DBTools.isAlbumExistInRecTable(t1));
		Assertions.assertFalse(DBTools.isAlbumExistInRecTable(t3));
	}

	@Test
	void testIsArtistExistInRecTable() {
		Artist t1 = SpotifyTools.getArtist("6qqNVTkY8uBg9cP3Jd7DAH");
		Artist t2 = SpotifyTools.getArtist("00FQb4jTyendYWaN8pK0wa");
		Artist t3 = SpotifyTools.getArtist("66CXWjxzNUsdJxJ2JdwvnR");
		ArrayList<Artist> storedArtists = new ArrayList<>();
		storedArtists.add(t1);
		storedArtists.add(t2);
		DBTools.storeRecommendationArtist(storedArtists);
		Assertions.assertTrue(DBTools.isArtistExistInRecTable(t1));
		Assertions.assertFalse(DBTools.isArtistExistInRecTable(t3));
	}
  
}
