import java.util.ArrayList;

import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import com.curator.tools.YoutubeTools;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//TODO: create sqlite database to keep user usage data, e.g. favorites etc
//TODO: add clear cache feature in settings menu bar, to remove downloaded mp3 files

/**
 * Entry to the app.
 */
public class Main extends Application {

    /**
     * Initialize GUI app
     *
     * @param stage - the main stage of the app
     * @throws Exception
     */
    @java.lang.Override
    public void start(Stage stage) throws Exception {
        Parent root = new FXMLLoader(getClass().getResource("/views/main.fxml")).load();
        stage.setScene(new Scene(root, 300, 300));
        stage.setHeight(600);
        stage.setWidth(1200);
        stage.setTitle("Music Curator");
        stage.show();
    }

    /**
     * USER NOTE:
     * Do not use the main method to run the application
     * Use Gradle -> run, instead.
     *
     * Entry method to app
     * @param args
     */
    public static void main(String[] args) {
        YoutubeTools.initializeInterpreter();
        DBTools.initialize("fancy_user_1");
        launch();


//    	DBTools db = new DBTools();
//
//
//
//    	ArrayList<Track> tracks =  SpotifyTools.searchTracks("Rap songs", 8);
//    	for (Track t: tracks) {
//    		db.storeUserPreferenceTracks(USER_ID, t.getTrackID(), true);
//    	}
//
//    	ArrayList<Track> list = db.getUserLikedSongs(USER_ID);
//		StringBuilder sb = new StringBuilder();
//		for (Track track: list) {
//			String track_id = track.getTrackID();
//			sb.append(track_id);
//		}
//        System.out.print(sb.toString());
    }


}
