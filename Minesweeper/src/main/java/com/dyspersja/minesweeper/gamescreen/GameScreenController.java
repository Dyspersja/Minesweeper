package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import com.dyspersja.minesweeper.model.Tile;
import com.dyspersja.minesweeper.model.TileStyle;
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

        var initializer = new GameScreenInitializer(minefieldGridPane);

        initializer.clearGridConstraints();
        initializer.addRowConstraints(gridHeight);
        initializer.addColumnConstraints(gridWidth);
        initializer.applyGridPaneStyles();

        initializeTileGrid();
    }

    private void initializeTileGrid() {
        tiles = new Tile[gridHeight][gridWidth];

        for (int column = 0; column < gridWidth; column++) {
            for (int row = 0; row < gridHeight; row++) {
                Tile tile = new Tile();

                tile.setOnMouseClicked(mouseEvent -> {
                    initializeBombLocations(tile);
                    reassignTilesClickEvent();
                    onTileClicked(tile);
                });

                tiles[row][column] = tile;
                minefieldGridPane.add(tile, column, row);
            }
        }
    }

    private void initializeBombLocations(Tile tile) {
        int bombCount = (gridHeight * gridWidth) / difficulty.getBombDensityFactor() + 1;
        Random random = new Random();

        while (bombCount > 0) {
            int row = random.nextInt(gridHeight);
            int column = random.nextInt(gridWidth);

            if (tiles[row][column].isBomb() || tiles[row][column] == tile) continue;
            tiles[row][column].setBomb(true);
            incrementAdjacentBombCount(row,column);
            bombCount--;
        }
    }

    private void incrementAdjacentBombCount(int row, int column) {
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (isValidTile(row+i, column+j))
                    tiles[row+i][column+j].incrementAdjacentBombs();
    }

    private boolean isValidTile(int row, int column) {
        return row >= 0 && column >= 0 && row < gridHeight && column < gridWidth;
    }

    private void reassignTilesClickEvent() {
        for (Tile[] rows : tiles)
            for (Tile tile : rows)
                tile.setOnMouseClicked(mouseEvent ->
                        onTileClicked(tile));
    }

    private void onTileClicked(Tile tile) {
        if (tile.isBomb()) onGameLost(tile);
        else revealTile(tile);
    }

    private void revealTile(Tile tile) {
        tile.reveal(TileStyle.REVEALED);
        if (tile.getAdjacentBombs() == 0)
            revealAdjacentTiles(tile);
    }

    private void revealAdjacentTiles(Tile tile) {
        int row = GridPane.getRowIndex(tile);
        int column = GridPane.getColumnIndex(tile);

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (isValidTile(row+i, column+j))
                    if (!tiles[row+i][column+j].isRevealed())
                        revealTile(tiles[row+i][column+j]);
    }

    private void onGameWon() {
        for (Tile[] rows : tiles)
            for (Tile currentTile : rows)
                if (currentTile.isBomb())
                    currentTile.reveal(TileStyle.BOMB_WON);
    }

    private void onGameLost(Tile tile) {
        for (Tile[] rows : tiles)
            for (Tile currentTile : rows)
                if (currentTile.isBomb())
                    currentTile.reveal(TileStyle.BOMB_LOST);

        tile.reveal(TileStyle.BOMB_LOST_STRUCK);
    }
}
