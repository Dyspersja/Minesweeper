package com.dyspersja.minesweeper.gamescreen;

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
        var logic = new GameScreenLogic(difficulty, height, width);
        logic.initializeTileGrid(minefieldGridPane);
    }

    public void displayGameWonWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameWonScreen.fxml"));
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Game Won");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> Platform.exit());

        stage.showAndWait();
    }

    public void displayGameLostWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameLostScreen.fxml"));
        Stage stage = new Stage();
        try {
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Game Lost");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> Platform.exit());

        stage.showAndWait();
    }
}
