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
        validateUserInput();
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
        return new TextFormatter<>(newText ->
                newText.getControlNewText().matches("[0-9]{0,2}")
                        ? newText
                        : null
        );
    }

    private boolean validateUserInput() {
        return  validateHeightInput() &&
                validateWidthInput() &&
                validateDifficultyInput();
    }

    private boolean validateTextFieldInput(TextField textField, int min, int max) {
        try {
            int number = Integer.parseInt(textField.getText());
            return number >= min && number < max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateHeightInput() {
        return validateTextFieldInput(heightTextField, 5, 40);
    }

    private boolean validateWidthInput() {
        return validateTextFieldInput(widthTextField, 5, 70);
    }

    private boolean validateDifficultyInput() {
        return difficultyChoiceBox.getValue() != null;
    }
}

