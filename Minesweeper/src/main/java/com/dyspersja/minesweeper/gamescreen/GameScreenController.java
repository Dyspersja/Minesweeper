package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import com.dyspersja.minesweeper.model.Tile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Random;

public class GameScreenController {

    @FXML // fx:id="minefieldGridPane"
    private GridPane minefieldGridPane;

    private Tile[][] tiles;
    private int bombCount;

    public void initializeGameScreenController(Difficulty difficulty, int height, int width) {
        bombCount = calculateBombCount(difficulty, height, width);

        clearGridConstraints();
        addRowConstraints(height);
        addColumnConstraints(width);

        applyGridPaneStyles();
        addTilesToGrid(width, height);
    }

    private int calculateBombCount(Difficulty difficulty, int height, int width) {
        return (height * width) / difficulty.getBombDensityFactor() + 1;
    }

    private void clearGridConstraints() {
        minefieldGridPane.getRowConstraints().clear();
        minefieldGridPane.getColumnConstraints().clear();
    }

    private void addRowConstraints(int count) {
        RowConstraints row = new RowConstraints();
        row.setMinHeight(20);
        for (int i = 0; i < count; i++) {
            minefieldGridPane.getRowConstraints().add(row);
        }
    }

    private void addColumnConstraints(int count) {
        ColumnConstraints column = new ColumnConstraints();
        column.setMinWidth(20);
        for (int i = 0; i < count; i++) {
            minefieldGridPane.getColumnConstraints().add(column);
        }
    }

    private void addTilesToGrid(int width, int height) {
        tiles = new Tile[height][width];

        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                Tile tile = new Tile();

                tiles[row][column] = tile;
                minefieldGridPane.add(tile, column, row);
                setInitialOnMouseClickAction(tile, column, row);
            }
        }
    }

    private void setInitialOnMouseClickAction(Tile tile, int column, int row) {
        tile.setOnMouseClicked(mouseEvent -> {
                    startGame(column, row);
                    onTileClicked(tile);
                });
    }

    private void applyGridPaneStyles() {
        minefieldGridPane.setStyle(
                "-fx-border-color: #666666;"
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
            } while (tiles[row][column].isBomb() || (row == firstMoveRow && column == firstMoveColumn));

            tiles[row][column].setBomb(true);
            bombsPlaced++;
        }
    }

    private void updateTilesOnMouseClickAction() {
        for (Tile[] rows : tiles)
            for (Tile tile : rows)
                tile.setOnMouseClicked(mouseEvent ->
                        onTileClicked(tile));
    }

    private void onTileClicked(Tile tile) {
        int row = GridPane.getRowIndex(tile);
        int column = GridPane.getColumnIndex(tile);

        if(!tile.isBomb()) {
            tile.setRevealed(true);

            int adjacentBombCount = getAdjacentBombCount(row,column);
            if (adjacentBombCount != 0)
                tile.reveal(Integer.toString(adjacentBombCount), "#eeeeee");
            else revealAdjacentTiles(tile, row, column);
        } else revealAllBombTiles(tile);
    }

    private void revealAdjacentTiles(Tile tile, int row, int column) {
        tiles[row][column].reveal("", "#eeeeee");

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (isValidTile(row+i, column+j))
                    if (!tiles[row+i][column+j].isRevealed()) {
                        tiles[row+i][column+j].setRevealed(true);

                        int adjacentBombCount = getAdjacentBombCount(row+i,column+j);
                        if (adjacentBombCount != 0)
                            tiles[row+i][column+j].reveal(Integer.toString(adjacentBombCount), "#eeeeee");
                        else revealAdjacentTiles(tiles[row+i][column+j], row+i, column+j);
                    }
    }

    private void revealAllBombTiles(Tile tile) {
        for (Tile[] rows : tiles)
            for (Tile currentTile : rows)
                if (currentTile.isBomb())
                    currentTile.reveal("X", "yellow");

        tile.reveal("X", "red");
    }

    private int getAdjacentBombCount(int row, int column) {
        int adjacentBombs = 0;

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (isValidTile(row+i, column+j))
                    if (tiles[row+i][column+j].isBomb())
                        adjacentBombs++;

        return adjacentBombs;
    }

    private boolean isValidTile(int row, int column) {
        int gridHeight = minefieldGridPane.getRowCount();
        int gridWidth = minefieldGridPane.getColumnCount();

        return row >= 0 && column >= 0 && row < gridHeight && column < gridWidth;
    }

}
