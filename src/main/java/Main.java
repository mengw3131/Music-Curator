import com.curator.controllers.WelcomeController;
import com.curator.tools.DBTools;
import com.curator.tools.YoutubeTools;
import javafx.application.Application;
import javafx.concurrent.Task;
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

    public boolean isAuthenticated = false;

    /**
     * Initialize GUI app
     *
     * @param stage - the main stage of the app
     * @throws Exception
     */
    @java.lang.Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/welcome.fxml"));
        Scene scene = new Scene(loader.load(), 300, 300);
        WelcomeController welcomeController = loader.getController();
        welcomeController.setStage(stage);
        welcomeController.setScene(scene);

        stage.setScene(scene);
        stage.setTitle("Music Curator");
        stage.show();

        Task task = new Task(){
            @Override
            protected Object call() throws Exception {
                YoutubeTools.initialize();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void stop() throws Exception {
        DBTools.terminate();
        super.stop();
    }
}
