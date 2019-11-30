package com.curator.views;

import com.curator.controllers.MainController;
import com.curator.controllers.NavbarController;
import com.curator.controllers.PlayerController;
import com.curator.models.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class ItemScrollPane extends ScrollPane {
    private HBox hBox = new HBox();

    public <T> ItemScrollPane(ArrayList<T> items, MainController mainController, NavbarController navbarController,
                              PlayerController playerController) {

        if (items.get(0) instanceof Track) {
            for (T item : items) {
                hBox.getChildren().add(new TrackPane((Track) item, mainController, navbarController,
                        playerController).asPane());
            }
        } else if (items.get(0) instanceof AlbumSimple) {
            for (T item : items) {
                hBox.getChildren().add(new AlbumPane((AlbumSimple) item, mainController,
                        navbarController, playerController).asPane());
            }
        } else if (items.get(0) instanceof Album) {
            for (T item : items) {
                hBox.getChildren().add(new AlbumPane((Album) item, mainController,
                        navbarController, playerController).asPane());
            }
        }
        else if (items.get(0) instanceof Artist) {
            for (T item : items) {
                hBox.getChildren().add(new ArtistPane((Artist) item, mainController,
                        navbarController, playerController).asPane());
            }
        }

        setPannable(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setStyle("-fx-background-color: transparent");
        setMinHeight(200);
        setPrefHeight(200);
        setMaxHeight(200);
        setContent(hBox);
    }
}
