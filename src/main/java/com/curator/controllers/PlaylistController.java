package com.curator.controllers;

import com.curator.models.Playlist;
import com.curator.models.Track;
import com.curator.tools.SpotifyTools;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {
    private MainController mainController;
    private PlayerController playerController;
    private NavbarController navbarController;


    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private VBox topRecommendationVBox;

    public ScrollPane createRecommendationBox(ArrayList<Playlist> playlists){

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
            pane = new FXMLLoader(getClass().getResource("/views/playlists_hbox.fxml")).load();
            pane.prefWidthProperty().bind(mainScrollPane.widthProperty());

            HBox box = (HBox)pane.getContent();

            //loop on each music pane in HBox
//            for (int i = 0; i < ((HBox)pane.getContent()).getChildren().size(); i++) {
            for (int i = 0; i < playlists.size(); i++) {
                Playlist playlist = playlists.get(i);


                Pane subPane = (Pane)box.getChildren().get(i);
                ImageView playlistImage = (ImageView) subPane.getChildren().get(0);
                Label playlistName = (Label)subPane.getChildren().get(1);
                ImageView playButton = (ImageView) subPane.getChildren().get(3);
                ImageView heartButton = (ImageView) subPane.getChildren().get(4);
                ImageView addToPlaylistButton = (ImageView) subPane.getChildren().get(5);
                ImageView dislikeButton = (ImageView) subPane.getChildren().get(6);

                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if (playlist.getImage() != null){
                            playlistImage.setImage(playlist.getImage());
                        } else {
                            playlistImage.setImage(new Image("/icons/musical-note.png"));
                        }
                        return null;
                    }
                };
                new Thread(task).start();

                playlistName.setText(playlist.getName());
                System.out.println("playlist name is " + playlist.getName());

                //when mouse enter the pane
                subPane.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        playlistImage.setOpacity(0.2);
                        playButton.setOpacity(1);
                        heartButton.setOpacity(1);
                        addToPlaylistButton.setOpacity(1);
                        dislikeButton.setOpacity(1);

                        playButton.setDisable(false);
                        heartButton.setDisable(false);
                        addToPlaylistButton.setDisable(false);
                        dislikeButton.setDisable(false);
                    }
                });

                //when mouse exit the pane
                subPane.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        playlistImage.setOpacity(1);
                        playButton.setOpacity(0);
                        heartButton.setOpacity(0);
                        addToPlaylistButton.setOpacity(0);
                        dislikeButton.setOpacity(0);

                        playButton.setDisable(true);
                        heartButton.setDisable(true);
                        addToPlaylistButton.setDisable(true);
                        dislikeButton.setDisable(true);
                    }
                });

                //when play button inside pane is clicked
                playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
//                        playerController.setCurrentTrack(track);
                        event.consume();
                    }
                });

                //when addToPlaylist button inside pane is clicked
                addToPlaylistButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //TODO: IMPLEMENT
                        System.out.println("playlist clicked");
                    }
                });

                //when addToPlaylist button inside pane is clicked
                heartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //TODO: IMPLEMENT
                        System.out.println("heart clicked");
                    }
                });

                dislikeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //TODO: IMPLEMENT
                        System.out.println("dislike clicked");
                    }
                });

                playlistName.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        playlistName.setStyle("-fx-underline: true");
                    }
                });

                playlistName.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        playlistName.setStyle("-fx-underline: false");

                    }
                });

                playlistName.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/album_page.fxml"));
                        try {
                            System.out.println("playlistname clicked");
//                            BorderPane borderPane = loader.load();
//                            AlbumPageController albumPageController = loader.getController();
//                            albumPageController.setPlayerController(playerController);
//                            albumPageController.setMainController(mainController);
//                            albumPageController.setAlbum(playlist.getAlbum());
//
//                            navbarController.addPage(borderPane);

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

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void setNavbarController(NavbarController navbarController) {
        this.navbarController = navbarController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Playlist> playlist = new ArrayList<>();
        ArrayList<Track> tracks =  SpotifyTools.searchTracks("Jazz", 8);
        playlist.add(new Playlist("Playlist 1", tracks));
        playlist.add(new Playlist("Playlist 2", tracks));
        playlist.add(new Playlist("Playlist 3", tracks));
        playlist.add(new Playlist("Playlist 4", tracks));

        topRecommendationVBox.getChildren().add(createRecommendationBox(playlist));
    }
}
