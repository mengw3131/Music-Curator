package com.curator.controllers;

import com.curator.Main;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller to favorites.fxml
 */
public class FavoritesController implements Initializable {
    SpotifyApi api = Main.api;

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

        for (Track track : api.searchTracks("Jazz", 10)){
            track_list.add(track);
        }

        return track_list;
    }

    public void fillColumn(){
        mainTable.getColumns().clear(); //to avoid duplicates

        TableColumn<Track, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Track, Integer> timeColumn = new TableColumn<>("Time");
        TableColumn<Track, String> artistColumn = new TableColumn<>("Artist");
        TableColumn<Track, String> albumColumn = new TableColumn<>("Album");

        //Correspond to Track object variable names
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("durationString"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistsString"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("albumString"));

        mainTable.setItems(getTracks());
        mainTable.getColumns().addAll(nameColumn, timeColumn, artistColumn, albumColumn);
    }

    public void setApi(SpotifyApi api) {
        this.api = api;
    }
}
