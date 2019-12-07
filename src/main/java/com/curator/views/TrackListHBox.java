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

/**
 * The TrackList representation of a Track
 */
class TrackListHBox {
    private HBox hbox;

    /**
     * Constructs the TrackList object of the track
     * @param track the track to of this trackList
     * @param mainController the main controller of the app
     * @param navbarController the navbar controller of the app
     * @param playerController the player controller of the app
     */
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

        //when mouse enter the hbox, show action icons
        hbox.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            buttonsPane.setOpacity(1);
            buttonsPane.setDisable(false);
        });

        //when mouse exit the hbox
        hbox.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            buttonsPane.setOpacity(0);
            buttonsPane.setDisable(true);
        });

        //if double click on track, play music
        hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    playerController.setCurrentTrack(SpotifyTools.getTrack(track.getTrackID()));
                }
            }
        });

        //if click on play icon, play music
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> playerController.setCurrentTrack(SpotifyTools.getTrack(track.getTrackID())));

        //if click on heart icon,
        heartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> DBTools.storeUserPreferenceTracks(track.getTrackID(), true));


        playlistButton.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                ContextMenu contextMenu = new ContextMenu();
                for (String playlist_id : DBTools.getAllPlaylistIDs()) {
                    MenuItem menuItem = new MenuItem(DBTools.getPlaylistName(playlist_id));
                    menuItem.setId(playlist_id);
                    menuItem.setOnAction(event2 -> DBTools.storeTrackToPlaylist(track.getTrackID(), playlist_id));
                    contextMenu.getItems().add(menuItem);
                }
                contextMenu.show(playlistButton, event.getScreenX(), event.getScreenY());
            }
        });
    }

    /**
     * Return the HBox object of the album
     * @return HBox object of this TrackList
     */
    public HBox asHBox(){
        return hbox;
    }
}
