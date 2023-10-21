package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Random;

public class GameScreenController {

    @FXML // fx:id="minefieldGridPane"
    private GridPane minefieldGridPane;

    private boolean[][] revealedTiles;
    private boolean[][] bombLocations;
    private int bombCount;

    public void initializeGameScreenController(Difficulty difficulty, int height, int width) {
        revealedTiles = new boolean[height][width];
        bombLocations = new boolean[height][width];
        bombCount = calculateBombCount(difficulty, height, width);

        clearGridConstraints();
        addRowConstraints(height, 20);
        addColumnConstraints(width, 20);

        addLabelsToGrid(width, height);
        applyGridPaneStyles();
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

        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);

        label.setOnMouseClicked(mouseEvent -> {
                    startGame(column, row);
                    revealTile(label);
                });

        applyLabelStylesHidden(label);
        
        minefieldGridPane.add(label, column, row);
    }

    private void applyGridPaneStyles() {
        minefieldGridPane.setStyle(
                "-fx-border-color: #666666;"
        );
    }

    private void applyLabelStylesHidden(Label label) {
        label.setStyle(
                "-fx-background-color: #aaaaaa;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 1px;"
        );
    }

    private void applyLabelStylesRevealed(Label label) {
        label.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-background-color: #eeeeee;" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 1px;"
        );
    }

    private void applyLabelStylesBomb(Label label, String color) {
        label.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-background-color: " + color + ";" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 1px;"
        );
    }

    private void startGame(int firstMoveColumn, int firstMoveRow) {
        randomizeBombPlacement(firstMoveColumn, firstMoveRow);
        updateTilesOnMouseClickAction();
    }

    private void randomizeBombPlacement(int firstMoveColumn, int firstMoveRow) {
        Random random = new Random();

        int bombsPlaced = 0;
        int gridHeight = minefieldGridPane.getRowCount();
        int gridWidth = minefieldGridPane.getColumnCount();

        while (bombsPlaced < bombCount) {
            int row, column;

            do {
                row = random.nextInt(gridHeight);
                column = random.nextInt(gridWidth);
            } while (bombLocations[row][column] || (row == firstMoveRow && column == firstMoveColumn));

            bombLocations[row][column] = true;
            bombsPlaced++;
        }
    }

    private void updateTilesOnMouseClickAction() {
        for (Node node : minefieldGridPane.getChildren()) {
            if (node instanceof Label label) {
                label.setOnMouseClicked(mouseEvent -> {
                    label.setOnMouseClicked(null);
                    revealTile(label);
                });
            }
        }
    }

    private void revealTile(Label label) {
        int row = GridPane.getRowIndex(label);
        int column = GridPane.getColumnIndex(label);

        if(!bombLocations[row][column]) {
            int adjacentBombCount = getAdjacentBombCount(row,column);
            if(adjacentBombCount != 0)
                label.setText(Integer.toString(adjacentBombCount));
            revealedTiles[row][column] = true;
            applyLabelStylesRevealed(label);
        } else revealAllBombTiles(row,column);
    }

    private void revealAllBombTiles(int row, int column) {
        for (Node node : minefieldGridPane.getChildren()) {
            if (node instanceof Label label) {
                int r = GridPane.getRowIndex(node);
                int c = GridPane.getColumnIndex(node);
                if (bombLocations[r][c]) {
                    label.setText("X");
                    String color = r == row && c == column
                            ? "red"
                            : "yellow";
                    applyLabelStylesBomb(label,color);
                }
            }
        }
    }

    private int getAdjacentBombCount(int row, int column) {
        int adjacentBombs = 0;

        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if(isValidTile(row+i,column+j)) {
                    if(bombLocations[row+i][column+j]) {
                        adjacentBombs++;
                    }
                }
            }
        }

        return adjacentBombs;
    }

    private boolean isValidTile(int row, int column) {
        int gridHeight = minefieldGridPane.getRowCount();
        int gridWidth = minefieldGridPane.getColumnCount();

        return row >= 0 && column >= 0 && row < gridHeight && column < gridWidth;
    }

}
