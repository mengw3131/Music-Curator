package com.curator.controllers;

import com.curator.models.Album;
import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.SpotifyTools;
import com.curator.views.ItemScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to discover.fxml
 */
public class DiscoverController implements Initializable {
    MainController mainController;
    PlayerController playerController;
    NavbarController navbarController;

    @FXML
    private ScrollPane discoverScrollPane;

    @FXML
    private VBox discoverVBox;

    @FXML
    private VBox trackVBox;

    @FXML
    private VBox artistVBox;

    @FXML
    private VBox albumVBox;

    @FXML
    private TextField searchBar;

    @FXML
    private Label trackLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private Label albumLabel;

    /**
     * Initialize the controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        artistLabel.setVisible(false);
        albumLabel.setVisible(false);
        trackLabel.setVisible(false);

//        trackVBox.setMaxHeight(0);
//        trackLabel.setMaxHeight(0);
//        albumVBox.setMaxHeight(0);
//        albumLabel.setMaxHeight(0);
//        artistVBox.setMaxHeight(0);

//        trackVBox.setVisible(false);
//        trackLabel.setVisible(false);
//        albumVBox.setVisible(false);
//        albumLabel.setVisible(false);
//        artistVBox.setVisible(false);
//        artistLabel.setVisible(false);


        discoverVBox.prefWidthProperty().bind(discoverScrollPane.widthProperty());

        //when user press Enter, updateIndex results
        searchBar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                updateSearchResult(searchBar.getText());
            }
        });
    }

    /**
     * Set controllers, and initialize property
     * @param mainController instance of MainController
     * @param navbarController instance of NavbarController
     * @param playerController instance of PlayerController
     */
    public void setControllers(MainController mainController, NavbarController navbarController,
                               PlayerController playerController) {
        this.playerController = playerController;
        this.navbarController = navbarController;
        this.mainController = mainController;
    }

    /**
     * Clear contents of VBox
     */
    private void clearVBox(){
        trackVBox.getChildren().clear();
        albumVBox.getChildren().clear();
        artistVBox.getChildren().clear();
        trackVBox.setPrefHeight(0);
        albumVBox.setPrefHeight(0);
        artistVBox.setPrefHeight(0);
    }

    /**
     * Updates search result given query
     * @param query search query
     */
    private void updateSearchResult(String query) {
        clearVBox();
        artistLabel.setVisible(true);
        albumLabel.setVisible(true);
        trackLabel.setVisible(true);

        ArrayList<Track> tracks = SpotifyTools.searchTracks(query, 8);
        if (tracks.size() == 0) {
            trackLabel.setText("No tracks found");
        } else {
            trackLabel.setText("Tracks");
            trackVBox.setPrefHeight(200);
            loadResults(tracks);
        }

        ArrayList<Album> albums = SpotifyTools.searchAlbums(query, 8);
        if (albums.size() == 0) {
            albumLabel.setText("No albums found");
        } else {
            albumLabel.setText("Albums");
            albumVBox.setPrefHeight(200);
            loadResults(albums);
        }

        ArrayList<Artist> artists = SpotifyTools.searchArtists(query, 8);
        if (artists.size() == 0) {
            artistLabel.setText("No artists found");
        } else {
            artistLabel.setText("Artists");
            artistVBox.setPrefHeight(200);
            loadResults(artists);
        }
    }

    /**
     * Create display container and load search results
     * @param items ArrayList of Artist, Track, or Album
     */
    private void loadResults(ArrayList items) {
        ScrollPane pane = new ItemScrollPane(items, mainController, navbarController, playerController, 0);
        pane.prefWidthProperty().bind(discoverScrollPane.widthProperty());

        if (items.get(0) instanceof Track) {
            trackVBox.getChildren().add(pane);
        } else if (items.get(0) instanceof Album || items.get(0) instanceof Album) {
            albumVBox.getChildren().add(pane);
        } else if (items.get(0) instanceof Artist) {
            artistVBox.getChildren().add(pane);
        }
    }
}
