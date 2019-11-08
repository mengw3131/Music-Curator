package com.curator.controllers;

import com.curator.Main;
import com.wrapper.spotify.SpotifyApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller to main.fxml
 */
public class MainController implements Initializable {

    //TODO: change to dependency injection to make api variable private
    SpotifyApi api = Main.api;
    private PlayerController playerController;
    private HomeController homeController;

    @FXML
    AnchorPane mainPane;

    @FXML
    Button homeButton;

    @FXML
    Button myMusicButton;

    @FXML
    Button favoritesButton;

    @FXML
    Button madeForYouButton;

    @FXML
    Button playlistsButton;

    @FXML
    Button profileButton;

    @FXML
    HBox playerContainer;

    /**
     * Triggered when home button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws IOException {
        mainPane.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
        HBox home = loader.load();

        //set width and height to follow container pane (for resizing)
        home.prefWidthProperty().bind(mainPane.widthProperty());
        home.prefHeightProperty().bind(mainPane.widthProperty());

        //set mainpane to be HBox from home.fxml
        mainPane.getChildren().setAll(home);

        //get controller of home.fxml
        homeController = loader.getController();

        //pass parent controller to child
        homeController.setMainController(this);
        homeController.setPlayerController(playerController);
    }

    /**
     * Triggered when My Music button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleMyMusicButtonAction(ActionEvent event) {
        mainPane.getChildren().clear();

        //TODO: TO BE IMPLEMENTED

        //set temporary view
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("My Music page"));
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().setAll(anchorPane);
    }

    /**
     * Triggered when Favorites button in the left bar is clicked
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleFavoritesButtonAction(ActionEvent event) throws IOException {
        mainPane.getChildren().clear();

        TableView tableView = FXMLLoader.load(getClass().getResource("/views/favorites.fxml"));

        //set tableview to fill the whole container upon resize
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //set tableview to follow container's width and height (for resizing)
        tableView.prefWidthProperty().bind(mainPane.widthProperty());
        tableView.prefHeightProperty().bind(mainPane.heightProperty());

        //set mainpane to be tableview from favorites.fxml
        mainPane.getChildren().setAll(tableView);
    }

    /**
     * Triggered when MadeForYou button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleMadeForYouButtonAction(ActionEvent event) {
        mainPane.getChildren().clear();

        //TODO: TO BE IMPLEMENTED

        //set temporary view
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("Made For You page"));
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().setAll(anchorPane);
    }

    /**
     * Triggered when Playlists button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handlePlaylistsButtonAction(ActionEvent event) {
        mainPane.getChildren().clear();

        //TODO: TO BE IMPLEMENTED

        //set temporary view
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("Playlist page"));
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().setAll(anchorPane);
    }

    /**
     * Triggered when Profile button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleProfileButtonAction(ActionEvent event) {
        mainPane.getChildren().clear();

        //TODO: TO BE IMPLEMENTED

        //set temporary view
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("Profile page"));
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().setAll(anchorPane);
    }

    /**
     * Load the music player (at the bottom of the app)
     */
    private void loadPlayer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/player.fxml"));

            HBox player = loader.load();

            //set player width to follow its container
            player.prefWidthProperty().bind(playerContainer.widthProperty());
            playerContainer.getChildren().setAll(player);

            playerController = loader.getController();
            playerController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize controller
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPlayer();
    }
}
