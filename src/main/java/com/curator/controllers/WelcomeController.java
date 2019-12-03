package com.curator.controllers;

import com.curator.tools.DBTools;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {


    @FXML
    private AnchorPane welcomeAnchorPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button okButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label instructionLabel;

    private Scene scene;
    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitButton.setOnMousePressed(event -> stage.close());

        usernameTextField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
            }
        });



        okButton.setOnMousePressed(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                DBTools.initialize(usernameTextField.getText());

                boolean isNewUser = DBTools.getLoginCount() == 1;

                /*
                If is a new user, show survey which music the user likes
                 */
                if (isNewUser) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/survey.fxml"));
                    try {
                        stage.setScene(new Scene(loader.load()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SurveyController surveyController = loader.getController();
                    surveyController.setStage(stage);
                    stage.setHeight(600);
                    stage.setWidth(600);

                    stage.setTitle("Music Curator");
                    stage.show();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
                    try {
                        stage.setScene(new Scene(loader.load()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setHeight(600);
                    stage.setMinWidth(1300);
                    stage.setTitle("Music Curator");
                    stage.show();
                }
            }
        });
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
