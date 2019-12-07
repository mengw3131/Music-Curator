package com.curator.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import com.curator.models.Artist;
import com.curator.models.Genre;
import com.curator.models.Track;
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

/**
 * Controllers to survey.fxml
 */
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

	private final HashSet<String> uniqueArtistsID = new HashSet<>();
	private final HashSet<String> uniqueTracksID = new HashSet<>();

	/**
	 * Initialize controller
	 * @param location
	 * @param resources
	 */
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

				// Run recommender
				System.out.println("Curating recommendation...");
				RecTools.runCombinedRecommender();
				System.out.println("Fetching data...");

				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/views/main.fxml"));
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

	/**
	 * Store user track and artist picks in DB
	 */
	private void storePicks() {
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

				// store track's album
				DBTools.storeUserPreferenceAlbum(SpotifyTools
						.getTrack(btn.getItemId()).getAlbum().getAlbumID(),
						true);
			}
		}
	}

	/**
	 *  Populate containers with predefined genres
	 */
	private void populateGenre() {
		for (Genre genre : Genre.values()) {
			genreFlowPane.getChildren().add(
					new SurveyButton(genre.id, genreFlowPane, this, 0, ""));
		}
	}

	/**
	 * Populate containers with suggested artists in the survey page
	 * @param artists suggested artists to be displayed
	 */
	public void populateArtist(ArrayList<Artist> artists) {
		for (Artist artist : artists) {
			if (!uniqueArtistsID.contains(artist.getArtistID())) {
				uniqueArtistsID.add(artist.getArtistID());
				artistFlowPane.getChildren()
						.add(new SurveyButton(artist.getName(), artistFlowPane,
								this, 1, artist.getArtistID()));
			}
		}
	}

	/**
	 * Populate containers with suggested tracks in the survey page
	 * @param tracks suggested tracks to be displayed
	 */
	public void populateTrack(ArrayList<Track> tracks) {
		for (Track track : tracks) {
			if (!uniqueTracksID.contains(track.getTrackID())) {
				uniqueTracksID.add(track.getTrackID());
				trackFlowPane.getChildren()
						.add(new SurveyButton(track.getTrackName(),
								trackFlowPane, this, 2, track.getTrackID()));
			}
		}
	}

	/**
	 * Set track questions to be visible
	 */
	public void showArtistQuestionLabel() {
		artistQuestionLabel.setOpacity(1);
	}

	/**
	 * Set track questions to be visible
	 */
	public void showTrackQuestionLabel() {
		trackQuestionLabel.setOpacity(1);
	}

	/**
     * Set OK button to be visible
	 */
	public void showOkButton() {
		okButton.setVisible(true);
	}

	/**
	 * Sets the stage of the app
	 * @param stage stage to be set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
