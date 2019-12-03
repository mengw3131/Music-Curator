import com.curator.controllers.WelcomeController;
import com.curator.models.Genre;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import com.curator.tools.YoutubeTools;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ===================================================
 * USER NOTE:
 * ===================================================
 * Use Gradle -> Task -> Application -> "run" to run the application
 * <p>
 * Entry to the app.
 */
public class Main extends Application {
    /**
     * Initialize GUI app
     *
     * @param stage - the main stage of the app
     */
    @java.lang.Override
    public void start(Stage stage) {
//        DBTools.initialize("islong2");
//        DBTools.cleanDB();
////        DBTools.initialize("islong3");
////        DBTools.removeUser();
//        System.exit(0);




        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/welcome.fxml"));
        Scene scene = null;
        try {


            scene = new Scene(loader.load(), 300, 300);

            WelcomeController welcomeController = loader.getController();
            welcomeController.setStage(stage);
            welcomeController.setScene(scene);

            stage.setScene(scene);
            stage.setTitle("Music Curator");
            stage.show();

            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    YoutubeTools.initialize();
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        DBTools.terminate();
        super.stop();
    }
}
