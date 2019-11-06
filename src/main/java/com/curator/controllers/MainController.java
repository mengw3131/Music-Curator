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

public class MainController implements Initializable {
    SpotifyApi api = Main.api;

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

    PlayerController playerController;
    HomeController homeController;

    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws IOException {
        mainPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));

        HBox home = loader.load();
        home.prefWidthProperty().bind(mainPane.widthProperty());
        home.prefHeightProperty().bind(mainPane.widthProperty());
        mainPane.getChildren().setAll(home);

        homeController = loader.getController();
        homeController.setApi(api);
        homeController.setMainController(this);
        homeController.setPlayerController(playerController);
    }

    @FXML
    private void handleMyMusicButtonAction(ActionEvent event) {
        mainPane.getChildren().clear();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("My Music page"));

        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
//        mainPane.getChildren().setAll(anchorPane);
        mainPane.getChildren().addAll(anchorPane);
    }

    @FXML
    private void handleFavoritesButtonAction(ActionEvent event) throws IOException {
        mainPane.getChildren().clear();

        TableView tableView = FXMLLoader.load(getClass().getResource("/views/favorites.fxml"));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.prefWidthProperty().bind(mainPane.widthProperty());
        tableView.prefHeightProperty().bind(mainPane.heightProperty());

        BorderPane borderPane = new BorderPane();
        borderPane.setRight(tableView);

        mainPane.getChildren().setAll(borderPane);
    }

    @FXML
    private void handleMadeForYouButtonAction(ActionEvent event) {
        mainPane.getChildren().clear();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("Made For You page"));

        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().setAll(anchorPane);
    }

    @FXML
    private void handlePlaylistsButtonAction(ActionEvent event) {
        mainPane.getChildren().clear();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("Playlist page"));

        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().setAll(anchorPane);
    }

    @FXML
    private void handleProfileButtonAction(ActionEvent event) {
        mainPane.getChildren().clear();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(new Label("Profile page"));

        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.getChildren().setAll(anchorPane);
    }

    private void loadPlayer(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/player.fxml"));

            HBox player = loader.load();
            player.prefWidthProperty().bind(playerContainer.widthProperty());
            playerContainer.getChildren().setAll(player);

            playerController = loader.getController();
            playerController.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPlayer();
    }

    public void setApi(SpotifyApi api) { this.api = api; }
}
