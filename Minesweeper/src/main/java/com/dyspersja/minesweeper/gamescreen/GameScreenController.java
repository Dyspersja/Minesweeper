package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameScreenController {

    @FXML // fx:id="minefieldGridPane"
    private GridPane minefieldGridPane;

    private int bombCount;

    public void initializeGameScreenController(Difficulty difficulty, int height, int width) {
        bombCount = calculateBombCount(difficulty, height, width);

        clearGridConstraints();
        addRowConstraints(height, 20);
        addColumnConstraints(width, 20);

        addLabelsToGrid(width, height);
    }

    private int calculateBombCount(Difficulty difficulty, int height, int width) {
        return (height * width) / difficulty.getBombDensityFactor() + 1;
    }

    private void clearGridConstraints() {
        minefieldGridPane.getRowConstraints().clear();
        minefieldGridPane.getColumnConstraints().clear();
    }

    private void addRowConstraints(int count, double minHeight) {
        RowConstraints row = new RowConstraints();
        row.setMinHeight(minHeight);
        for (int i = 0; i < count; i++) {
            minefieldGridPane.getRowConstraints().add(row);
        }
    }

    private void addColumnConstraints(int count, double minWidth) {
        ColumnConstraints column = new ColumnConstraints();
        column.setMinWidth(minWidth);
        for (int i = 0; i < count; i++) {
            minefieldGridPane.getColumnConstraints().add(column);
        }
    }

    private void addLabelsToGrid(int width, int height) {
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                createLabel(column, row);
            }
        }
    }

    private void createLabel(int column, int row) {
        Label label = new Label();

        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);

        label.setOnMouseClicked(mouseEvent ->
                startGame(column, row));

        minefieldGridPane.add(label, column, row);
    }

    private void startGame(int firstMoveColumn, int firstMoveRow) {
    }
}
