package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import com.dyspersja.minesweeper.model.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class GameScreenController {

    @FXML // fx:id="minefieldGridPane"
    private GridPane minefieldGridPane;

    private Tile[][] tiles;
    private Difficulty difficulty;
    private int gridHeight;
    private int gridWidth;

    public void initializeGameScreenController(Difficulty difficulty, int height, int width) {
        this.difficulty = difficulty;
        this.gridHeight = height;
        this.gridWidth = width;

        var initializer = new GameScreenControllerInitializer();

        initializer.clearGridConstraints(minefieldGridPane);
        initializer.addRowConstraints(minefieldGridPane, gridHeight);
        initializer.addColumnConstraints(minefieldGridPane, gridWidth);
        initializer.applyGridPaneStyles(minefieldGridPane);

        addTilesToGrid();
    }

    private void addTilesToGrid() {
        tiles = new Tile[gridHeight][gridWidth];

        for (int column = 0; column < gridWidth; column++) {
            for (int row = 0; row < gridHeight; row++) {
                Tile tile = new Tile();

                tiles[row][column] = tile;
                minefieldGridPane.add(tile, column, row);
                setInitialOnMouseClickAction(tile);
            }
        }
    }

    private void setInitialOnMouseClickAction(Tile tile) {
        tile.setOnMouseClicked(mouseEvent -> {
                    startGame(tile);
                    onTileClicked(tile);
                });
    }

    private void startGame(Tile tile) {
        randomizeBombPlacement(tile);
        updateTilesOnMouseClickAction();
    }

    private void randomizeBombPlacement(Tile tile) {
        int bombCount = (gridHeight * gridWidth) / difficulty.getBombDensityFactor() + 1;
        Random random = new Random();

        while (bombCount > 0) {
            int row = random.nextInt(gridHeight);
            int column = random.nextInt(gridWidth);

            if (tiles[row][column].isBomb() || tiles[row][column] == tile) continue;
            tiles[row][column].setBomb(true);
            bombCount--;
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
        tile.reveal("", "#eeeeee");

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
        return row >= 0 && column >= 0 && row < gridHeight && column < gridWidth;
    }

}
