package com.dyspersja.minesweeper.welcomescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class WelcomeScreenInitializer {

    public void initializeHeightTextField(TextField heightTextField) {
        heightTextField.setTextFormatter(getNumberTextFormatter());
        heightTextField.setText("12");
    }

    public void initializeWidthTextField(TextField widthTextField) {
        widthTextField.setTextFormatter(getNumberTextFormatter());
        widthTextField.setText("10");
    }

    public void initializeDifficultyChoiceBox(ChoiceBox<Difficulty> difficultyChoiceBox) {
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
}
