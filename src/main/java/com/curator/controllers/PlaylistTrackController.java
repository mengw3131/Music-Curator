package com.curator.controllers;

import com.curator.models.Playlist;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.views.TrackListVBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *  Controller to the playlist_track_page.fxml
 */
public class PlaylistTrackController implements Initializable {
    private PlayerController playerController;
    private MainController mainController;
    private NavbarController navbarController;
    private Playlist playlist;

    @FXML
    private VBox mainVBox;

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private ImageView playlistImage;

    @FXML
    private Label playlistName;

    /**
     * Initialize controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
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
     * Set UI elements size bindings
     */
    private void initProperty() {
        mainController.mainPane.getChildren().add(topBorderPane);
        topBorderPane.prefHeightProperty().bind(mainController.mainPane.heightProperty());
        topBorderPane.prefWidthProperty().bind(mainController.mainPane.widthProperty());
    }


    /**
     * Set Playlist (full) to be displayed on page
     *
     * @param playlist playlist to be displayed on page
     */
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        playlistImage.setImage(playlist.getImage());
        playlistName.setText(playlist.getName());

        //load tracks after playlist is determined
        loadTracks();
    }

    /**
     * Load tracks of the playlist to be displayed
     */
    private void loadTracks() {
        mainVBox.getChildren().addAll(new TrackListVBox(playlist.getTracks(), mainController,
                navbarController, playerController).asVBox());
    }

    /**
     * Delete track from playlist
     * @param track track to be deleted
     */
    public void delete(Track track) {
        DBTools.removeTrackFromPlaylist(track, playlist);
        playlist = DBTools.getPlaylist(playlist.getId());

        //offset first two is image + playlist name
        mainVBox.getChildren().remove(2, 2 + playlist.getTracks().size() + 1);

        mainVBox.getChildren().addAll(new TrackListVBox(playlist.getTracks(), mainController,
                navbarController, playerController).asVBox());
    }
}
