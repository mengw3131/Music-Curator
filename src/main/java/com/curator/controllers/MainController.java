package com.curator.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller to main.fxml.
 */
public class MainController implements Initializable {

    //Each page has an index for navigation purposes
    public enum PAGE_INDEX {
        HOME(0), DISCOVER(1), MYMUSIC(2), FAVORITES(3),
        MADEFORYOU(4), PLAYLISTS(5), PROFILE(6);

        int index;
        PAGE_INDEX(int i) {
            this.index = i;
        }
    }

    //MainController controls which page is currently on display
    public static int currentPageIndex = PAGE_INDEX.HOME.index;

    private PlayerController playerController;
    private NavbarController navbarController;
    private HomeController homeController;
    private DiscoverController discoverController;
    private PlaylistController playlistController;
    private MyMusicController myMusicController;

    private BorderPane homePane;
    private BorderPane discoverPane;
    private BorderPane myMusicPane;
    private AnchorPane madeForYouPane;
    private BorderPane playlistPane;
    private AnchorPane profilePane;

    FXMLLoader loader;

    @FXML
    AnchorPane mainPane;

    @FXML
    Button homeButton;

    @FXML
    Button discoverButton;

    @FXML
    Button myMusicButton;

    @FXML
    Button madeForYouButton;

    @FXML
    Button playlistsButton;

    @FXML
    Button profileButton;

    @FXML
    HBox playerContainer;

    @FXML
    AnchorPane navbarContainer;


