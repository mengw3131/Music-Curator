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

public class SurveyButton extends Button {

    private boolean selected = false;
    private FlowPane parent;
    private SurveyController surveyController;
    private String id;
    private int type;

    public String getItemId() {
        return id;
    }

    public int getItemType() {
        return type;
    }

    public boolean isSelected() {
        return selected;
    }

    public SurveyButton(String text, FlowPane parentContainer, SurveyController surveyController,
                        int type, String id) {
        this.surveyController = surveyController;
        this.parent = parentContainer;
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
}
