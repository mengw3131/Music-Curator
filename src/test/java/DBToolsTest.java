
import com.curator.tools.SpotifyTools;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.curator.models.Track;
import com.curator.tools.DBTools;

class DBToolsTest {

	@Test
	void testGetUserLikedSongs() {
		//String userID = "meng";
//		String trackID = "463564A";
//		Boolean like = true;
		
//		DBTools.storeUserPreferenceTracks(userID, trackID, like);
		DBTools db = new DBTools();
		ArrayList<Track> list = db.getUserLikedSongs("meng");
		StringBuilder sb = new StringBuilder();
		for (Track track: list) {
			String track_id = track.getTrackID();
			sb.append(track_id);
		}
		Assertions.assertEquals("463564A", sb.toString());
		fail("Not yet implemented");
	}

}
