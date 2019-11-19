package com.curator.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {
    MainController mainController;
    AnchorPane mainPane;

    private ArrayList<Node> homeArr = new ArrayList<>();
    private ArrayList<Object> discoverArr = new ArrayList<>();
    private ArrayList<Object> myMusicArr = new ArrayList<>();
    private ArrayList<Object> favoritesArr = new ArrayList<>();
    private ArrayList<Object> madeForYouArr = new ArrayList<>();
    private ArrayList<Object> playlistsArr = new ArrayList<>();
    private ArrayList<Object> profileArr = new ArrayList<>();

    //indices
    private int homeI = -1;
    private int discoverI = -1;
    private int myMusicI = -1;
    private int favoritesI = -1;
    private int madeForYouI = -1;
    private int playlistsI = -1;
    private int profileI = -1;

    @FXML
    AnchorPane navBar;

    @FXML
    ImageView backButton;

    @FXML
    ImageView nextButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialized");
        initProperty();
    }

    public void setMain(MainController mainController) {
        this.mainController = mainController;
        this.mainPane = mainController.mainPane;
    }

    private void initProperty() {
        nextButton.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                nextButton.setOpacity(1);
                System.out.println("exited next");
            }
        });
        nextButton.setOnMouseEntered(event -> {
            nextButton.setOpacity(0.3);
            System.out.println("entered next");
        });

        nextButton.setOnMouseClicked(event -> {
            System.out.println("clicked next");
        });

        backButton.setOnMouseExited(event -> {
            backButton.setOpacity(1);
            System.out.println("exited back");
        });
        backButton.setOnMouseEntered(event -> {
            backButton.setOpacity(0.5);
            System.out.println("entered back");
        });

        backButton.setOnMouseClicked(event -> {
            System.out.println("clicked back");
            hideAll();
            homeI--;
            homeArr.get(homeI).setVisible(true);
            homeArr.get(homeI).setDisable(false);
            mainPane.getChildren().setAll(homeArr.get(homeI));

        });

    }

    public void addHomeArr(Node o) {
        hideAll();
        System.out.println("adding home");
        homeArr.add(o);
        homeI++;
        System.out.println("homearr is now length: " + homeArr.size());
    }

    private void hideAll() {
        System.out.println("hiding all");
        for (Node homeObj : homeArr) {
            homeObj.setVisible(false);
            homeObj.setDisable(true);
        }
    }


}
