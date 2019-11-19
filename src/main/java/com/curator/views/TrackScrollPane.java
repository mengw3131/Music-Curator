package com.curator.views;

import com.curator.controllers.AlbumPageController;
import com.curator.models.Track;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class TrackScrollPane extends ScrollPane {
    private int size;
    ArrayList<Track> tracks;
    ScrollPane pane;

    public TrackScrollPane(ArrayList<Track> tracks) {
        this.tracks = tracks;
        size = tracks.size();
    }

    public void create() {
        try {
            pane = new FXMLLoader(getClass().getResource("/views/tracks_hbox.fxml")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;

    }

    public void loadPane() {
        try {
            pane = new FXMLLoader(getClass().getResource("/views/tracks_hbox.fxml")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

