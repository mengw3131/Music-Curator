package com.curator.views;

import com.curator.controllers.MainController;
import com.curator.controllers.NavbarController;
import com.curator.controllers.PlayerController;
import com.curator.models.Album;
import com.curator.models.Artist;
import com.curator.models.Track;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * The parent container to Track , Album, and Artist HBoxes
 */
public class ItemScrollPane extends ScrollPane {

    /**
     * Constructs the ItemScrollPane object of the items in the arrayList
     * @param items the items to be added to the scroll pane, must be instance of Track, Album, or Artist
     * @param mainController the main controller of the app
     * @param navbarController the navbar controller of the app
     * @param playerController the player controller of the app
     * @param type set the behavior of the underlying panes
     * @param <T> the type of the object in items array, must be instance of Track, Album, or Artist
     */
    public <T> ItemScrollPane(ArrayList<T> items, MainController mainController, NavbarController navbarController,
                              PlayerController playerController, int type) {
        HBox hBox = new HBox();
        if (items.get(0) instanceof Track) {
            for (int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                TrackPane newTrackPane =
                        new TrackPane((Track) item, mainController, navbarController,
                                playerController, this, i);
                hBox.getChildren().add(newTrackPane.asPane());
            }
        } else if (items.get(0) instanceof Album) {
            for (int i = 0; i < items.size(); i++) {
                T item = items.get(i);
                hBox.getChildren().add(new AlbumPane((Album) item, mainController,
                        navbarController, playerController, this, i, type).asPane());
            }
        } else if (items.get(0) instanceof Artist) {
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
