package com.dyspersja.minesweeper.endscreen;

import com.dyspersja.minesweeper.errorscreen.ErrorScreenProvider;
import com.dyspersja.minesweeper.gamescreen.GameScreenController;
import com.dyspersja.minesweeper.model.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameLostScreenController {

    @FXML // fx:id="mainMenuButton"
    private Button mainMenuButton;

    @FXML // fx:id="tryAgainButton"
    private Button tryAgainButton;

    private Stage mainStage;

    private Difficulty difficulty;
    private int gridHeight;
    private int gridWidth;

    public void passMainWindowStage(Stage stage) {
        this.mainStage = stage;
    }

    public void passLastGameOptions(Difficulty difficulty, int gridHeight, int gridWidth) {
        this.difficulty = difficulty;
        this.gridHeight = gridHeight;
        this.gridWidth=gridWidth;
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WelcomeScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            mainStage.setScene(scene);
        } catch (IllegalStateException | IOException e) {
            ErrorScreenProvider.displayErrorWindow(e);
        }
        closeWindow();
    }

    @FXML
    void restartGame(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GameScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            GameScreenController gameScreenController = fxmlLoader.getController();
            gameScreenController.initializeGameScreenController(difficulty, gridHeight, gridWidth);

            mainStage.setScene(scene);
        } catch (IllegalStateException | IOException e) {
            ErrorScreenProvider.displayErrorWindow(e);
        }
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) mainMenuButton.getScene().getWindow();
        stage.close();
    }

}
