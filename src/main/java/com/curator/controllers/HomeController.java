package com.curator.controllers;

import com.curator.tools.SpotifyTools;
import com.curator.tools.YoutubeTools;
import com.curator.models.*;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Controller to home.fxml
 */
public class HomeController implements Initializable {
    MainController mainController;
    PlayerController playerController;
    FXMLLoader loader = new FXMLLoader();

    @FXML
    ScrollPane mainScrollPane;

    @FXML
    VBox mainVBox;

    @FXML
    VBox topRecommendationVBox;


    //TODO: on mouse hover, dim background, show play button
    //TODO: OPTIMIZE performance




    /**
     * Creates a horizontal box of track panes. Default of 8 tracks.
     *
     * @param tracks
     * @return HBox of track panes in tracks
     */
    public ScrollPane createRecommendationBox(ArrayList<Track> tracks){

        /*
          Nodes Hierarchy:

          ScrollPane (isPannable true (can drag horizontally by mouse), hbarPolicy NEVER (hide scrollbar))
             HBox
                Pane (one for each track, default 8 panes)
                  ImageView (for track image)
                  Label (track name)
                  Label (track artist)
                  ImageView (for in-pane play button)
                  ImageView (for in-pane heart button)
                  ImageView (for in-pane addToPlaylist button)
        */
        ScrollPane pane = null;

        try {
            pane = new FXMLLoader(getClass().getResource("/views/tracks_hbox.fxml")).load();
            pane.prefWidthProperty().bind(mainScrollPane.widthProperty());

            HBox box = (HBox)pane.getContent();

            //loop on each music pane in HBox
            for (int i = 0; i < ((HBox)pane.getContent()).getChildren().size(); i++) {
                Track track = tracks.get(i);
                Pane subPane = (Pane)box.getChildren().get(i);
                ImageView trackImage = (ImageView) subPane.getChildren().get(0);
                Label trackName = (Label)subPane.getChildren().get(1);
                Label trackArtist = (Label)subPane.getChildren().get(2);
                ImageView inPanePlayButton = (ImageView) subPane.getChildren().get(3);
                ImageView inPaneHeartButton = (ImageView) subPane.getChildren().get(4);
                ImageView inPaneAddToPlaylistButton = (ImageView) subPane.getChildren().get(5);

                trackImage.setImage(track.getImage());
                trackName.setText(track.getName());
                trackArtist.setText(track.getArtistsString());


                //when mouse enter the pane
                subPane.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackImage.setOpacity(0.2);
                        inPanePlayButton.setOpacity(1);
                        inPaneHeartButton.setOpacity(1);
                        inPaneAddToPlaylistButton.setOpacity(1);

                        inPanePlayButton.setDisable(false);
                        inPaneHeartButton.setDisable(false);
                        inPaneAddToPlaylistButton.setDisable(false);
                    }
                });

                //when mouse exit the pane
                subPane.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackImage.setOpacity(1);
                        inPanePlayButton.setOpacity(0);
                        inPaneHeartButton.setOpacity(0);
                        inPaneAddToPlaylistButton.setOpacity(0);

                        inPanePlayButton.setDisable(true);
                        inPaneHeartButton.setDisable(true);
                        inPaneAddToPlaylistButton.setDisable(true);
                    }
                });

                //when play button inside pane is clicked
                inPanePlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Media media = YoutubeTools.getMusicFileFromQuery(
                                YoutubeTools.createYoutubeQuery(track.getName(), track.getArtistsString())
                        );
                        track.setMedia(media);
                        playerController.setCurrentTrack(track);
                        event.consume();
                    }
                });

                //when addToPlaylist button inside pane is clicked
                inPaneAddToPlaylistButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //TODO: IMPLEMENT
                        System.out.println("playlist clicked");
                    }
                });

                //when addToPlaylist button inside pane is clicked
                inPaneHeartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //TODO: IMPLEMENT
                        System.out.println("heart clicked");
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }


    /**
     * Initialize com.curator.controllers.HomeController
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this method currently contains placeholder values
        //this is supposed to be the receiver of the recommendation model

        //Set placeholder values
        Label label = new Label("Top Recommendation");
        label.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label);


        System.out.print("Getting 8 tracks... ");
        ArrayList<Track> tracks =  SpotifyTools.searchTracks("Claude Debussy", 8);
        System.out.println("Done");
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));

//        tracks = api.searchTracks("Louis Armstrong", 6);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));

        Label label2 = new Label("Hot This Week");
        label2.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label2.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label2);

//        tracks = api.searchTracks("Al Bowlly", 6);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));

        Label label3 = new Label("Mood");
        label3.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label3.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label3);

//        tracks = api.searchTracks("Calm", 6);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));
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
}
