package com.dyspersja.minesweeper.controller;

import com.dyspersja.minesweeper.model.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class WelcomeScreenController {

    @FXML // fx:id="heightTextField"
    private TextField heightTextField;
    @FXML // fx:id="widthTextField"
    private TextField widthTextField;
    @FXML // fx:id="difficultyChoiceBox"
    private ChoiceBox<Difficulty> difficultyChoiceBox;
    @FXML // fx:id="startGameButton"
    private Button startGameButton;

    @FXML
    void startGame(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        initializeHeightTextField();
        initializeWidthTextField();
        initializeDifficultyChoiceBox();
    }

    private void initializeHeightTextField() {
        heightTextField.setTextFormatter(getNumberTextFormatter());
        heightTextField.setText("12");
    }

    private void initializeWidthTextField() {
        widthTextField.setTextFormatter(getNumberTextFormatter());
        widthTextField.setText("10");
    }

    private void initializeDifficultyChoiceBox() {
        difficultyChoiceBox.getItems()
                .addAll(Difficulty.values());
        difficultyChoiceBox.setValue(Difficulty.INTERMEDIATE);
    }

    private TextFormatter<?> getNumberTextFormatter() {
        return new TextFormatter<>(change ->
                change.getText().matches("[0-9]*")
                        ? change
                        : null
        );
    }

}

