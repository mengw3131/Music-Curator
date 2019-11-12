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
    private DiscoverController discoverController;

    private BorderPane homePane;
    private BorderPane discoverPane;
    private TableView favoritesTable;



    //set reuseable loader to improve performance
    FXMLLoader loader;

    @FXML
    AnchorPane mainPane;

    @FXML
    Button homeButton;

    @FXML
    Button discoverButton;

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

    private void hideAllMainPane(){
        if (homePane != null) {
            homePane.setVisible(false);
        }

        if (favoritesTable != null) {
            favoritesTable.setVisible(false);
        }

        if (discoverPane != null){
            discoverPane.setVisible(false);
        }
    }

    private void untoggleAllButton(){
           homeButton.setStyle("-fx-background-color: none;");
           discoverButton.setStyle("-fx-background-color: none;");
           favoritesButton.setStyle("-fx-background-color: none;");
           madeForYouButton.setStyle("-fx-background-color: none;");
           myMusicButton.setStyle("-fx-background-color: none;");
           playlistsButton.setStyle("-fx-background-color: none;");
           profileButton.setStyle("-fx-background-color: none;");
    }



    /**
     * Triggered when home button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws IOException {
        hideAllMainPane();
        untoggleAllButton();
        homeButton.setStyle("-fx-background-color: lightgrey;");

        if (homePane == null){
            loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));

            homePane = loader.load();
            ScrollPane scrollPane = (ScrollPane) homePane.getChildren().get(0);

            //set BorderPane to follow mainpane's dimension (for resizing)
            homePane.prefHeightProperty().bind(mainPane.heightProperty());
            homePane.prefWidthProperty().bind(mainPane.widthProperty());

            //hide horizontal and vertical scrollbar
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            //
            scrollPane.setMinWidth(800);
            scrollPane.setMaxWidth(1000);

            //set mainpane to be BorderPane from home.fxml
            mainPane.getChildren().add(homePane);

            //pass parent controller to child
            homeController = loader.getController(); //get controller of home.fxml
            homeController.setMainController(this);
            homeController.setPlayerController(playerController);
        }

        homePane.setVisible(true);
    }

    @FXML
    private void handleDiscoverButtonAction(ActionEvent event){
        hideAllMainPane();
        untoggleAllButton();
        discoverButton.setStyle("-fx-background-color: lightgrey;");

        if (discoverPane == null){
            loader = new FXMLLoader(getClass().getResource("/views/discover.fxml"));
            try {
                discoverPane = loader.load();
                /*
                   Hierarchy
                   BorderPane
                      ScrollPane
                      AnchorPane
                      AnchorPane
                      SearchBar
                 */
                ScrollPane scrollPane = (ScrollPane) discoverPane.getChildren().get(0);
                VBox discoverVBox = (VBox) scrollPane.getContent();

                //set BorderPane to follow mainpane's dimension (for resizing)
                discoverPane.prefHeightProperty().bind(mainPane.heightProperty());
                discoverPane.prefWidthProperty().bind(mainPane.widthProperty());

                //set mainpane to be BorderPane from home.fxml
                mainPane.getChildren().add(discoverPane);

                //hide horizontal and vertical scrollbar
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setMinWidth(800);
                scrollPane.setMaxWidth(1000);

                //center the vbox and its children (incl. search bar
                discoverVBox.prefWidthProperty().bind(scrollPane.widthProperty());

                discoverController = loader.getController(); //get controller of discover.fxml
                discoverController.setMainController(this);
                discoverController.setPlayerController(playerController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        discoverPane.setVisible(true);
    }

    /**
     * Triggered when My Music button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleMyMusicButtonAction(ActionEvent event) {
        hideAllMainPane();
        untoggleAllButton();
        myMusicButton.setStyle("-fx-background-color: lightgrey;");

        //TODO: TO BE IMPLEMENTED

        //set temporary view
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("My Music page"));
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().add(anchorPane);
    }

    /**
     * Triggered when Favorites button in the left bar is clicked
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleFavoritesButtonAction(ActionEvent event) throws IOException {
        hideAllMainPane();
        untoggleAllButton();
        favoritesButton.setStyle("-fx-background-color: lightgrey;");


        if (favoritesTable == null) {
            favoritesTable = loader.load(getClass().getResource("/views/favorites.fxml"));

            //set tableview to fill the whole container upon resize
            favoritesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            //set tableview to follow container's width and height (for resizing)
            favoritesTable.prefWidthProperty().bind(mainPane.widthProperty());
            favoritesTable.prefHeightProperty().bind(mainPane.heightProperty());

            //set mainpane to be tableview from favorites.fxml
            mainPane.getChildren().add(favoritesTable);
        }
        favoritesTable.setVisible(true);
    }

    /**
     * Triggered when MadeForYou button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleMadeForYouButtonAction(ActionEvent event) {
        hideAllMainPane();
        untoggleAllButton();
        madeForYouButton.setStyle("-fx-background-color: lightgrey;");


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
        hideAllMainPane();
        untoggleAllButton();
        playlistsButton.setStyle("-fx-background-color: lightgrey;");


        //TODO: TO BE IMPLEMENTED

        //set temporary view
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("Playlist page"));
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().add(anchorPane);
    }

    /**
     * Triggered when Profile button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleProfileButtonAction(ActionEvent event) {
        hideAllMainPane();
        untoggleAllButton();
        profileButton.setStyle("-fx-background-color: lightgrey;");



        //TODO: TO BE IMPLEMENTED

        //set temporary view
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("Profile page"));
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().add(anchorPane);
    }

    /**
     * Load the music player (at the bottom of the app)
     */
    private void loadPlayer() {
        try {
            loader = new FXMLLoader(getClass().getResource("/views/player.fxml"));

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
        System.out.println("Loading player ... ");
        loadPlayer();
        System.out.println("Loading panes... ");
        favoritesButton.fire();
        discoverButton.fire();
        homeButton.fire();
    }
}
