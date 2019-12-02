package com.curator.views;

import com.curator.controllers.SurveyController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

public class SurveyButton extends Button {

    private boolean isClicked = false;
    private FlowPane parent;

    private SurveyController surveyController;


    public SurveyButton(String text, FlowPane parentContainer){
        this.parent = parentContainer;
        setText(text);
        setStyle("-fx-background-color: none");
        setAlignment(Pos.CENTER);

        setOnMouseEntered(event -> {
            if (!isClicked){
                setStyle("-fx-background-color: #B0B0B0");
            }
        });

        setOnMouseExited(event -> {
            if (!isClicked){
                setStyle("-fx-background-color: none");
            }
        });

        setOnMousePressed(event -> {
            if (isClicked){
                setStyle("-fx-background-color: none");
            } else {
                System.out.println("User likes " + text);
                setStyle("-fx-background-color: #B0B0B0");

                parent.getChildren().add(new SurveyButton(
                        text, parent
                ));
            }
            isClicked = !isClicked;
        });
    }
}
