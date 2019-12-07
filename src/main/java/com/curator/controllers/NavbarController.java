package com.curator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to navbar.fxml. Handles pane transition.
 */
public class NavbarController implements Initializable {
    private AnchorPane mainPane;  //holds the pane to be set

    private final ArrayList<ArrayList<Node>> mainArr = new ArrayList<>();
    private final ArrayList<Integer> mainArrIndex = new ArrayList<>();
    private int currentPageIndex; //Set in MainController class: 0 Home, 1 Discover, etc..

    @FXML
    private AnchorPane navBar;

    @FXML
    private ImageView backButton;

    @FXML
    private ImageView nextButton;


    /**
     * Initialize controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNavButtonProperties();
        initializeMainArr();
    }


    /**
     * Initialize the page arrays and indexes
     */
    private void initializeMainArr() {

        //for each page (home, discover, etc) in the app, add a new node tracker and indexer
        for (int i = 0; i < MainController.PAGE_INDEX.values().length; i++) {
            mainArr.add(new ArrayList<>());
            mainArrIndex.add(0);
        }

        //fetch current index from mainController
        updateIndex();
    }

    /**
     * Set the default behavior when next/back button is pressed
     */
    private void initNavButtonProperties() {
        nextButton.setOnMouseExited(event -> nextButton.setOpacity(1));
        nextButton.setOnMouseEntered(event -> nextButton.setOpacity(0.3));

        nextButton.setOnMouseClicked(event -> {
            if (countNonNull(mainArr.get(currentPageIndex)) > mainArrIndex.get(currentPageIndex) + 1) {
                hideAll();
                mainArrIndex.set(currentPageIndex, mainArrIndex.get(currentPageIndex) + 1);
                mainArr.get(currentPageIndex).get(mainArrIndex.get(currentPageIndex)).setVisible(true);
                mainArr.get(currentPageIndex).get(mainArrIndex.get(currentPageIndex)).setDisable(false);
                mainPane.getChildren().setAll(mainArr.get(currentPageIndex));
            }
        });

        backButton.setOnMouseExited(event -> backButton.setOpacity(1));
        backButton.setOnMouseEntered(event -> backButton.setOpacity(0.5));

        backButton.setOnMouseClicked(event -> {
            if (0 <= mainArrIndex.get(currentPageIndex) - 1) {
                hideAll();
                mainArrIndex.set(currentPageIndex, mainArrIndex.get(currentPageIndex) - 1);
                mainArr.get(currentPageIndex).get(mainArrIndex.get(currentPageIndex)).setVisible(true);
                mainArr.get(currentPageIndex).get(mainArrIndex.get(currentPageIndex)).setDisable(false);
                mainPane.getChildren().setAll(mainArr.get(currentPageIndex));
            }
        });
    }

    /**
     * Sets reference to mainController and mainPane
     *
     * @param mainController instance of MainController
     */
    public void setMain(MainController mainController) {
        this.mainPane = mainController.mainPane;
    }


    /**
     * Transition to already existing page, e.g. from Home to Discover
     */
    public void switchPage() {
        hideAll();
        mainArr.get(currentPageIndex).get(mainArrIndex.get(currentPageIndex)).setVisible(true);
        mainArr.get(currentPageIndex).get(mainArrIndex.get(currentPageIndex)).setDisable(false);
        mainPane.getChildren().setAll(mainArr.get(currentPageIndex));
    }


    /**
     * Add the page to the corresponding array and show it in mainPane
     *
     * @param node the node to be displayed on mainPane
     */
    public void addPage(Node node) {
        hideAll();

        //if current section has more than one pages, (e.g. Home -> Album page -> Artist page)
        //   then increment the page pointer index
        // else if it's zero, don't increment, the page will be added at location 0 (init pointer value)
        if (mainArr.get(currentPageIndex).size() != 0) {
            mainArrIndex.set(currentPageIndex, mainArrIndex.get(currentPageIndex) + 1);
        }

        //if the current page is not null (in the middle of browsing), nullify the current and subsequent pages
        if (!elementIsNull(mainArr.get(currentPageIndex), mainArrIndex.get(currentPageIndex))) {
            nullifyElementFromIndex(mainArr.get(currentPageIndex), mainArrIndex.get(currentPageIndex));
        }

        //then override, but check the boundary first
        if (mainArrIndex.get(currentPageIndex) >= mainArr.get(currentPageIndex).size()) {
            //if index doesn't exist, add page
            mainArr.get(currentPageIndex).add(mainArrIndex.get(currentPageIndex), node);
        } else {
            //else, set page at index
            mainArr.get(currentPageIndex).set(mainArrIndex.get(currentPageIndex), node);
        }
        //display on mainpane
        mainPane.getChildren().setAll(mainArr.get(currentPageIndex));
    }

    /**
     * Hides and disables all nodes in the arrays
     */
    private void hideAll() {
        for (ArrayList<Node> arrN : mainArr) {
            for (Node n : arrN) {
                if (n != null) {
                    n.setVisible(false);
                    n.setDisable(true);
                }
            }
        }
    }

    /**
     * Count the number of non-null elements in the arrayList.
     *
     * @param arr arrayList of nodes
     * @return number of non-null elements in the arrayList of nodes
     */
    private int countNonNull(ArrayList arr) {
        int count = 0;
        for (Object o : arr) {
            if (o != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Check if particular the element in arr of specified index is null
     *
     * @param arr   the array to be checked
     * @param index the index of the element in the arr
     * @return true if element at index in arr is null
     */
    private boolean elementIsNull(ArrayList arr, int index) {
        try {
            arr.get(index);
            return false;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }

    /**
     * Make null the element of index i until the end
     *
     * @param arr the array to be modified
     * @param i   the starting index
     */
    private void nullifyElementFromIndex(ArrayList arr, int i) {
        for (; i < arr.size(); i++) {
            arr.set(i, null);
        }
    }

    /**
     * Fetch and update the local value of the currentPageIndex from mainController.
     * If the current page index changes, it means user is moving from one section to another
     * E.g. from Discover -> Favorites, thus currentPageIndex changes from 1 -> 3
     *
     */
    public void updateIndex() {
        if (this.currentPageIndex != MainController.currentPageIndex) {
            this.currentPageIndex = MainController.currentPageIndex;
        }
    }


    /**
     * Returns the number of pages in section.
     * E.g. user is ine Home, click and album,
     * then page count for home(index 0) is two
     *
     * @param currentPageIndex section index, e.g. 0 for Home, 1 for Discover, etc.
     * @return number of pages in section
     */
    public int getPagesCountInSection(int currentPageIndex) {
        return mainArr.get(currentPageIndex).size();
    }
}
