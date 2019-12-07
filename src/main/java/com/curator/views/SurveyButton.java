package com.curator.views;

import com.curator.controllers.SurveyController;
import com.curator.models.Artist;
import com.curator.models.Genre;
import com.curator.models.Track;
import com.curator.tools.SpotifyTools;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

/**
 * The class representation of the survey button in album_pane.fxml
 */
public class SurveyButton extends Button {
    private boolean selected = false;
    private final String id;
    private int type;

    /**
     * Creates a SurveyButton object
     * @param text the text to be displayed on the button
     * @param parentContainer the parent container object of the button
     * @param surveyController the survey controller of the app
     * @param type sets the behavior of the button
     * @param id the id of the item represented by this button
     */
    public SurveyButton(String text, FlowPane parentContainer, SurveyController surveyController, int type, String id) {
        this.id = id;

        if (text.length() > 60){
            setText(text.substring(0,60) + "...");
        } else {
            setText(text);
        }
        setStyle("-fx-background-color: none");
        setAlignment(Pos.CENTER);

        setOnMouseEntered(event -> {
            if (!selected) {
                setStyle("-fx-background-color: #B0B0B0");
            }
        });

        setOnMouseExited(event -> {
            if (!selected) {
                setStyle("-fx-background-color: none");
            }
        });

        setOnMousePressed(event -> {
            if (selected) {
                setStyle("-fx-background-color: none");
            } else {
                setStyle("-fx-background-color: #B0B0B0");

                //Genre type button
                if (type == 0) {
                    surveyController.showArtistQuestionLabel();

                    surveyController.populateArtist(SpotifyTools.getArtistByGenre(
                            Genre.getGenreFromId(text), 15));

                //Artist type button
                } else if (type == 1) {
                    surveyController.showTrackQuestionLabel();

                    ArrayList<com.curator.models.Artist> artists = SpotifyTools.getRelatedArtists(id);
                    surveyController.populateArtist(artists);

                    ArrayList<Track> tracks = new ArrayList<>();
                    for (int i1 = 0; i1 < artists.size(); i1++) {
                        Artist artist = artists.get(i1);
                        ArrayList<Track> topTracks = SpotifyTools.getArtistTopTracks(artist.getArtistID());
                        for (int i2 = 0; i2 < topTracks.size(); i2++) {
                            //how many top tracks to show for each related artists
                            if (i2 == 3) {
                                break;
                            }
                            tracks.add(topTracks.get(i2));
                        }
                        //how many related artists to show
                        if (i1 == 5) {
                            break;
                        }
                    }
                    surveyController.populateTrack(tracks);
                //track type button
                } else if (type == 2) {
                    surveyController.showOkButton();
                }
            }
            selected = !selected;
        });
    }

    /**
     * Returns the id of the item represented by this button
     * @return the id of the item represented by this button
     */
    public String getItemId() {
        return id;
    }

    /**
     * Returns the type of the item represented by this button
     * @return the type of the item represented by this button
     */
    public int getItemType() {
        return type;
    }

    /**
     * Returns true if the button is selected, returns false otherwise
     * @return true if the button is selected, returns false otherwise
     */
    public boolean isSelected() {
        return selected;
    }
}
