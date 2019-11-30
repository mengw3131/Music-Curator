package com.curator.views;

import com.curator.controllers.MainController;
import com.curator.controllers.NavbarController;
import com.curator.controllers.PlayerController;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TrackListHBox {
    private HBox hbox;

    public TrackListHBox(Track track, MainController mainController, NavbarController navbarController,
                         PlayerController playerController) {
        try {
            hbox = new FXMLLoader(getClass().getResource("/views/album_tracks_row.fxml")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((Label) ((AnchorPane) hbox.getChildren().get(0)).getChildren().get(0)).setText(track.getTrackName()); //set track name
        AnchorPane buttonsPane = (AnchorPane) (hbox.getChildren().get(2));
        ImageView playButton = (ImageView) buttonsPane.getChildren().get(0);
        ImageView heartButton = (ImageView) buttonsPane.getChildren().get(1);
        ImageView playlistButton = (ImageView) buttonsPane.getChildren().get(2);

        //initially hide and disable the action buttons (play, heart, add to playlist)
        buttonsPane.setOpacity(0);
        buttonsPane.setDisable(true);

        //alternate the background color of the track row using flag
        // white + light grey
//        if (flag) {
//            hbox.setStyle("-fx-background-color: #e8e8e8");
//        }
//        flag = !flag;


        //when mouse enter the hbox, show action icons
        hbox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonsPane.setOpacity(1);
                buttonsPane.setDisable(false);
            }
        });

        //when mouse exit the hbox
        hbox.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonsPane.setOpacity(0);
                buttonsPane.setDisable(true);
            }
        });

        //if double click on track, play music
        hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        playerController.setCurrentTrack(SpotifyTools.getTrack(track.getTrackID()));
                    }
                }
            }
        });

        //if click on play icon, play music
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playerController.setCurrentTrack(SpotifyTools.getTrack(track.getTrackID()));
            }
        });

        //if click on heart icon,
        heartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                //TODO: IMPLEMENT
                System.out.println("heart clicked");

            }
        });


        playlistButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown()) {

                    ContextMenu contextMenu = new ContextMenu();
                    for (String playlist_id : DBTools.getAllPlaylistIDs()) {
                        MenuItem menuItem = new MenuItem(DBTools.getPlaylistName(playlist_id));
                        menuItem.setId(playlist_id);
                        menuItem.setOnAction(event2 -> {
                            DBTools.storeTrackToPlaylist(track.getTrackID(), playlist_id);
                        });
                        contextMenu.getItems().add(menuItem);
                    }
                    contextMenu.show(playlistButton, event.getScreenX(), event.getScreenY());
                }
            }
        });
    }

    public HBox asHBox(){
        return hbox;
    }
}
