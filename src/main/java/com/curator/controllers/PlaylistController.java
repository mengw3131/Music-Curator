package com.curator.controllers;

import com.curator.models.Playlist;
import com.curator.tools.DBTools;
import com.curator.views.Icons;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to playlists.fxml
 */
public class PlaylistController implements Initializable {
    private MainController mainController;
    private PlayerController playerController;
    private NavbarController navbarController;

    private ArrayList<Playlist> playlists = new ArrayList<>();

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox mainVBox;

    @FXML
    private Button newPlaylistButton;

    /**
     * Initialize controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playlists.addAll(DBTools.getAllPlaylist()); //get user's playlist (based on USER_ID)
        initProperty();
        reload();
    }

    /**
     * Set the UI elements behavior
     */
    private void initProperty(){
        //when playlist page is visible again, loadContent to get new data from database
        topBorderPane.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                reload();
            }
        });

        //when "New" button is clicked, add new playlist
        newPlaylistButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            DBTools.storePlaylist(new Playlist("New Playlist"));
            reload();
        });
    }

    /**
     * Set controllers
     * @param mainController instance of MainController
     * @param navbarController instance of NavbarController
     * @param playerController instance of PlayerController
     */
    public void setControllers(MainController mainController, NavbarController navbarController,
                               PlayerController playerController) {
        this.playerController = playerController;
        this.navbarController = navbarController;
        this.mainController = mainController;
    }

    /**
     * Creates the playlist recommendation container
     * @param playlists the playlists to be displayed
     * @return a Scroll Pane object containing the playlists
     */
    private ScrollPane createRecommendationBox(ArrayList<Playlist> playlists) {

        /*
          Nodes Hierarchy:

          ScrollPane (isPannable true (can drag horizontally by mouse), hbarPolicy NEVER (hide scrollbar))
             HBox
                Pane (one for each playlist, default 8 panes)
                  ImageView (for playlist image)
                  Label (playlist name)
                  Label (playlist artist)
                  ImageView (for in-pane play button)
                  ImageView (for in-pane heart button)
                  ImageView (for in-pane addToPlaylist button)
        */
        ScrollPane pane = null;

        try {
            pane = new FXMLLoader(getClass().getResource("/views/playlist_hbox.fxml")).load();
            pane.prefWidthProperty().bind(mainScrollPane.widthProperty());

            HBox box = (HBox) pane.getContent();

            //loop on each music pane in HBox
            for (int i = 0; i < playlists.size(); i++) {
                final int index = i;
                Playlist playlist = playlists.get(i);

                Pane subPane = (Pane) box.getChildren().get(i);
                ImageView playlistImage = (ImageView) subPane.getChildren().get(0);

                Label playlistName = (Label) ((GridPane) subPane.getChildren().get(1)).getChildren().get(0);
                TextField textField = (TextField) ((GridPane) subPane.getChildren().get(1)).getChildren().get(1);
                textField.setVisible(false);

                ImageView playButton = (ImageView) subPane.getChildren().get(2);
                ImageView renamePlaylist = (ImageView) subPane.getChildren().get(3);
                ImageView deleteButton = (ImageView) subPane.getChildren().get(4);

                Task task = new Task() {
                    @Override
                    protected Object call() {
                        if (playlist.getImage() != null) {
                            playlistImage.setImage(playlist.getImage());
                        } else {
                            playlistImage.setImage(Icons.MUSICAL_NOTE);
                        }
                        return null;
                    }
                };
                new Thread(task).start();

                playlistName.setText(playlist.getName());

                //when mouse enter the pane
                subPane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    playlistImage.setOpacity(0.2);
                    playButton.setOpacity(1);
                    renamePlaylist.setOpacity(1);
                    deleteButton.setOpacity(1);

                    playButton.setDisable(false);
                    renamePlaylist.setDisable(false);
                    deleteButton.setDisable(false);
                });

                //when mouse exit the pane
                subPane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                    playlistImage.setOpacity(1);
                    playButton.setOpacity(0);
                    renamePlaylist.setOpacity(0);
                    deleteButton.setOpacity(0);

                    playButton.setDisable(true);
                    renamePlaylist.setDisable(true);
                    deleteButton.setDisable(true);
                });

                //when play button inside pane is clicked
                playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (playlist.getTracks().size() != 0) {
                        playerController.setCurrentTrack(playlist.getTracks().get(0));
                    } else {
                        System.out.println("No tracks found in playlist " + playlist.getName());
                    }
                    event.consume();
                });

                //when addToPlaylist button inside pane is clicked
                renamePlaylist.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (textField.isVisible()) {
                        textField.setVisible(false);
                        playlistName.setVisible(true);
                        textField.setText(playlistName.getText());

                    } else {
                        textField.setVisible(true);
                        playlistName.setVisible(false);
                        textField.setText(playlistName.getText());
                        textField.requestFocus();
                    }
                });

                textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    //if no longer focus on textfield
                    if (!newValue) {
                        textField.setVisible(false);
                        playlistName.setVisible(true);
                    }
                });
                textField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        playlists.get(index).rename(textField.getText());
                        playlistName.setText(textField.getText());

                        DBTools.renamePlaylist(textField.getText(), playlist.getId());
                        textField.setVisible(false);
                        playlistName.setVisible(true);
                    }
                });

                deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    removeFromPlaylists(playlist.getId());
                    reload();
                });

                playlistName.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> playlistName.setStyle("-fx-underline: true"));

                playlistName.addEventHandler(MouseEvent.MOUSE_EXITED, event -> playlistName.setStyle("-fx-underline: false"));

                playlistName.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent event) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playlist_track_page.fxml"));
                        try {
                            BorderPane borderPane = loader.load();
                            PlaylistTrackController playlistTrackController = loader.getController();
                            playlistTrackController.setControllers(mainController, navbarController, playerController);
                            playlistTrackController.setPlaylist(playlist);

                            navbarController.addPage(borderPane);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    /**
     * Remove playlist from user's playlists
     */
    private void removeFromPlaylists(String playlist_id) {
        DBTools.removePlaylist(playlist_id);
        reload();
    }

    /**
     * Reload playlist page
     */
    private void reload() {
        playlists = DBTools.getAllPlaylist();
        mainVBox.getChildren().remove(2, mainVBox.getChildren().size());

        int remaining = playlists.size();
        int i = 0;
        while (remaining > 0) {
            if (remaining >= 8) {
                mainVBox.getChildren().add(createRecommendationBox(new ArrayList<>(playlists.subList(i, i + 8))));
                remaining -= 8;
                i += 8;
            } else {
                mainVBox.getChildren().add(createRecommendationBox(new ArrayList<>(playlists.subList(i, i + remaining))));
                break;
            }
        }
    }
}
