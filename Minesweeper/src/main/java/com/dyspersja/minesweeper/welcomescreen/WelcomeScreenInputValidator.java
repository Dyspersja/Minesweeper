package com.dyspersja.minesweeper.welcomescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class WelcomeScreenInputValidator {

    public boolean validateHeightInput(TextField heightTextField) {
        return validateTextFieldInput(heightTextField, 5, 40);
    }

    public boolean validateWidthInput(TextField widthTextField) {
        return validateTextFieldInput(widthTextField, 5, 70);
    }

    public boolean validateDifficultyInput(ChoiceBox<Difficulty> difficultyChoiceBox) {
        return difficultyChoiceBox.getValue() != null;
    }

    private boolean validateTextFieldInput(TextField textField, int min, int max) {
        try {
            int number = Integer.parseInt(textField.getText());
            return number >= min && number < max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
