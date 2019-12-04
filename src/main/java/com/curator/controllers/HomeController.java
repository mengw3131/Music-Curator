package com.curator.controllers;

import com.curator.models.*;
import com.curator.tools.DBTools;
import com.curator.tools.RecTools;
import com.curator.views.ItemScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to home.fxml
 */
public class HomeController implements Initializable {
    private MainController mainController;
    private PlayerController playerController;
    private NavbarController navbarController;

    private ArrayList<Track> tracks;
    private ArrayList<com.curator.models.Album> albums;
    private ArrayList<Artist> artists;

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox topRecommendationVBox;

    @FXML
    private VBox trackVBox;

    @FXML
    private VBox albumVBox;

    @FXML
    private VBox artistVBox;

    @FXML
    private Label welcomeLabel;

    /**
     * Initialize HomeController
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        welcomeLabel.setText("Welcome, " + DBTools.getUserId());
        initProperty();

        //NOTE: DISPLAY RECOMMENDATION MODEL RESULTS HERE


    }

    /**
     * Initialize the behaviors and properties of the UI elements
     */
    private void initProperty() {
        topRecommendationVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        topBorderPane.visibleProperty().addListener(((observable, oldValue, newValue) -> {
            loadAll();
        }));
    }


    /**
     * Set the tracks to be displayed
     *
     * @param tracks ArrayList of tracks to be displayed
     */
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    /**
     * Set the albums to be displayed
     *
     * @param albums ArrayList of albums to be displayed
     */
    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }


    /**
     * Set the artists to be displayed
     *
     * @param artists ArrayList of artists to be displayed
     */
    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    /**
     * Set controllers, then load content
     *
     * @param mainController   instance of MainController
     * @param navbarController instance of NavbarController
     * @param playerController instance of PlayerController
     */
    public void setControllers(MainController mainController, NavbarController navbarController,
                               PlayerController playerController) {
        this.playerController = playerController;
        this.navbarController = navbarController;
        this.mainController = mainController;

        //after the controllers are ready, load contents
        loadAll();
    }

    /**
     * Loads all item to be displayed
     */
    private void loadAll() {
        if (tracks == null) {
            setTracks(RecTools.popTracks(21));
            if (tracks != null && tracks.size() != 0){
                loadItems(tracks);
            }
        } else if (tracks.size() != 0){
            loadItems(tracks);
        }
        if (artists == null) {
            setArtists(RecTools.popArtists(21));
            if (artists != null && artists.size() != 0){
                loadItems(artists);
            }
        } else if (artists.size() != 0){
            loadItems(artists);
        }

        if (albums == null) {
            setAlbums(RecTools.popAlbums(21));
            if (albums != null && albums.size() != 0){
                loadItems(albums);
            }
        } else if (albums.size() != 0){
            loadItems(albums);
        }
    }

    /**
     * Create display container and load items to page.
     * Items can be Tracks, AlbumSimples, or Artists.
     *
     * @param items ArrayList of Track, AlbumSimple, or Artist
     */
    private void loadItems(ArrayList items) {
        //if there are recommended items
        if (items.size() != 0) {
            ArrayList<ScrollPane> panes = new ArrayList<>();
            ScrollPane pane;

            //each row contains 7 item, store in panes array list
            int n = 7;
            int itemSize = items.size();
            for (int i = 0; i < itemSize; i += n) {
                if (i + n < itemSize) {
                    pane = new ItemScrollPane(new ArrayList(items.subList(i, i + n)),
                            mainController, navbarController, playerController, 0);
                    pane.prefWidthProperty().bind(mainScrollPane.widthProperty());
                    panes.add(pane);
                } else {
                    pane = new ItemScrollPane(new ArrayList(items.subList(i, itemSize)),
                            mainController, navbarController, playerController, 0);
                    pane.prefWidthProperty().bind(mainScrollPane.widthProperty());
                    panes.add(pane);
                    break;
                }
            }

            //set the location of each pane, depending of the item's type
            if (items.get(0) instanceof Track) {
                trackVBox.getChildren().setAll(panes);
            } else if (items.get(0) instanceof Album) {
                albumVBox.getChildren().setAll(panes);
            } else if (items.get(0) instanceof Artist) {
                artistVBox.getChildren().setAll(panes);
            }
        }
    }
}
