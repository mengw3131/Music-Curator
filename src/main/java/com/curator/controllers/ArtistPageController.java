package com.curator.controllers;

import com.curator.tools.SpotifyTools;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.curator.models.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to artist_page.fxml. Shows tracks, albums by artist
 */
public class ArtistPageController implements Initializable {
    private Artist artist;
    private ArrayList<Track> tracks;
    private ArrayList<Album> albums;
    private NavbarController navbarController;
    private PlayerController playerController;
    private MainController mainController;

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox mainVBox;

    @FXML
    private ImageView artistImage;

    @FXML
    private Label artistName;


    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab trackTab;

    @FXML
    private AnchorPane trackTabPane;

    @FXML
    private ScrollPane trackTabPaneScroll;

    @FXML
    private VBox trackTabPaneVBox;

    @FXML
    private Tab albumTab;

    @FXML
    private AnchorPane albumTabPane;

    @FXML
    private VBox albumTabPaneVBox;


    /**
     * Initialize controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void createTrackList(ArrayList<Track> tracks){
        boolean flag = false;
        /*
          Nodes hierarchy

          HBox
           AnchorPane
             Label (track name)
           AnchorPane
           AnchorPane
             ImageView (play)
             ImageView (heart)
             ImageView (add to playlist)
         */
        HBox hbox;
        for (Track track: tracks) {
            try {
                hbox = new FXMLLoader(getClass().getResource("/views/tracks_row.fxml")).load();

                ((Label)((AnchorPane)hbox.getChildren().get(0)).getChildren().get(0)).setText(track.getTrackName()); //set track name
                AnchorPane buttonsPane = (AnchorPane)(hbox.getChildren().get(2));
                ImageView playButton = (ImageView) buttonsPane.getChildren().get(0);
                ImageView heartButton = (ImageView) buttonsPane.getChildren().get(1);
                ImageView playlistButton = (ImageView) buttonsPane.getChildren().get(2);

                //initially hide and disable the action buttons (play, heart, add to playlist)
                buttonsPane.setOpacity(0);
                buttonsPane.setDisable(true);

                //alternate the background color of the track row using flag
                // white + light grey
                if (flag) {
                    hbox.setStyle("-fx-background-color: #e8e8e8");
                }
                flag = !flag;


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
                        if (event.getButton().equals(MouseButton.PRIMARY)){
                            if (event.getClickCount() == 2){
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

                //if click on playlist icon,
                playlistButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        //TODO: IMPLEMENT
                        System.out.println("playlist clicked");
                    }
                });

                //add to container
                trackTabPaneVBox.getChildren().add(hbox);

                hbox.prefWidthProperty().bind(mainScrollPane.widthProperty());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //add to container & bind display config
        mainController.mainPane.getChildren().add(topBorderPane);
        topBorderPane.prefHeightProperty().bind(mainController.mainPane.heightProperty());
        topBorderPane.prefWidthProperty().bind(mainController.mainPane.widthProperty());
        mainVBox.prefWidthProperty().bind(mainScrollPane.widthProperty());
        mainTabPane.prefWidthProperty().bind(mainScrollPane.widthProperty());

        //make height follow its parents
        mainVBox.prefHeightProperty().bind(mainScrollPane.heightProperty());
        mainTabPane.prefHeightProperty().bind(mainScrollPane.heightProperty());

        trackTabPaneScroll.prefWidthProperty().bind(mainScrollPane.widthProperty());
        trackTabPaneScroll.prefHeightProperty().bind(mainScrollPane.heightProperty());

        trackTabPane.prefWidthProperty().bind(mainScrollPane.widthProperty());
        trackTabPane.prefHeightProperty().bind(mainScrollPane.heightProperty());

    }

    public void createAlbumsList(ArrayList<Album> albums){
        albumTabPaneVBox.getChildren().add(createAlbumsRecommendationBox( new ArrayList<>(albums.subList(0, 8))));
        albumTabPaneVBox.getChildren().add(createAlbumsRecommendationBox( new ArrayList<>(albums.subList(9, 16))));
    }