    /**
     * Triggered when home button in the left bar is clicked
     */
    @FXML
    private void handleHomeButtonAction() throws IOException {
        //update button UI
        unselectAllButtons();
        homeButton.setStyle("-fx-background-color: lightgrey;");

        //update page index
        currentPageIndex = PAGE_INDEX.HOME.index;
        navbarController.updateIndex();

        //if home page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {
            if (homePane == null) {
                loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
                homePane = loader.load();

                //set BorderPane to follow mainpane's dimension (for resizing)
                homePane.prefHeightProperty().bind(mainPane.heightProperty());
                homePane.prefWidthProperty().bind(mainPane.widthProperty());

                //pass parent controller to child
                homeController = loader.getController(); //get controller of home.fxml
                homeController.setControllers(this, navbarController, playerController);
            }

            //add homePane
            navbarController.addPage(homePane);
        }
    }

    /**
     * Triggered when discover button in the left bar is clicked
     */
    @FXML
    private void handleDiscoverButtonAction() {
        //change button visual effects
        unselectAllButtons();
        discoverButton.setStyle("-fx-background-color: lightgrey;");

        //update index page index
        currentPageIndex = PAGE_INDEX.DISCOVER.index;
        navbarController.updateIndex();

        //if discover page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {
            if (discoverPane == null) {
                loader = new FXMLLoader(getClass().getResource("/views/discover.fxml"));
                try {
                    discoverPane = loader.load();
                    ScrollPane scrollPane = (ScrollPane) discoverPane.getChildren().get(0);
                    VBox discoverVBox = (VBox) scrollPane.getContent();

                    //set BorderPane to follow mainpane's dimension (for resizing)
                    discoverPane.prefHeightProperty().bind(mainPane.heightProperty());
                    discoverPane.prefWidthProperty().bind(mainPane.widthProperty());
                    discoverVBox.prefWidthProperty().bind(scrollPane.widthProperty());

                    discoverController = loader.getController();
                    discoverController.setControllers(this, navbarController, playerController);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //add discoverPane
            navbarController.addPage(discoverPane);
        }
    }

    /**
     * Triggered when My Music button in the left bar is clicked
     */
    @FXML
    private void handleMyMusicButtonAction() {
        //change button visual effects
        unselectAllButtons();
        myMusicButton.setStyle("-fx-background-color: lightgrey;");

        //set current page index
        currentPageIndex = PAGE_INDEX.MYMUSIC.index;
        navbarController.updateIndex();

        //if music page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {
            if (myMusicPane == null) {
                loader = new FXMLLoader(getClass().getResource("/views/my_music.fxml"));
                try {
                    myMusicPane = loader.load();
                    ScrollPane scrollPane = (ScrollPane) myMusicPane.getChildren().get(2);
                    VBox myMusicVBox = (VBox) scrollPane.getContent();

                    //set BorderPane to follow mainpane's dimension (for resizing)
                    myMusicPane.prefHeightProperty().bind(mainPane.heightProperty());
                    myMusicPane.prefWidthProperty().bind(mainPane.widthProperty());
                    myMusicVBox.prefWidthProperty().bind(scrollPane.widthProperty());

                    myMusicController = loader.getController();
                    myMusicController.setControllers(this, navbarController, playerController);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //add myMusicPane
            navbarController.addPage(myMusicPane);
        }
    }

    /**
     * Triggered when MadeForYou button in the left bar is clicked
     */
    @FXML
    private void handleMadeForYouButtonAction() {
        //change button visual effects
        unselectAllButtons();
        madeForYouButton.setStyle("-fx-background-color: lightgrey;");

        //set current page index
        currentPageIndex = PAGE_INDEX.MADEFORYOU.index;
        navbarController.updateIndex();

        //if music page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {
            //TODO: IMPLEMENT HERE

            madeForYouPane = new AnchorPane();
            madeForYouPane.getChildren().add(new Label("Made For You page"));
            madeForYouPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));

            //add pane
            navbarController.addPage(madeForYouPane);
        }
    }

    /**
     * Triggered when Playlists button in the left bar is clicked
     */
    @FXML
    private void handlePlaylistsButtonAction() {
        //update button UI
        unselectAllButtons();
        playlistsButton.setStyle("-fx-background-color: lightgrey;");

        //update page index
        currentPageIndex = PAGE_INDEX.PLAYLISTS.index;
        navbarController.updateIndex();

        //if playlists page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {
            if (playlistPane == null) {
                loader = new FXMLLoader(getClass().getResource("/views/playlists.fxml"));
                try {
                    playlistPane = loader.load();

                    //set BorderPane to follow mainpane's dimension (for resizing)
                    playlistPane.prefHeightProperty().bind(mainPane.heightProperty());
                    playlistPane.prefWidthProperty().bind(mainPane.widthProperty());

                    playlistController = loader.getController();
                    playlistController.setControllers(this, navbarController, playerController);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //add homePane
            navbarController.addPage(playlistPane);
        }
    }

    /**
     * Triggered when Profile button in the left bar is clicked
     */
    @FXML
    private void handleProfileButtonAction() {

        //change button visual effects
        unselectAllButtons();
        profileButton.setStyle("-fx-background-color: lightgrey;");

        //set current page index
        currentPageIndex = PAGE_INDEX.PROFILE.index;
        navbarController.updateIndex();

        //if music page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {


            //TODO: IMPLEMENT HERE


            profilePane = new AnchorPane();
            profilePane.getChildren().add(new Label("Profile page"));
            profilePane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));

            //add myMusicPane
            navbarController.addPage(profilePane);
        }
    }

    /**
     * Load the music player (at the bottom of the app)
     */
    private void loadPlayer() {
        try {
            loader = new FXMLLoader(getClass().getResource("/views/player.fxml"));
            HBox player = loader.load();

            //set player width to follow its container
            player.prefWidthProperty().bind(playerContainer.widthProperty());
            playerContainer.getChildren().setAll(player);

            playerController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the music player (at the bottom of the app)
     */
    private void loadNavBar() {
        try {
            loader = new FXMLLoader(getClass().getResource("/views/navbar.fxml"));
            AnchorPane navbar = loader.load();

            //set player width to follow its container
            navbar.prefWidthProperty().bind(navbarContainer.widthProperty());
            navbarContainer.getChildren().setAll(navbar);

            navbarController = loader.getController();
            navbarController.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Unselect all currently pane buttons
     */
    public void unselectAllButtons() {
        homeButton.setStyle("-fx-background-color: none;");
        discoverButton.setStyle("-fx-background-color: none;");
        madeForYouButton.setStyle("-fx-background-color: none;");
        myMusicButton.setStyle("-fx-background-color: none;");
        playlistsButton.setStyle("-fx-background-color: none;");
        profileButton.setStyle("-fx-background-color: none;");
    }

    /**
     * Initialize controller
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Loading panes ... ");
        loadPlayer();
        loadNavBar();
        System.out.println("Loading home content... ");

        //which page to show first
        homeButton.fire();
    }
}
