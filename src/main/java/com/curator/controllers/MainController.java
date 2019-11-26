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
 * Controller to main.fxml
 */
public class MainController implements Initializable {
    public enum PAGE_INDEX {
        HOME(0), DISCOVER(1), MYMUSIC(2), FAVORITES(3),
        MADEFORYOU(4), PLAYLISTS(5), PROFILE(6);

        int index;

        PAGE_INDEX(int i) {
            this.index = i;
        }
    }

    public static int currentPageIndex = PAGE_INDEX.HOME.index;

    private PlayerController playerController;
    private NavbarController navbarController;
    private HomeController homeController;
    private DiscoverController discoverController;
    private PlaylistController playlistController;

    private BorderPane homePane;
    private BorderPane discoverPane;
    private TableView favoritesTable;
    private AnchorPane myMusicPane;
    private AnchorPane madeForYouPane;
    private BorderPane playlistPane;
    private AnchorPane profilePane;

    //set reuseable loader to improve performance
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
    Button favoritesButton;

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
     *
     * @param event
     */
    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws IOException {
        //update button UI
        unselectAllButtons();
        homeButton.setStyle("-fx-background-color: lightgrey;");

        //update index first
        currentPageIndex = PAGE_INDEX.HOME.index;
        navbarController.updateIndex();

        //if home page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {
            if (homePane == null) {
                loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));

                homePane = loader.load();
                ScrollPane scrollPane = (ScrollPane) homePane.getChildren().get(0);

                //set BorderPane to follow mainpane's dimension (for resizing)
                homePane.prefHeightProperty().bind(mainPane.heightProperty());
                homePane.prefWidthProperty().bind(mainPane.widthProperty());

                //hide horizontal and vertical scrollbar
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                scrollPane.setMinWidth(800);
                scrollPane.setMaxWidth(1000);

                //pass parent controller to child
                homeController = loader.getController(); //get controller of home.fxml
                homeController.setMainController(this);
                homeController.setPlayerController(playerController);
                homeController.setNavbarController(navbarController);
            }

            //add homePane
            navbarController.addPage(homePane);


        }
    }

    /**
     * Triggered when discover button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleDiscoverButtonAction(ActionEvent event) {
        //change button visual effects
        unselectAllButtons();
        discoverButton.setStyle("-fx-background-color: lightgrey;");

        //update index index first
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

                    //hide horizontal and vertical scrollbar
                    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    scrollPane.setMinWidth(800);
                    scrollPane.setMaxWidth(1000);

                    //center the vbox and its children (incl. search bar
                    discoverVBox.prefWidthProperty().bind(scrollPane.widthProperty());

                    discoverController = loader.getController(); //get controller of discover.fxml
                    discoverController.setMainController(this);
                    discoverController.setPlayerController(playerController);
                    discoverController.setNavbarController(navbarController);
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
     *
     * @param event
     */
    @FXML
    private void handleMyMusicButtonAction(ActionEvent event) {
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
            //TODO: IMPLEMENT HERE

            myMusicPane = new AnchorPane();
            myMusicPane.getChildren().add(new Label("My Music page"));
            myMusicPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));

            //add myMusicPane
            navbarController.addPage(myMusicPane);
        }
    }

    /**
     * Triggered when Favorites button in the left bar is clicked
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleFavoritesButtonAction(ActionEvent event) throws IOException {
        //change button visual effects
        unselectAllButtons();
        favoritesButton.setStyle("-fx-background-color: lightgrey;");

        //set current page index
        currentPageIndex = PAGE_INDEX.FAVORITES.index;
        navbarController.updateIndex();

        //if music page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {
            //TODO: IMPLEMENT HERE


            if (favoritesTable == null) {
                favoritesTable = loader.load(getClass().getResource("/views/favorites.fxml"));

                //set tableview to fill the whole container upon resize
                favoritesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                //set tableview to follow container's width and height (for resizing)
                favoritesTable.prefWidthProperty().bind(mainPane.widthProperty());
                favoritesTable.prefHeightProperty().bind(mainPane.heightProperty());
            }

            //add myMusicPane
            navbarController.addPage(favoritesTable);
        }
    }

    /**
     * Triggered when MadeForYou button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleMadeForYouButtonAction(ActionEvent event) {
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
     *
     * @param event
     */
    @FXML
    private void handlePlaylistsButtonAction(ActionEvent event) {
        //update button UI
        unselectAllButtons();
        playlistsButton.setStyle("-fx-background-color: lightgrey;");

        //update index first
        currentPageIndex = PAGE_INDEX.PLAYLISTS.index;
        navbarController.updateIndex();

        //if playlists page already exists, don't create new, just switch to the last page
        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
            navbarController.switchPage();
        } else {
            if (playlistPane == null) {

                loader = new FXMLLoader(getClass().getResource("/views/playlists.fxml"));

                playlistController = loader.getController(); //get controller of home.fxml

                try {
                    playlistPane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ScrollPane scrollPane = (ScrollPane) playlistPane.getChildren().get(0);

                //set BorderPane to follow mainpane's dimension (for resizing)
                playlistPane.prefHeightProperty().bind(mainPane.heightProperty());
                playlistPane.prefWidthProperty().bind(mainPane.widthProperty());

                //hide horizontal and vertical scrollbar
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

                scrollPane.setMinWidth(800);
                scrollPane.setMaxWidth(1000);
            }

            //add homePane
            navbarController.addPage(playlistPane);
        }


//        //change button visual effects
//        unselectAllButtons();
//        playlistsButton.setStyle("-fx-background-color: lightgrey;");
//
//        //set current page index
//        currentPageIndex = PAGE_INDEX.PLAYLISTS.index;
//        navbarController.updateIndex();
//
//        //if music page already exists, don't create new, just switch to the last page
//        if (navbarController.getPagesCountInSection(currentPageIndex) != 0) {
//            navbarController.switchPage();
//        } else {
//            //TODO: IMPLEMENT HERE
//
//            playlistPane = new AnchorPane();
//            playlistPane.getChildren().add(new Label("Playlist page"));
//            playlistPane.setBackground(new Background(new BackgroundFill(Color.web("#d4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
//
//            //add myMusicPane
//            navbarController.addPage(playlistPane);
//        }
    }

    /**
     * Triggered when Profile button in the left bar is clicked
     *
     * @param event
     */
    @FXML
    private void handleProfileButtonAction(ActionEvent event) {

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
        favoritesButton.setStyle("-fx-background-color: none;");
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
        homeButton.fire();
    }
}
