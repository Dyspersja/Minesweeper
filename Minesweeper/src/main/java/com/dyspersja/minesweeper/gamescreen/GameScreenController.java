package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

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
}
