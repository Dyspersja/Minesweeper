package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import com.dyspersja.minesweeper.model.Tile;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

        addTilesToGrid(width, height);
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

    private void addTilesToGrid(int width, int height) {
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                createTile(column, row);
            }
        }
    }

    private void createTile(int column, int row) {
        Tile tile = new Tile();

        tile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tile.setAlignment(Pos.CENTER);

        tile.setOnMouseClicked(mouseEvent -> {
                    startGame(column, row);
                    onTileClicked(tile);
                });

        applyTileStyle(tile, "#aaaaaa");
        
        minefieldGridPane.add(tile, column, row);
    }

    private void applyGridPaneStyles() {
        minefieldGridPane.setStyle(
                "-fx-border-color: #666666;"
        );
    }

    private void applyTileStyle(Tile tile, String color) {
        tile.setStyle(
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
            if (node instanceof Tile tile) {
                tile.setOnMouseClicked(mouseEvent -> {
                    tile.setOnMouseClicked(null);
                    onTileClicked(tile);
                });
            }
        }
    }

    private void removeTilesOnMouseClickAction(Tile tile) {
        tile.setOnMouseClicked(null);
    }

    private void onTileClicked(Tile tile) {
        int row = GridPane.getRowIndex(tile);
        int column = GridPane.getColumnIndex(tile);

        if(!bombLocations[row][column]) {
            revealedTiles[row][column] = true;

            int adjacentBombCount = getAdjacentBombCount(row,column);
            if (adjacentBombCount != 0)
                revealTile(tile, Integer.toString(adjacentBombCount), "#eeeeee");
            else revealAdjacentTiles(tile, row, column);
        } else revealAllBombTiles(tile);
    }

    private void revealTile(Tile tile, String symbol, String color) {
        tile.setText(symbol);
        applyTileStyle(tile, color);
        removeTilesOnMouseClickAction(tile);
    }

    private void revealAdjacentTiles(Tile tile, int row, int column) {
        revealTile(tile, "", "#eeeeee");

        for (Node node : minefieldGridPane.getChildren()) {
            if (node instanceof Tile currentTile) {
                int r = GridPane.getRowIndex(node);
                int c = GridPane.getColumnIndex(node);
                if (r >= row - 1 && r <= row + 1) {
                    if (c >= column - 1 && c <= column + 1) {
                        if (!revealedTiles[r][c]) {
                            revealedTiles[r][c] = true;

                            int adjacentBombCount = getAdjacentBombCount(r,c);
                            if (adjacentBombCount != 0)
                                revealTile(currentTile, Integer.toString(adjacentBombCount), "#eeeeee");
                            else revealAdjacentTiles(currentTile, r, c);
                        }
                    }
                }
            }
        }
    }

    private void revealAllBombTiles(Tile tile) {
        for (Node node : minefieldGridPane.getChildren()) {
            if (node instanceof Tile currentTile) {
                int r = GridPane.getRowIndex(node);
                int c = GridPane.getColumnIndex(node);
                if (bombLocations[r][c]) revealTile(currentTile, "X", "yellow");
            }
        }
        revealTile(tile, "X", "red");
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