    public ScrollPane createAlbumsRecommendationBox(ArrayList<Album> albums) {
        /*
          Nodes Hierarchy:

          ScrollPane (isPannable true (can drag horizontally by mouse), hbarPolicy NEVER (hide scrollbar))
             HBox
                Pane (one for each track, default 8 panes)
                  ImageView (for track image)
                  Label (album name)
                  Label (album artist)
                  ImageView (for in-pane play button)
                  ImageView (for in-pane heart button)
                  ImageView (for in-pane addToPlaylist button)
        */
        ScrollPane pane = null;

        try {
            pane = new FXMLLoader(getClass().getResource("/views/albums_hbox.fxml")).load();
            pane.prefWidthProperty().bind(mainScrollPane.widthProperty());

            HBox box = (HBox) pane.getContent();

            //loop on each music pane in HBox
            for (int i = 0; i < albums.size(); i++) {
                Album album = albums.get(i);

                Pane subPane = (Pane) box.getChildren().get(i);
                ImageView trackImage = (ImageView) subPane.getChildren().get(0);
                Label trackName = (Label) subPane.getChildren().get(1);
                Label trackArtist = (Label) subPane.getChildren().get(2);
                ImageView inPanePlayButton = (ImageView) subPane.getChildren().get(3);
                ImageView inPaneHeartButton = (ImageView) subPane.getChildren().get(4);
                ImageView inPaneAddToPlaylistButton = (ImageView) subPane.getChildren().get(5);

                trackImage.setImage(album.getImages().get(0));
                trackName.setText(album.getName());
                trackArtist.setText(album.getArtistsNames());

                //when mouse enter the pane
                subPane.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackImage.setOpacity(0.2);
                        inPanePlayButton.setOpacity(1);
                        inPaneHeartButton.setOpacity(1);
                        inPaneAddToPlaylistButton.setOpacity(1);

                        inPanePlayButton.setDisable(false);
                        inPaneHeartButton.setDisable(false);
                        inPaneAddToPlaylistButton.setDisable(false);
                    }
                });

                //when mouse exit the pane
                subPane.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackImage.setOpacity(1);
                        inPanePlayButton.setOpacity(0);
                        inPaneHeartButton.setOpacity(0);
                        inPaneAddToPlaylistButton.setOpacity(0);

                        inPanePlayButton.setDisable(true);
                        inPaneHeartButton.setDisable(true);
                        inPaneAddToPlaylistButton.setDisable(true);
                    }
                });

                //when play button inside pane is clicked

//                inPanePlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        Media media = com.curator.tools.YoutubeTools.getMusicFileFromQuery(
//                                com.curator.tools.YoutubeTools.createYoutubeQuery(track.getTrackName(), track.getArtistsNames())
//                        );
//                        track.setMediaFile(media);
//                        playerController.setCurrentTrack(track);
//                        event.consume();
//                    }
//                });

                //when addToPlaylist button inside pane is clicked
                inPaneAddToPlaylistButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //TODO: IMPLEMENT
                        System.out.println("playlist clicked");
                    }
                });

                //when addToPlaylist button inside pane is clicked
                inPaneHeartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //TODO: IMPLEMENT
                        System.out.println("heart clicked");
                    }
                });

                trackName.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackName.setStyle("-fx-underline: true");
                    }
                });

                trackName.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackName.setStyle("-fx-underline: false");

                    }
                });

                trackName.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/album_page.fxml"));
                        try {
                            BorderPane borderPane = loader.load();
                            AlbumPageController albumPageController = loader.getController();
                            albumPageController.setPlayerController(playerController);
                            albumPageController.setMainController(mainController);
                            albumPageController.setAlbum(album);

                            navbarController.addPage(borderPane);

                        } catch (IOException e) {
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
     * Injects mainController
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Injects navbarController
     * @param navbarController
     */
    public void setNavbarController(NavbarController navbarController) {
        this.navbarController = navbarController;
    }

    /**
     * Injects playerController
     * @param playerController
     */
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    /**
     * Set artist of
     * @param artist
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
//        artistImage.setImage(artist.getImages().get(0));
        artistName.setText(artist.getName());

        tracks = SpotifyTools.getArtistTopTracks(artist.getArtistID());

        //limit is max 16
        albums = SpotifyTools.getArtistAlbums(artist.getArtistID(),16);

        for (int i = 0; i < 3; i++) {
            for (TrackSimple t: albums.get(0).getTracks()) {
                tracks.add(SpotifyTools.getTrack(t.getTrackID()));
            }
        }

        createTrackList(tracks);
        createAlbumsList(albums);

    }
}
