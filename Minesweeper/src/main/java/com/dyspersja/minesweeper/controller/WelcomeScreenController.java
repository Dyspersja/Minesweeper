package com.dyspersja.minesweeper.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class WelcomeScreenController {

    @FXML // fx:id="heightTextField"
    private TextField heightTextField;
    @FXML // fx:id="widthTextField"
    private TextField widthTextField;
    @FXML // fx:id="difficultyChoiceBox"
    private ChoiceBox<?> difficultyChoiceBox;
    @FXML // fx:id="startGameButton"
    private Button startGameButton;

    @FXML
    void startGame(ActionEvent event) {

    }

    @FXML
    public void initialize() {

    }
}

