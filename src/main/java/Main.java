import com.curator.controllers.WelcomeController;
import com.curator.tools.DBTools;
import com.curator.tools.YoutubeTools;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
    	YoutubeTools.initialize();
    	//SET DEFAULT FOLDER CONTAINING ffmpeg HERE
    	YoutubeTools.setFFMPEGpath("/usr/local/bin/");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/welcome.fxml"));
        Scene scene;
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
                protected Object call() {
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
