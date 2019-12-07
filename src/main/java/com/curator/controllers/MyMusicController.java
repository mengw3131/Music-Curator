package com.curator.controllers;

import com.curator.models.Album;
import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.views.ItemScrollPane;
import com.curator.views.TrackListVBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controllers to my_music.fxml
 */
public class MyMusicController implements Initializable {

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
    private TabPane mainTabPane;

    @FXML
    private Tab trackTab;

    @FXML
    private AnchorPane trackTabPane;

    @FXML
    private ScrollPane trackTabPaneScroll;

    @FXML
    private Tab albumTab;

    @FXML
    private VBox albumTabPaneVBox;

    @FXML
    private Tab artistTab;

    @FXML
    private VBox artistTabPaneVBox;

    @FXML
    private ScrollPane albumTabScrollPane;

    @FXML
    private ScrollPane artistTabScrollPane;

    @FXML
    private AnchorPane albumTabPane;

    @FXML
    private AnchorPane artistTabPane;


    /**
     * Initialize controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //if respective tab is selected, reload contents

        trackTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                loadTracks();
            }
        });

        albumTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                loadAlbums();
            }
        });

        artistTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                loadArtists();
            }
        });

        //when my music page becomes visible, reload respective tab if selected
        topBorderPane.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                if (trackTab.isSelected()){
                    loadTracks();
                }
                if (albumTab.isSelected()){
                    loadAlbums();
                }
                if (artistTab.isSelected()){
                    loadArtists();
                }
            }
        });
    }

    /**
     * Loads tracks to be displayed
     */
    private void loadTracks(){
        ArrayList<Track> tracks = DBTools.getUserLikedSongs();
        VBox trackListVBox = new TrackListVBox(tracks, mainController, navbarController, playerController).asVBox();
        trackListVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        trackTabPaneScroll.setContent(trackListVBox);
    }

    /**
     * Loads albums to be displayed
     */
    private void loadAlbums() {
        albumTabPaneVBox.getChildren().clear();
        ArrayList<Album> albums = DBTools.getUserLikedAlbum();

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

    /**
     * Loads artists to be displayed
     */
    private void loadArtists() {

        artistTabPaneVBox.getChildren().clear();
        ArrayList<Artist> artists = DBTools.getUserLikedArtists();

        int remaining = artists.size();
        int i = 0;
        while (remaining > 0) {
            if (remaining >= 8) {
                artistTabPaneVBox.getChildren().add(new ItemScrollPane(new ArrayList(artists.subList(i, i + 8)),
                        mainController, navbarController, playerController, 1));
                remaining -= 8;
                i += 8;
            } else {
                artistTabPaneVBox.getChildren().add(new ItemScrollPane(new ArrayList(artists.subList(i, i + remaining)),
                        mainController, navbarController, playerController, 1));
                break;
            }
        }
    }

    /**
     * Set controllers, and initialize property + load tracks
     * @param mainController main controller of the app
     * @param navbarController navbar controller of the app
     * @param playerController player controller of the app
     */
    public void setControllers(MainController mainController, NavbarController navbarController,
                               PlayerController playerController) {
        this.playerController = playerController;
        this.navbarController = navbarController;
        this.mainController = mainController;

        initProperty();
        loadTracks();
    }

    /**
     * Set the width and height bindings
     */
    private void initProperty(){
        //add to container & bind display config
        mainController.mainPane.getChildren().add(topBorderPane);
        topBorderPane.prefHeightProperty().bind(mainController.mainPane.heightProperty());
        topBorderPane.prefWidthProperty().bind(mainController.mainPane.widthProperty());
        mainVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        mainTabPane.prefWidthProperty().bind(mainScrollPane.widthProperty());

        //make height follow its parents
        mainVBox.prefHeightProperty().bind(mainScrollPane.heightProperty());
        mainTabPane.prefHeightProperty().bind(mainScrollPane.heightProperty());

        trackTabPaneScroll.prefWidthProperty().bind(mainScrollPane.widthProperty());
        trackTabPaneScroll.prefHeightProperty().bind(mainScrollPane.heightProperty());

        trackTabPane.prefWidthProperty().bind(mainScrollPane.widthProperty());
        trackTabPane.prefHeightProperty().bind(mainScrollPane.heightProperty());

        albumTabScrollPane.prefWidthProperty().bind(mainScrollPane.widthProperty());
        albumTabPane.prefHeightProperty().bind(mainScrollPane.heightProperty());
        albumTabPaneVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());

        artistTabScrollPane.prefWidthProperty().bind(mainScrollPane.widthProperty());
        artistTabPane.prefHeightProperty().bind(mainScrollPane.heightProperty());
        artistTabPaneVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
    }
}
