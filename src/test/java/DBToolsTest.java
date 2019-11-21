

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.curator.models.Track;
import com.curator.tools.DBTools;

class DBToolsTest {

	@Test
	void testDBTools() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserLikedSongs() {
		String userID = "meng";
		String trackID = "463564A";
		Boolean like = true;
		
		DBTools.storeUserPreferenceTracks(userID, trackID, like);
		String track_id = DBTools.getUserLikedSongs("meng").toString();
	    
		Assertions.assertEquals("463564A", track_id);
		fail("Not yet implemented");
     }
	
}
