package com.curator.controllers;

import com.curator.models.*;
import com.curator.tools.DBTools;
import com.curator.tools.RecTools;
import com.curator.tools.SpotifyTools;
import com.curator.views.SurveyButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SurveyController implements Initializable {

    @FXML
    private Button okButton;

    @FXML
    private FlowPane genreFlowPane;

    @FXML
    private FlowPane artistFlowPane;

    @FXML
    private FlowPane trackFlowPane;

    private Stage stage;

    @FXML
    private Label genreQuestionLabel;

    @FXML
    private Label artistQuestionLabel;

    @FXML
    private Label trackQuestionLabel;

    @FXML
    private Label welcomeLabel;


    private HashSet<String> uniqueArtistsID = new HashSet<String>();
    private HashSet<String> uniqueTracksID = new HashSet<String>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText("Hi, " + DBTools.getUserId() + "!");

        okButton.setVisible(false);

        populateGenre();

        okButton.setOnMousePressed(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Storing your picks");
                storePicks();

                //Run recommender
                System.out.println("Curating recommendation...");
//                RecTools.runAlbumRecommender();
//                RecTools.runArtistRecommender();
//                RecTools.runSongRecommender();
                System.out.println("Fetching data...");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
                try {
                    stage.setScene(new Scene(loader.load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stage.setHeight(600);
                stage.setMinWidth(1300);
                stage.setTitle("Music Curator");
                stage.show();
            }
        });
    }

    public void storePicks() {
        for (Node node : artistFlowPane.getChildren()) {
            SurveyButton btn = (SurveyButton) node;
            if (btn.isSelected()) {
                DBTools.storeUserPreferenceArtist(btn.getItemId(), true);
            }
        }

        for (Node node : trackFlowPane.getChildren()) {
            SurveyButton btn = (SurveyButton) node;
            if (btn.isSelected()) {
                DBTools.storeUserPreferenceTracks(btn.getItemId(), true);

                //store track's album
                DBTools.storeUserPreferenceAlbum(
                        SpotifyTools.getTrack(btn.getItemId()).getAlbum().getAlbumID(), true);
            }
        }
    }

    public void populateGenre() {
        for (Genre genre : Genre.values()) {
            genreFlowPane.getChildren().add(new SurveyButton(genre.id,
                    genreFlowPane, this, 0, ""));
        }
    }

    public void populateArtist(ArrayList<Artist> artists) {
        for (Artist artist : artists) {
            if (!uniqueArtistsID.contains(artist.getArtistID())) {
                uniqueArtistsID.add(artist.getArtistID());
                artistFlowPane.getChildren().add(
                        new SurveyButton(artist.getName(), artistFlowPane, this, 1, artist.getArtistID()));
            }
        }
    }

    public void populateTrack(ArrayList<Track> tracks) {
        for (Track track : tracks) {
            if (!uniqueTracksID.contains(track.getTrackID())) {
                uniqueTracksID.add(track.getTrackID());
                trackFlowPane.getChildren().add(
                        new SurveyButton(track.getTrackName(),
                                trackFlowPane, this, 2, track.getTrackID()));
            }
        }
    }


    public void showArtistQuestionLabel(){
        artistQuestionLabel.setOpacity(1);

    }

    public void showTrackQuestionLabel(){
        trackQuestionLabel.setOpacity(1);
    }

    public void showOkButton(){
        okButton.setVisible(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

