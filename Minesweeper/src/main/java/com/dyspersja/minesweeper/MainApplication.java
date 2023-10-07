package com.dyspersja.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private final String WINDOW_TITLE = "Minesweeper";
    private final String WELCOME_SCREEN_FXML = "/fxml/WelcomeScreen.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(WELCOME_SCREEN_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(WINDOW_TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
