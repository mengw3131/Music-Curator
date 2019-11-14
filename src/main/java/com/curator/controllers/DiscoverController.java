package com.curator.controllers;

import com.curator.tools.SpotifyTools;
import com.curator.tools.YoutubeTools;
import com.curator.models.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiscoverController implements Initializable {
    MainController mainController;
    PlayerController playerController;

    @FXML
    ScrollPane discoverScrollPane;

    @FXML
    VBox discoverVBox;

    @FXML
    Label discoverTitle;

    @FXML
    TextField searchBar;

    @FXML
    Label tracksLabel;

    @FXML
    ScrollPane tracksScrollPane;

    @FXML
    Label artistsLabel;

    @FXML
    ScrollPane artistsScrollPane;

    @FXML
    Label albumsLabel;

    @FXML
    ScrollPane albumsScrollPane;

    private void toggleChildrenVisibility(boolean isVisible) {
        tracksLabel.setVisible(isVisible);
        artistsLabel.setVisible(isVisible);
        albumsLabel.setVisible(isVisible);
        tracksScrollPane.setVisible(isVisible);
        artistsScrollPane.setVisible(isVisible);
        albumsScrollPane.setVisible(isVisible);
    }

    private void update(String query) {
        if (query.equals("")) {
            toggleChildrenVisibility(false);
        } else {
            toggleChildrenVisibility(true);

            //set matching tracks
            ArrayList<Track> tracks = SpotifyTools.searchTracks(query, 8);
            if (tracks.size() == 0) {
                tracksLabel.setText("No tracks found");
//                discoverVBox.getChildren().set(2, new Label("No tracks found"));
            } else {
                tracksLabel.setText("Tracks");
//                discoverVBox.getChildren().set(2, tracksLabel);
                tracksScrollPane = createTracksRecommendationBox(tracks);
//                discoverVBox.getChildren().set(2, tracksScrollPane);
            }

            //set matching artists
            ArrayList<Artist> artists = SpotifyTools.searchArtists(query, 8);
            if (artists.size() == 0) {
                artistsLabel.setText("No artists found");
                discoverVBox.getChildren().set(4, new Label("No artists found"));
            } else {
                artistsLabel.setText("Artists");

//                discoverVBox.getChildren().get(3).setVisible(true);
                artistsScrollPane = createArtistsRecommendationBox(artists);
//                discoverVBox.getChildren().set(4, artistsScrollPane);
            }

            //set matching albums
            ArrayList<AlbumSimple> albums = SpotifyTools.searchAlbums(query, 8);
            if (albums.size() == 0) {

                albumsLabel.setText("No albums found");
                discoverVBox.getChildren().set(6, new Label("No albums found"));
            } else {
                albumsLabel.setText("Albums");
//                discoverVBox.getChildren().get(5).setVisible(true);
                albumsScrollPane = createAlbumsRecommendationBox(albums);
//                discoverVBox.getChildren().set(6, albumsScrollPane);
//                discoverVBox.getChildren().get(6).setVisible(true);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initializing");

        update("");
        searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    update(searchBar.getText());
                }
            }
        });
    }


    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Creates a horizontal box of track panes. Default of 8 tracks.
     *
     * @param artists
     * @return HBox of track panes in tracks
     */
    public ScrollPane createArtistsRecommendationBox(ArrayList<Artist> artists) {
        /*
          Nodes Hierarchy:

          ScrollPane (isPannable true (can drag horizontally by mouse), hbarPolicy NEVER (hide scrollbar))
             HBox
                Pane (one for each artists, default 8 panes)
                  ImageView (for artist image)
                  Label (artist's name)
                  ImageView (for in-pane play button)
                  ImageView (for in-pane heart button)
                  ImageView (for in-pane addToPlaylist button)
        */
        ScrollPane pane = null;

        try {
            pane = new FXMLLoader(getClass().getResource("/views/artists_hbox.fxml")).load();
            pane.prefWidthProperty().bind(discoverScrollPane.widthProperty());

            HBox box = (HBox) pane.getContent();

            //loop on each music pane in HBox
            for (int i = 0; i < ((HBox) pane.getContent()).getChildren().size(); i++) {
                Artist artist = artists.get(i);
                Pane subPane = (Pane) box.getChildren().get(i);
                ImageView trackImage = (ImageView) subPane.getChildren().get(0);
                Label artistName = (Label) subPane.getChildren().get(1);
                ImageView inPanePlayButton = (ImageView) subPane.getChildren().get(2);
                ImageView inPaneHeartButton = (ImageView) subPane.getChildren().get(3);
                ImageView inPaneAddToPlaylistButton = (ImageView) subPane.getChildren().get(4);

                if (artist.getImages().size() != 0) {
                    trackImage.setImage(artist.getImages().get(0));
                }
                artistName.setText(artist.getName());


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
                //MUST CREATE PLAYLIST
//                inPanePlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        Media media = com.curator.tools.YoutubeTools.getMusicFileFromQuery(
//                                com.curator.tools.YoutubeTools.createYoutubeQuery(track.getName(), track.getArtistsString())
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    public ScrollPane createAlbumsRecommendationBox(ArrayList<AlbumSimple> albumSimpleArr) {
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
            pane.prefWidthProperty().bind(discoverScrollPane.widthProperty());

            HBox box = (HBox) pane.getContent();

            //loop on each music pane in HBox
            for (int i = 0; i < ((HBox) pane.getContent()).getChildren().size(); i++) {
                AlbumSimple album = albumSimpleArr.get(i);

                Pane subPane = (Pane) box.getChildren().get(i);
                ImageView trackImage = (ImageView) subPane.getChildren().get(0);
                Label trackName = (Label) subPane.getChildren().get(1);
                Label trackArtist = (Label) subPane.getChildren().get(2);
                ImageView inPanePlayButton = (ImageView) subPane.getChildren().get(3);
                ImageView inPaneHeartButton = (ImageView) subPane.getChildren().get(4);
                ImageView inPaneAddToPlaylistButton = (ImageView) subPane.getChildren().get(5);

                trackImage.setImage(album.getImages().get(0));
                trackName.setText(album.getName());
                trackArtist.setText(album.getArtistsString());


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
//                                com.curator.tools.YoutubeTools.createYoutubeQuery(track.getName(), track.getArtistsString())
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }


    /**
     * Creates a horizontal box of track panes. Default of 8 tracks.
     *
     * @param tracks
     * @return HBox of track panes in tracks
     */
    public ScrollPane createTracksRecommendationBox(ArrayList<Track> tracks) {
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
            pane.prefWidthProperty().bind(discoverScrollPane.widthProperty());

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

                trackImage.setImage(track.getImage());
                trackName.setText(track.getName());
                trackArtist.setText(track.getArtistsString());


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
                inPanePlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Media media = YoutubeTools.getMusicFileFromQuery(
                                YoutubeTools.createYoutubeQuery(track.getName(), track.getArtistsString())
                        );
                        track.setMedia(media);
                        playerController.setCurrentTrack(track);
                        event.consume();
                    }
                });

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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }
}
