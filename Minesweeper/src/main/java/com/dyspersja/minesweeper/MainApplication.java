package com.dyspersja.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WelcomeScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Minesweeper");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IllegalStateException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Error loading FXML");
            alert.setContentText("An error occurred while loading the FXML file: " + e.getMessage());

            alert.showAndWait();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
