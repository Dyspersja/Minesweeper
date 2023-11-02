package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameScreenController {

    @FXML // fx:id="minefieldGridPane"
    private GridPane minefieldGridPane;

    public void initializeGameScreenController(Difficulty difficulty, int height, int width) {
        var initializer = new GameScreenInitializer(minefieldGridPane);

        initializer.clearGridConstraints();
        initializer.addRowConstraints(height);
        initializer.addColumnConstraints(width);
        initializer.applyGridPaneStyles();

        initializeGameLogic(difficulty, height, width);
    }

    private void initializeGameLogic(Difficulty difficulty, int height, int width) {
        var logic = new GameScreenLogic(difficulty, height, width);
        logic.initializeTileGrid(minefieldGridPane);
    }

    public void displayGameWonWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameWonScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Game Won");
            stage.setResizable(false);
            stage.setOnCloseRequest(event -> Platform.exit());

            stage.setScene(scene);
            stage.showAndWait();
        } catch (IllegalStateException | IOException e) {
            displayErrorWindow(e);
        }
    }

    public void displayGameLostWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameLostScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Game Lost");
            stage.setResizable(false);
            stage.setOnCloseRequest(event -> Platform.exit());

            stage.setScene(scene);
            stage.showAndWait();
        } catch (IllegalStateException | IOException e) {
            displayErrorWindow(e);
        }
    }

    private void displayErrorWindow(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("Error loading FXML");
        alert.setContentText("An error occurred while loading the FXML file: " + e.getMessage());

        alert.showAndWait();
        Platform.exit();
    }
}
