package com.curator.controllers;

import com.curator.models.Album;
import com.curator.models.AlbumSimple;
import com.curator.tools.SpotifyTools;
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
 * Controller to album_page.fxml. Shows album and its tracks.
 */
public class AlbumPageController implements Initializable {
    private PlayerController playerController;
    private MainController mainController;
    private NavbarController navbarController;
    private Album album;

    @FXML
    private VBox mainVBox;

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private ImageView albumImage;

    @FXML
    private Label albumName;

    @FXML
    private Label artistsNames;

    @FXML
    private VBox secondaryVBox;

    @FXML
    private ScrollPane secondaryScrollPane;

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
     * Convert AlbumSimple to Album then display it
     *
     * @param albumSimple album to be displayed on page
     */
    public void setAlbum(AlbumSimple albumSimple) {
        this.album = SpotifyTools.getAlbum(albumSimple.getAlbumID());
        if (this.album != null){
            setAlbum(this.album);
        }
    }

    /**
     * Set Album to be displayed on page, then load track of the albums
     *
     * @param album album to be displayed on page
     */
    public void setAlbum(Album album) {
        this.album = album;
        if (album.getImages().size() != 0) {
            albumImage.setImage(album.getImages().get(0));
        }
        artistsNames.setText(album.getArtistsNames());
        albumName.setText(album.getName());

        loadTrack();
    }

    /**
     * Load the tracks of the current album to the page
     */
    public void loadTrack(){
        secondaryVBox.getChildren().add(new TrackListVBox(this.album.getTracks(), mainController,
                navbarController, playerController).asVBox());
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

        initProperty();
    }

    /**
     * Set the width and height bindings
     */
    private void initProperty(){
        mainController.mainPane.getChildren().add(topBorderPane);
        topBorderPane.prefHeightProperty().bind(mainController.mainPane.heightProperty());
        topBorderPane.prefWidthProperty().bind(mainController.mainPane.widthProperty());

        mainVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        mainVBox.prefHeightProperty().bind(mainScrollPane.heightProperty());

        secondaryVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        secondaryScrollPane.prefHeightProperty().bind(mainScrollPane.heightProperty());
    }
}
