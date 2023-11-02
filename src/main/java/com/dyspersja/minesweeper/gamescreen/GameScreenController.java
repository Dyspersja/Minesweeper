package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.endscreen.GameLostScreenController;
import com.dyspersja.minesweeper.endscreen.GameWonScreenController;
import com.dyspersja.minesweeper.errorscreen.ErrorScreenProvider;
import com.dyspersja.minesweeper.model.Difficulty;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        var logic = new GameScreenLogic(this, difficulty, height, width);
        logic.initializeTileGrid(minefieldGridPane);
    }

    public void displayGameWonWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameWonScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());

            GameWonScreenController gameWonScreenController = fxmlLoader.getController();
            gameWonScreenController.passMainWindowStage((Stage) minefieldGridPane.getScene().getWindow());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Game Won");
            stage.setResizable(false);
            stage.setOnCloseRequest(event -> Platform.exit());

            stage.setScene(scene);
            stage.showAndWait();
        } catch (IllegalStateException | IOException e) {
            ErrorScreenProvider.displayErrorWindow(e);
        }
    }

    public void displayGameLostWindow(Difficulty difficulty, int gridHeight, int gridWidth) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameLostScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());

            GameLostScreenController gameLostScreenController = fxmlLoader.getController();
            gameLostScreenController.passMainWindowStage((Stage) minefieldGridPane.getScene().getWindow());
            gameLostScreenController.passLastGameOptions(difficulty, gridHeight, gridWidth);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Game Lost");
            stage.setResizable(false);
            stage.setOnCloseRequest(event -> Platform.exit());

            stage.setScene(scene);
            stage.showAndWait();
        } catch (IllegalStateException | IOException e) {
            ErrorScreenProvider.displayErrorWindow(e);
        }
    }
}
