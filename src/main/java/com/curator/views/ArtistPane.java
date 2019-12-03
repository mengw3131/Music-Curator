package com.curator.views;

import com.curator.controllers.ArtistPageController;
import com.curator.controllers.MainController;
import com.curator.controllers.NavbarController;
import com.curator.controllers.PlayerController;
import com.curator.models.Artist;
import com.curator.models.Track;
import com.curator.tools.DBTools;
import com.curator.tools.RecTools;
import com.curator.tools.SpotifyTools;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ArtistPane {
    private Pane pane;
    private NavbarController navbarController;
    private MainController mainController;
    private PlayerController playerController;
    private ItemScrollPane parentContainer;
    private int childIndex;
    private int type;

    public ArtistPane(Artist artist, MainController mainController, NavbarController navbarController,
                      PlayerController playerController, ItemScrollPane parentContainer,
                      int childIndex, int type) {
        this.mainController = mainController;
        this.navbarController = navbarController;
        this.playerController = playerController;
        this.parentContainer = parentContainer;
        this.childIndex = childIndex;
        this.type = type;

        try {
            //=================================
            // GET CHILD NODES & ASSIGN CONTENT
            //=================================
            pane = new FXMLLoader(getClass().getResource("/views/artist_pane.fxml")).load();
            ImageView artistImageView              = (ImageView) pane.getChildren().get(0);
            Label artistNameLabel                  = (Label)     pane.getChildren().get(1);
            ImageView playButtonImageView          = (ImageView) pane.getChildren().get(3);
            ImageView heartButtonImageView         = (ImageView) pane.getChildren().get(4);
            ImageView dislikeButtonImageView       = (ImageView) pane.getChildren().get(5);

            if (artist.getImages().size() != 0){
                artistImageView.setImage(artist.getImages().get(0));
            }

            artistNameLabel                        .setText(artist.getName());
            playButtonImageView                    .setImage(Icons.PLAY);
            heartButtonImageView                   .setImage(Icons.HEART);
            dislikeButtonImageView                 .setImage(Icons.DISLIKE);


            //=================================
            // SET EVENTS HANDLER
            //=================================

            //Called when mouse enters the scrollPane
            pane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                artistImageView.setOpacity(0.2);

                setVisible(true, playButtonImageView, heartButtonImageView,
                        dislikeButtonImageView);

                setDisabled(false,playButtonImageView, heartButtonImageView,
                        dislikeButtonImageView );

            });

            //Called when mouse exits the scrollPane
            pane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                artistImageView.setOpacity(1);

                setVisible(false, playButtonImageView, heartButtonImageView,
                        dislikeButtonImageView);

                setDisabled(true, playButtonImageView, heartButtonImageView,
                        dislikeButtonImageView );
            });

            //Called when mouse exits the scrollPane
            playButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Track t = SpotifyTools.getTrack(artist.getTracks().get(0).getTrackID());
                playerController.setCurrentTrack(t);
                event.consume();
            });


            heartButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    event -> DBTools.storeUserPreferenceArtist(artist.getArtistID(), true));

            dislikeButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    event -> {
                        DBTools.storeUserPreferenceArtist(artist.getArtistID(), false);

                        //replace
                        if (type == 0){
//                            Artist a = SpotifyTools.searchArtists("John", 1).get(0);
                            ArtistPane replacement =
                                    new ArtistPane(
                                            RecTools.popArtist(), mainController, navbarController,
                                            playerController, parentContainer, childIndex, type
                                    );
                            replaceAtTheEnd(replacement);
                        }
                        deleteSelf();
            });

            artistNameLabel.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    event -> artistNameLabel.setStyle("-fx-underline: true"));

            artistNameLabel.addEventHandler(MouseEvent.MOUSE_EXITED,
                    event -> artistNameLabel.setStyle("-fx-underline: false"));

            artistNameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/artist_page.fxml"));
                        try {
                            BorderPane borderPane = loader.load();
                            ArtistPageController artistPageController = loader.getController();
                            artistPageController.setControllers(mainController, navbarController, playerController);
                            artistPageController.setArtist(artist);

                            navbarController.addPage(borderPane);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Replace this artistPane with another artistPane at the end
     *
     * @param artistPane new ArtistPane to be set
     */
    private void replaceAtTheEnd(ArtistPane artistPane) {
        HBox hBox = ((HBox) (parentContainer.getContent()));
        hBox.getChildren().add(artistPane.asPane());
    }

    /**
     * Delete this pane
     */
    private void deleteSelf() {
        HBox hBox = ((HBox) (parentContainer.getContent()));
        hBox.getChildren().remove(this.asPane());

        //update the indices of the other trackpanes in the same container
        ObservableList<Node> children = hBox.getChildren();
        for (int i = 0; i < children.size(); i++) {
            children.get(i).setId(i + "");
        }
    }

    public Pane asPane() {
        return pane;
    }

    private void setVisible(boolean isVisible, Node ... nodes){
        for (Node n:nodes) {
            n.setOpacity(isVisible ? 1 : 0);
        }
    }
    private void setDisabled(boolean isDisabled, Node ... nodes){
        for (Node n:nodes) {
            n.setDisable(isDisabled);
        }
    }
}
