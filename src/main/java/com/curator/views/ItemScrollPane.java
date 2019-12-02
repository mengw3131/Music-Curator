package com.curator.views;

import com.curator.controllers.MainController;
import com.curator.controllers.NavbarController;
import com.curator.controllers.PlayerController;
import com.curator.models.Album;
import com.curator.models.AlbumSimple;
import com.curator.models.Artist;
import com.curator.models.Track;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class ItemScrollPane extends ScrollPane {
    private HBox hBox = new HBox();

    public <T> ItemScrollPane(ArrayList<T> items, MainController mainController, NavbarController navbarController,
                              PlayerController playerController, int type) {

        if (items.get(0) instanceof Track) {
            for (int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                TrackPane newTrackPane =
                        new TrackPane((Track) item, mainController, navbarController,
                        playerController, this, i);
                hBox.getChildren().add(newTrackPane.asPane());
            }
        } else if (items.get(0) instanceof AlbumSimple) {
            for (int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                hBox.getChildren().add(new AlbumPane((AlbumSimple) item, mainController,
                        navbarController, playerController, this, i, type).asPane());
            }
        } else if (items.get(0) instanceof Album) {
            for (int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                hBox.getChildren().add(new AlbumPane((Album) item, mainController,
                        navbarController, playerController, this, i, type).asPane());
            }
        }
        else if (items.get(0) instanceof Artist) {
            for (int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                hBox.getChildren().add(new ArtistPane((Artist) item, mainController,
                        navbarController, playerController, this, i, type).asPane());
            }
        }

        setPannable(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setStyle("-fx-background-color: transparent");
        setMinHeight(200);
        setPrefHeight(200);
        setMaxHeight(200);
        setContent(hBox);
    }
}
