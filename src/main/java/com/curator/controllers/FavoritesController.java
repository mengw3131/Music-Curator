package com.curator.controllers;

import com.curator.tools.SpotifyTools;
import com.curator.models.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller to favorites.fxml
 */
public class FavoritesController implements Initializable {
//    SpotifyApi api = SpotifyTools.getApi();

    @FXML
    TableView mainTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       fillColumn();
    }

    /**
     * Get random
     * @return
     */
    public ObservableList<Track> getTracks(){
        ObservableList<Track> track_list = FXCollections.observableArrayList();
        track_list.addAll(Objects.requireNonNull(SpotifyTools.searchTracks("Jazz", 10)));
        return track_list;
    }

    public void fillColumn(){
        mainTable.getColumns().clear(); //to avoid duplicates

        TableColumn<Track, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Track, Integer> timeColumn = new TableColumn<>("Time");
        TableColumn<Track, String> artistColumn = new TableColumn<>("com.curator.models.Artist");
        TableColumn<Track, String> albumColumn = new TableColumn<>("com.curator.models.Album");

        //Correspond to com.curator.models.Track object variable names
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("durationString"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistsString"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("albumString"));

        mainTable.setItems(getTracks());
        mainTable.getColumns().addAll(nameColumn, timeColumn, artistColumn, albumColumn);
    }
}
