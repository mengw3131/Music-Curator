package com.curator.controllers;

import com.curator.tools.DBTools;
import com.curator.tools.SpotifyTools;
import com.curator.tools.YoutubeTools;
import com.curator.models.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;


/**
 * Controller to home.fxml
 */
public class HomeController implements Initializable {
    private MainController mainController;
    private PlayerController playerController;
    private NavbarController navbarController;

    @FXML
    private BorderPane topBorderPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox topRecommendationVBox;


    /**
     * //TODO: REFACTOR INTO SEPARATE CLASS
     * Creates a horizontal box of track panes. Default of 8 tracks.
     *
     * @param tracks
     * @return HBox of track panes in tracks
     */
    public ScrollPane createRecommendationBox(ArrayList<Track> tracks) {

        /*
          Nodes Hierarchy:

          ScrollPane (isPannable true (can drag horizontally by mouse), hbarPolicy NEVER (hide scrollbar))
             HBox
                Pane (one for each track, default 8 panes)
                  ImageView (for track image)
                  Label (track name)
                  Label (track artist)
                  ImageView (for in-pane play button)
                  ImageView (for in-pane heart button)
                  ImageView (for in-pane addToPlaylist button)
        */
        ScrollPane pane = null;

        try {
            pane = new FXMLLoader(getClass().getResource("/views/tracks_hbox.fxml")).load();
            pane.prefWidthProperty().bind(mainScrollPane.widthProperty());

            HBox box = (HBox) pane.getContent();

            //loop on each music pane in HBox
            for (int i = 0; i < ((HBox) pane.getContent()).getChildren().size(); i++) {
                Track track = tracks.get(i);
                Pane subPane = (Pane) box.getChildren().get(i);
                ImageView trackImage = (ImageView) subPane.getChildren().get(0);
                Label trackName = (Label) subPane.getChildren().get(1);
                Label trackArtist = (Label) subPane.getChildren().get(2);
                ImageView inPanePlayButton = (ImageView) subPane.getChildren().get(3);
                ImageView inPaneHeartButton = (ImageView) subPane.getChildren().get(4);
                ImageView inPaneAddToPlaylistButton = (ImageView) subPane.getChildren().get(5);
                ImageView dislikeButton = (ImageView) subPane.getChildren().get(6);

                trackImage.setImage(track.getImage());
                trackName.setText(track.getTrackName());
                trackArtist.setText(track.getArtistsNames());

                //when mouse enter the pane
                subPane.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackImage.setOpacity(0.2);
                        inPanePlayButton.setOpacity(1);
                        inPaneHeartButton.setOpacity(1);
                        inPaneAddToPlaylistButton.setOpacity(1);
                        dislikeButton.setOpacity(1);

                        inPanePlayButton.setDisable(false);
                        inPaneHeartButton.setDisable(false);
                        inPaneAddToPlaylistButton.setDisable(false);
                        dislikeButton.setDisable(false);
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
                        dislikeButton.setOpacity(0);

                        inPanePlayButton.setDisable(true);
                        inPaneHeartButton.setDisable(true);
                        inPaneAddToPlaylistButton.setDisable(true);
                        dislikeButton.setDisable(true);
                    }
                });

