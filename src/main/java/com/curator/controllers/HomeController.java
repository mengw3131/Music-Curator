package com.curator.controllers;

import com.curator.Main;
import com.curator.object_models.Song;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Track;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    SpotifyApi api = Main.api;
    MainController mainController;
    PlayerController playerController;

    @FXML
    ScrollPane mainScrollPane;

    @FXML
    VBox mainVBox;

    @FXML
    VBox topRecommendationVBox;

    @FXML
    Label recommendedLabel;

    @FXML
    GridPane mainGridPane;


    public AnchorPane createTrackPane(Track track) {
        AnchorPane pane = new AnchorPane();
        pane.setMinHeight(200);
        pane.setMinWidth(150);

        Label trackName = new Label(track.getName());
        AnchorPane.setBottomAnchor(trackName, 30.0);
        AnchorPane.setLeftAnchor(trackName, 8.0);

        Label trackArtists = new Label(track.getArtistsString());
        AnchorPane.setBottomAnchor(trackArtists, 10.0);
        AnchorPane.setLeftAnchor(trackArtists, 8.0);


        ImageView imageView = new ImageView();

        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setStyle(
                "-fx-background-color: none; -fx-content-display: top; -fx-wrap-text: true;" +
                        "-fx-border-insets: 5px; -fx-background-insets: 5px; -fx-padding: 5px"
        );
        AnchorPane.setBottomAnchor(imageView, 49.0);
        AnchorPane.setLeftAnchor(imageView, 8.0);
        AnchorPane.setTopAnchor(imageView, 8.0);
        AnchorPane.setRightAnchor(imageView, 8.0);

        pane.getChildren().addAll(imageView, trackName, trackArtists);

//        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<T>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("pressed");
//
//            }
//        });


        return pane;
    }

    public HBox createRecommendationBox(Track[] tracks){
        HBox hBox = new HBox();
        hBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        hBox.minHeight(150);
        hBox.setPrefHeight(150);
        hBox.setAlignment(Pos.TOP_CENTER);

        for (Track track:tracks) {
            hBox.getChildren().add(createTrackPane(track));
        }

        return hBox;
    }

    public HBox createRecommendation() {
        HBox hBox = new HBox();
        hBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        hBox.minHeight(150);
        hBox.setPrefHeight(150);

        hBox.setAlignment(Pos.TOP_CENTER);
        Song song = new Song(getClass().getResource("/wavs/sample.wav").getPath(),
                "Sleep Away", "99:99:99", "Bob Acri",
                "Sleep Away", "Jazz");

        Song song2 = new Song(getClass().getResource("/wavs/sample2.wav").getPath(),
                "Arabesque No. 1", "99:99:99", "Claude Debussy",
                "", "Classical");


        Button btn = createRecommendedButton(song);
        btn.setBackground(new Background(new BackgroundFill(Color.web("#000000"),
                CornerRadii.EMPTY, Insets.EMPTY)));

        hBox.getChildren().add(btn);
        hBox.getChildren().add(createRecommendedButton(song2));
        hBox.getChildren().add(createRecommendedButton(song));
        hBox.getChildren().add(createRecommendedButton(song2));
        hBox.getChildren().add(createRecommendedButton(song));

        return hBox;
    }

    public Button createRecommendedButton(Song song) {
        Button button = new Button();
        button.setMinHeight(200);
        button.setMinWidth(150);

        ImageView imageView = new ImageView(song.getCover());
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        button.setGraphic(imageView);

        button.setStyle(
                "-fx-background-color: none; -fx-content-display: top; -fx-wrap-text: true;" +
                        "-fx-border-insets: 5px; -fx-background-insets: 5px; -fx-padding: 5px"
        );

        String filename = "sample.wav";

        button.setWrapText(true);
        button.setText(song.getName() + "\n" + song.getArtist());
        button.setTextAlignment(TextAlignment.CENTER);

        //TODO: Onclick, fetch song, update player,
        button.setOnAction(event -> {
            playerController.setCurrentSong(song);
        });
        return button;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainScrollPane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        topRecommendationVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        topRecommendationVBox.maxHeightProperty().bind(mainScrollPane.heightProperty());
        Label label = new Label("Top Recommendation");
        label.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label);

        topRecommendationVBox.getChildren().add(createRecommendation());
        topRecommendationVBox.getChildren().add(createRecommendation());
        topRecommendationVBox.getChildren().add(createRecommendation());

        Label label2 = new Label("Hot This Week");
        label2.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label2.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label2);

        topRecommendationVBox.getChildren().add(createRecommendation());
        topRecommendationVBox.getChildren().add(createRecommendation());
        topRecommendationVBox.getChildren().add(createRecommendation());

        Label label3 = new Label("Mood");
        label3.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label3.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label3);

        topRecommendationVBox.getChildren().add(createRecommendation());
        topRecommendationVBox.getChildren().add(createRecommendation());
        mainScrollPane.setContent(topRecommendationVBox);

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void setApi(SpotifyApi api) { this.api = api; }



}
