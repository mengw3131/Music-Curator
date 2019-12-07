package com.curator.controllers;

import com.curator.models.Album;
import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.SpotifyTools;
import com.curator.views.ItemScrollPane;
import com.curator.views.TrackListVBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to artist_page.fxml. Shows tracks, albums by artist
 */
public class ArtistPageController implements Initializable {
    private ArrayList<Track> tracks;
    private ArrayList<Album> albums;
    private NavbarController navbarController;
    private PlayerController playerController;
    private MainController mainController;

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox mainVBox;

    @FXML
    private ImageView artistImage;

    @FXML
    private Label artistName;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private AnchorPane trackTabPane;

    @FXML
    private ScrollPane trackTabPaneScroll;

    @FXML
    private VBox albumTabPaneVBox;

    @FXML
    private ScrollPane albumTabScrollPane;


    /**
     * Initialize controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Set controllers, and initialize property
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

        initProperty();
    }

    /**
     * Set the width and height bindings
     */
    private void initProperty() {
        mainController.mainPane.getChildren().add(topBorderPane);

        topBorderPane.prefHeightProperty().bind(mainController.mainPane.heightProperty());
        topBorderPane.prefWidthProperty().bind(mainController.mainPane.widthProperty());

        mainVBox.prefHeightProperty().bind(mainScrollPane.heightProperty());
        mainTabPane.prefHeightProperty().bind(mainScrollPane.heightProperty());
        trackTabPaneScroll.prefHeightProperty().bind(mainScrollPane.heightProperty());
        trackTabPane.prefHeightProperty().bind(mainScrollPane.heightProperty());

        mainVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        mainTabPane.prefWidthProperty().bind(mainScrollPane.widthProperty());
        trackTabPaneScroll.prefWidthProperty().bind(mainScrollPane.widthProperty());
        trackTabPane.prefWidthProperty().bind(mainScrollPane.widthProperty());


        albumTabScrollPane.prefWidthProperty().bind(mainScrollPane.widthProperty());
        albumTabPaneVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
    }

    /**
     * Set artist to be display and loads its albums and tracks
     *
     * @param artist artist to be displayed
     */
    public void setArtist(Artist artist) {
        //set UI contents
        artistName.setText(artist.getName());

        //set image if artist's image exists
        if (artist.getImages().size() != 0) {
            artistImage.setImage(artist.getImages().get(0));
        }

        //once artist is determined, get and load artist's albums and tracks
        setAlbums(SpotifyTools.getArtistAlbums(artist.getArtistID(), 16));
        setTracks(SpotifyTools.getArtistTopTracks(artist.getArtistID()));
        loadAlbums();
        loadTracks();
    }

    /**
     * Set the albums of the artists to be displayed
     */
    private void setAlbums(ArrayList<Album> albums) {
        this.albums = albums; //limit is max 16
    }

    /**
     * Set the albums of the artists to be displayed
     */
    private void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
        for (int i = 0; i < 3; i++) {
            tracks.addAll(albums.get(0).getTracks());
        }
    }

    /**
     * Load the tracks to the page
     */
    private void loadTracks() {
        VBox trackListVBox = new TrackListVBox(tracks, mainController, navbarController, playerController).asVBox();
        trackListVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        trackTabPaneScroll.setContent(trackListVBox);
    }

    /**
     * Load the albums to the page
     * TODO: REFACTOR
     */
    private void loadAlbums() {
        int remaining = albums.size();
        int i = 0;
        while (remaining > 0) {
            if (remaining >= 8) {
                albumTabPaneVBox.getChildren().add(new ItemScrollPane(new ArrayList(albums.subList(i, i + 8)),
                        mainController, navbarController, playerController, 1));
                remaining -= 8;
                i += 8;
            } else {
                albumTabPaneVBox.getChildren().add(new ItemScrollPane(new ArrayList(albums.subList(i, i + remaining)),
                        mainController, navbarController, playerController, 1));
                break;
            }
        }
    }
}

