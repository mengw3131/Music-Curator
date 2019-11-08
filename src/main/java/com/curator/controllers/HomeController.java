package com.curator.controllers;

import com.curator.Main;
import com.curator.YoutubeTools;
import com.curator.object_models.Song;
import com.curator.object_models.Sound;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Track;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller to home.fxml
 */
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


    //TODO: home page loads too slow: for performance, create 6-pane HBox instead creating one by one


    //TODO: on mouse hover, dim background, show play button
    /**
     * Create a track pane from Track object. Plays music on click.
     * @param track
     * @return
     */
    public AnchorPane createTrackPane(Track track) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/songpane.fxml"));
        AnchorPane pane = null;

        try {
            pane = loader.load();

            //set track cover image
            ImageView imageView = (ImageView) pane.getChildren().get(0);
            imageView.setImage(track.getAlbum().getImages()[0].getImage());

            //set track name and artist name labels
            Label trackName = (Label)pane.getChildren().get(1);
            Label trackArtist = (Label)pane.getChildren().get(2);
            trackName.setText(track.getName());
            trackArtist.setText(track.getArtistsString());

            //handle image on click
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Sound sound = YoutubeTools.getMusicFileFromQuery(
                            YoutubeTools.createYoutubeQuery(track.getName(), track.getArtistsString())
                    );
                    Song song = new Song(sound.getPath(),
                            track.getName(), String.valueOf(sound.length()),track.getArtistsString(),
                            track.getAlbumString(),"");
                    playerController.setCurrentSong(song);

                    event.consume();
                }
            });

            pane.getChildren().set(0, imageView);
            pane.getChildren().set(1, trackName);
            pane.getChildren().set(2, trackArtist);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    /**
     * Creates a horizontal box of track panes. Default of 6 tracks.
     *
     * @param tracks
     * @return HBox of track panes in tracks
     */
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


    /**
     * Initialize HomeController
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this method currently contains placeholder values
        //this is supposed to be the receiver of the recommendation model



        //set background color
        mainScrollPane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        //hide horizontal & vertical scroll bar
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //set top recommendation container height's to follow mainScrollPane's
        topRecommendationVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());


        //Set placeholder values
        Label label = new Label("Top Recommendation");
        label.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label);

        Track[] tracks =  api.searchTracks("Ella Fitzgerald", 6);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));

        tracks = api.searchTracks("Louis Armstrong", 6);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));

        Label label2 = new Label("Hot This Week");
        label2.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label2.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label2);

        tracks = api.searchTracks("Al Bowlly", 6);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));

        Label label3 = new Label("Mood");
        label3.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label3.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label3);

        tracks = api.searchTracks("Calm", 6);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));




        //TODO: Fix scroll to the bottom issues
        mainScrollPane.setContent(topRecommendationVBox);
        mainScrollPane.setMaxHeight(500);
    }

    /**
     * Injects mainController
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Injects playerController
     * @param playerController
     */
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    /**
     * Injects api object
     * @param api
     */
    public void setApi(SpotifyApi api) { this.api = api; }



}
