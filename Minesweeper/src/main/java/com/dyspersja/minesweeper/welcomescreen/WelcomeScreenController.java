package com.dyspersja.minesweeper.welcomescreen;

import com.dyspersja.minesweeper.model.Difficulty;
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
    private ChoiceBox<Difficulty> difficultyChoiceBox;
    @FXML // fx:id="startGameButton"
    private Button startGameButton;

    @FXML
    void startGame(ActionEvent event) {
        validateUserInput();
    }

    @FXML
    public void initialize() {
        var initializer = new WelcomeScreenInitializer();

        initializer.initializeHeightTextField(heightTextField);
        initializer.initializeWidthTextField(widthTextField);
        initializer.initializeDifficultyChoiceBox(difficultyChoiceBox);
    }

    private boolean validateUserInput() {
        var validator = new WelcomeScreenInputValidator();

        return  validator.validateHeightInput(heightTextField) &&
                validator.validateWidthInput(widthTextField) &&
                validator.validateDifficultyInput(difficultyChoiceBox);
    }
}

