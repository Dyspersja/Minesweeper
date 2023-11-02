package com.dyspersja.minesweeper.endscreen;

import com.dyspersja.minesweeper.errorscreen.ErrorScreenProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameWonScreenController {

    @FXML // fx:id="mainMenuButton"
    private Button mainMenuButton;

    private Stage mainStage;

    public void passMainWindowStage(Stage stage) {
        this.mainStage = stage;
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

    private void closeWindow() {
        Stage stage = (Stage) mainMenuButton.getScene().getWindow();
        stage.close();
    }
}
