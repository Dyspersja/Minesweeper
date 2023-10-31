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
                    onTileClicked(label);
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
                    onTileClicked(label);
                });
            }
        }
    }

    private void onTileClicked(Label label) {
        int row = GridPane.getRowIndex(label);
        int column = GridPane.getColumnIndex(label);

        if(!bombLocations[row][column]) {
            revealedTiles[row][column] = true;

            int adjacentBombCount = getAdjacentBombCount(row,column);
            if (adjacentBombCount != 0) revealTile(label, adjacentBombCount);
            else revealAdjacentTiles(label, row, column);
        } else revealAllBombTiles(label);
    }

    private void revealTile(Label label, int adjacentBombCount) {
        label.setText(Integer.toString(adjacentBombCount));
        applyLabelStylesRevealed(label);
    }

    private void revealAdjacentTiles(Label label, int row, int column) {
        applyLabelStylesRevealed(label);

        for (Node node : minefieldGridPane.getChildren()) {
            if (node instanceof Label tileLabel) {
                int r = GridPane.getRowIndex(node);
                int c = GridPane.getColumnIndex(node);
                if (r >= row - 1 && r <= row + 1) {
                    if (c >= column - 1 && c <= column + 1) {
                        if (!revealedTiles[r][c]) {
                            revealedTiles[r][c] = true;

                            int adjacentBombCount = getAdjacentBombCount(r,c);
                            if (adjacentBombCount != 0) revealTile(tileLabel, adjacentBombCount);
                            else revealAdjacentTiles(tileLabel, r, c);
                        }
                    }
                }
            }
        }
    }

    private void revealAllBombTiles(Label label) {
        for (Node node : minefieldGridPane.getChildren()) {
            if (node instanceof Label tileLabel) {
                int r = GridPane.getRowIndex(node);
                int c = GridPane.getColumnIndex(node);
                if (bombLocations[r][c]) {
                    tileLabel.setText("X");
                    applyLabelStylesBomb(tileLabel,"yellow");
                }
            }
        }
        applyLabelStylesBomb(label,"red");
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
