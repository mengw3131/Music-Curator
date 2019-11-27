import com.curator.tools.DBTools;
import com.curator.tools.YoutubeTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TODO: create sqlite database to keep user usage data, e.g. favorites etc
//TODO: add clear cache feature in settings menu bar, to removeFromPlaylists downloaded mp3 files

/**
 * ===================================================
 *                    USER NOTE:
 * ===================================================
 *
 * Use Gradle -> "run" to run the application
 *
 *
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
        YoutubeTools.initializeInterpreter();
        DBTools.initialize("user");

        Parent root = new FXMLLoader(getClass().getResource("/views/main.fxml")).load();
        stage.setScene(new Scene(root, 300, 300));
        stage.setHeight(600);
        stage.setMinWidth(1300);
        stage.setTitle("Music Curator");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        DBTools.terminate();
        super.stop();
    }
}
