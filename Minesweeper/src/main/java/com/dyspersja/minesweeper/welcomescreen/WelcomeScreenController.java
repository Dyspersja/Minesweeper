package com.dyspersja.minesweeper.welcomescreen;

import com.dyspersja.minesweeper.gamescreen.GameScreenController;
import com.dyspersja.minesweeper.model.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
    void startGame(ActionEvent event) throws IOException {
        if(!validateUserInput()) {
            displayIncorrectInputWindow();
            return;
        }

        // load game board scene from FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameScreen.fxml"));
        Stage stage = (Stage) startGameButton.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());

        // pass user input to gameScreenController
        GameScreenController gameScreenController = fxmlLoader.getController();
        gameScreenController.initializeGameScreenController(
                difficultyChoiceBox.getValue(),
                Integer.parseInt(heightTextField.getText()),
                Integer.parseInt(widthTextField.getText())
        );

        stage.setScene(scene);
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

    private void displayIncorrectInputWindow(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/IncorrectInputScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Incorrect Input");
            stage.setResizable(false);

            stage.setScene(scene);
            stage.showAndWait();
        } catch (IllegalStateException | IOException ignored) {}
    }
}