                //when play button inside pane is clicked
                inPanePlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        playerController.setCurrentTrack(track);
                        event.consume();
                    }
                });



                inPaneAddToPlaylistButton.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.isPrimaryButtonDown()){

                            ContextMenu contextMenu = new ContextMenu();
                            for (String playlist_id:DBTools.getAllPlaylistIDs()) {
                                MenuItem menuItem = new MenuItem(DBTools.getPlaylistName(playlist_id));
                                menuItem.setId(playlist_id);
                                menuItem.setOnAction(event2 -> {
                                    DBTools.storeTrackToPlaylist(track.getTrackID(), playlist_id);
                                });
                                contextMenu.getItems().add(menuItem);
                            }
                            contextMenu.show(inPaneAddToPlaylistButton, event.getScreenX(), event.getScreenY());
                        }
                    }
                });

                //when addToPlaylist button inside pane is clicked
                inPaneHeartButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        DBTools.storeUserPreferenceTracks(track.getTrackID(), true);
                    }
                });

                dislikeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        DBTools.storeUserPreferenceTracks(track.getTrackID(), false);


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
                        if (event.getButton() == MouseButton.PRIMARY) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/album_page.fxml"));
                            try {
                                BorderPane borderPane = loader.load();
                                AlbumPageController albumPageController = loader.getController();
                                albumPageController.setPlayerController(playerController);
                                albumPageController.setMainController(mainController);
                                albumPageController.setAlbum(track.getAlbum());

                                navbarController.addPage(borderPane);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


                trackArtist.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackArtist.setStyle("-fx-underline: true");
                    }
                });

                trackArtist.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        trackArtist.setStyle("-fx-underline: false");

                    }
                });

                trackArtist.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/artist_page.fxml"));
                        try {
                            BorderPane borderPane = loader.load();
                            ArtistPageController artistPageController = loader.getController();
                            artistPageController.setPlayerController(playerController);
                            artistPageController.setMainController(mainController);
                            artistPageController.setNavbarController(navbarController);
                            artistPageController.setArtist(track.getArtists().get(0)); //only get first artist

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

    //create dummy arrays of track, no API calls, use this for testing
    private ArrayList<Track> createDummy() {
        System.out.println("creating dummy tracks");
        ArrayList<Track> tracks = new ArrayList<>();
        if (!new File("src/main/resources/music/g-jsW61e_-w.mp3").exists()) {
            YoutubeTools.getMediaFileFromYoutubeId("g-jsW61e_-w");
        }
        for (int i = 0; i < 8; i++) {
            Track track = new Track();

            track.setMedia(new Media(new File("src/main/resources/music/g-jsW61e_-w.mp3").toURI().toString()));
            track.setImage(new Image("/icons/musical-note.png"));
            track.setArtistsNames("[DUMMY] Bill Evans");
            track.setTrackName("[DUMMY]My Foolish Heart");
            tracks.add(track);
        }
        return tracks;
    }


    /**
     * Initialize com.curator.controllers.HomeController
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reload();


//        //when home page is visible again, reload to get new data from database
//        topBorderPane.visibleProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                if (newValue){
//                    reload();
//                }
//            }
//        });
    }

    public void reload(){
        topRecommendationVBox.getChildren().clear();

        //this method currently contains placeholder values
        //this is supposed to be the receiver of the recommendation model


        Label label = new Label("Top Recommendation");
        label.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label); //INDEX 0


        ArrayList<Track> tracks = SpotifyTools.searchTracks("Art Tatum", 8);
//        ArrayList<Track> tracks =  createDummy();    //use dummy data

        System.out.println("Loading tracks...");
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks)); //INDEX 1

//        tracks = SpotifyTools.searchTracks("Art Tatum", 8);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks)); //INDEX 2

        Label label2 = new Label("Hot This Week");
        label2.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label2.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label2);  //INDEX 3

//        tracks = SpotifyTools.searchTracks("Oscar Peterson", 8);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks));//INDEX 4

        Label label3 = new Label("Mood");
        label3.setFont(Font.font("Calibri", FontWeight.EXTRA_LIGHT, 35));
        label3.setAlignment(Pos.TOP_LEFT);
        topRecommendationVBox.getChildren().add(label3); //INDEX 5

//        tracks = SpotifyTools.searchTracks("Calm", 8);
        topRecommendationVBox.getChildren().add(createRecommendationBox(tracks)); //INDEX 6

    }

    /**
     * Injects mainController
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Injects playerController
     *
     * @param playerController
     */
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    /**
     * Injects navbarController
     *
     * @param navbarController
     */
    public void setNavbarController(NavbarController navbarController) {
        this.navbarController = navbarController;
    }


    private void getContextMenu(){

    }

}
