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
    Task task;
    ProgressBar progressBar;
    AnchorPane pane = new AnchorPane();
    Scene tempScene;

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
     * Entry method to app
     * @param args
     */
    public static void main(String[] args) {
        YoutubeTools.initializeInterpreter();
        launch();
    }


}
