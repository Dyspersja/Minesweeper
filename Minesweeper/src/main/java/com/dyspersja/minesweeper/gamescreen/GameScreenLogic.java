package com.dyspersja.minesweeper.gamescreen;

import com.dyspersja.minesweeper.model.Difficulty;
import com.dyspersja.minesweeper.model.Tile;
import com.dyspersja.minesweeper.model.TileStyle;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class GameScreenLogic {

    private Tile[][] tiles;
    private final Difficulty difficulty;
    private final int gridHeight;
    private final int gridWidth;

    public GameScreenLogic(Difficulty difficulty, int gridHeight, int gridWidth) {
        this.difficulty = difficulty;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    public void initializeTileGrid(GridPane minefieldGridPane) {
        tiles = new Tile[gridHeight][gridWidth];

        for (int column = 0; column < gridWidth; column++) {
            for (int row = 0; row < gridHeight; row++) {
                Tile tile = new Tile();

                assignInitialTileClickEvent(tile);

                tiles[row][column] = tile;
                minefieldGridPane.add(tile, column, row);
            }
        }
    }

    private void assignInitialTileClickEvent(Tile tile) {
        tile.setOnMouseClicked(mouseEvent -> {
            initializeBombLocations(tile);
            reassignTileClickEvent();
            onTileClicked(tile);
        });
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

    private void reassignTileClickEvent() {
        for (Tile[] rows : tiles)
            for (Tile tile : rows)
                tile.setOnMouseClicked(mouseEvent ->
                        onTileClicked(tile));
    }

    private void onTileClicked(Tile tile) {
        if (tile.isBomb()) onGameLost(tile);
        else revealTile(tile);

        if (checkIfGameWon()) onGameWon();
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

    private boolean checkIfGameWon() {
        for (Tile[] rows : tiles)
            for (Tile tile : rows)
                if (!tile.isRevealed() && !tile.isBomb())
                    return false;

        return true;
    }

    private void onGameWon() {
        for (Tile[] rows : tiles)
            for (Tile tile : rows)
                if (tile.isBomb())
                    tile.reveal(TileStyle.BOMB_WON);
    }

    private void onGameLost(Tile tile) {
        for (Tile[] rows : tiles)
            for (Tile currentTile : rows)
                if (currentTile.isBomb())
                    currentTile.reveal(TileStyle.BOMB_LOST);

        tile.reveal(TileStyle.BOMB_LOST_STRUCK);
    }
}
