import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.curator.tools.DBTools;

class DBToolsTest {

    @BeforeAll
    public void init() {
        DBTools.initialize("unit_test_user");
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
//		Assertions.assertEquals("463564A", sb.toString());
//		fail("Not yet implemented");
    }

    @Test
    public void isTrackExistInPlaylist_returnsTrueIfTrackExistsInPlaylist(){

    }

    @Test
    public void isTrackExistInPlaylist_returnsFalseIfTrackDoesNotExistsInPlaylist(){

    }

    @Test
    public void isTrackExistInPlaylist_returnsFalseIfPlaylistDoesNotExist(){

    }

    @Test
    public void isPlaylistExist_returnsTrueIfPlaylistExists(){

    }

    @Test
    public void isPlaylistExist_returnsFalseIfPlaylistDoesNotExists(){

    }

    @Test
    public void removeTrackFromPlaylist_removesTrackIfExistsInPlaylist() {

    }

    @Test
    public void removeTrackFromPlaylist_removesNothingIfTrackDoesNotExistsInPlaylist() {

    }

    @Test
    public void removeTrackFromPlaylist_removesNothingIfPlaylistDoesNotExist() {

    }

    @Test
    public void removePlaylist_removesPlaylistIfPlaylistExists() {

    }

    @Test
    public void removePlaylist_removesNothingIfPlaylistDoesNotExists() {

    }

    @Test
    public void storeTracksToPlaylist_storesTracksToPlaylistIfPlaylistExistsAndTracksNeverAdded(){

    }

    @Test
    public void storeTracksToPlaylist_storesNothingIfPlaylistExistsAndTracksAlreadyInPlaylist(){

    }

    @Test
    public void storeTracksToPlaylist_storesNothingIfPlaylistDoesNotExist(){

    }
}
