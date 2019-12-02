package com.curator.controllers;

import com.curator.models.AlbumSimple;
import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.SpotifyTools;
import com.curator.views.ItemScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ArrayList<AlbumSimple> albums;
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

    /**
     * Initialize HomeController
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initProperty();

        //NOTE: DISPLAY RECOMMENDATION MODEL RESULTS HERE
        setTracks(SpotifyTools.searchTracks("Art Tatum", 10));
        setAlbums(SpotifyTools.searchAlbums("Oscar Peterson", 8));
        setArtists(SpotifyTools.searchArtists("John", 8));
    }

    /**
     * Initialize the behaviors and properties of the UI elements
     */
    private void initProperty() {
        topRecommendationVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        topBorderPane.visibleProperty().addListener(((observable, oldValue, newValue) -> { loadAll(); }));
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
    public void setAlbums(ArrayList<AlbumSimple> albums) {
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
        loadItems(tracks);
        loadItems(artists);
        loadItems(albums);
    }

    /**
     * Create display container and load items to page.
     * Items can be Tracks, AlbumSimples, or Artists.
     *
     * @param items ArrayList of Track, AlbumSimple, or Artist
     */
    private void loadItems(ArrayList items) {
        ScrollPane pane = new ItemScrollPane(items, mainController, navbarController, playerController,
                0);
        pane.prefWidthProperty().bind(mainScrollPane.widthProperty());

        //set the location of each pane, depending of the item's type
        if (items.get(0) instanceof Track) {
            trackVBox.getChildren().setAll(pane);
        } else if (items.get(0) instanceof AlbumSimple) {
            albumVBox.getChildren().setAll(pane);
        } else if (items.get(0) instanceof Artist) {
            artistVBox.getChildren().setAll(pane);
        }
    }
}
