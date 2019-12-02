package com.curator.views;

import com.curator.controllers.MainController;
import com.curator.controllers.NavbarController;
import com.curator.controllers.PlayerController;
import com.curator.models.Track;
import com.curator.models.TrackSimple;
import com.curator.tools.SpotifyTools;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TrackListVBox {
    private VBox vbox = new VBox();

    public TrackListVBox(ArrayList tracks, MainController mainController, NavbarController navbarController,
                         PlayerController playerController) {
        if (tracks.size() == 0){
            System.out.println("No tracks supplied when creating TrackListVBox");
        } else {
            if (tracks.get(0) instanceof Track) {
                for (int i = 0; i < tracks.size(); i++) {
                    HBox hbox = new TrackListHBox((Track) tracks.get(i),
                            mainController, navbarController, playerController).asHBox();
                    if (i % 2 == 0) {
                        hbox.setStyle("-fx-background-color: #e8e8e8");
                    }
                    vbox.getChildren().add(hbox);
                }
            } else if (tracks.get(0) instanceof TrackSimple) {
                for (int i = 0; i < tracks.size(); i++) {
                    HBox hbox = new TrackListHBox(SpotifyTools.getTrack(((TrackSimple) tracks.get(i)).getTrackID()),
                            mainController, navbarController, playerController).asHBox();
                    if (i % 2 == 0) {
                        hbox.setStyle("-fx-background-color: #e8e8e8");
                    }
                    vbox.getChildren().add(hbox);
                }
            }
        }
    }

    public VBox asVBox(){
        return vbox;
    }

}
