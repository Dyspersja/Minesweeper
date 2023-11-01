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

                tile.setOnMouseClicked(mouseEvent -> {
                    randomizeBombPlacement(tile);
                    updateTilesOnMouseClickAction();
                    onTileClicked(tile);
                });
                tiles[row][column] = tile;
                minefieldGridPane.add(tile, column, row);
            }
        }
    }

    private void randomizeBombPlacement(Tile tile) {
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

    private void updateTilesOnMouseClickAction() {
        for (Tile[] rows : tiles)
            for (Tile tile : rows)
                tile.setOnMouseClicked(mouseEvent ->
                        onTileClicked(tile));
    }

    private void onTileClicked(Tile tile) {
        if (tile.isBomb()) onGameLost(tile);
        else if (tile.getAdjacentBombs() == 0)
            revealAdjacentTiles(tile);
        else tile.reveal("#eeeeee");
    }

    private void revealAdjacentTiles(Tile tile) {
        tile.reveal("#eeeeee");
        int row = GridPane.getRowIndex(tile);
        int column = GridPane.getColumnIndex(tile);

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (isValidTile(row+i, column+j))
                    if (!tiles[row+i][column+j].isRevealed()) {
                        if (tiles[row+i][column+j].getAdjacentBombs() != 0)
                            tiles[row+i][column+j].reveal("#eeeeee");
                        else revealAdjacentTiles(tiles[row+i][column+j]);
                    }
    }

    private void onGameWon() {
        for (Tile[] rows : tiles)
            for (Tile currentTile : rows)
                if (currentTile.isBomb())
                    currentTile.reveal("green");
    }

    private void onGameLost(Tile tile) {
        for (Tile[] rows : tiles)
            for (Tile currentTile : rows)
                if (currentTile.isBomb())
                    currentTile.reveal("yellow");

        tile.reveal("red");
    }

    private boolean isValidTile(int row, int column) {
        return row >= 0 && column >= 0 && row < gridHeight && column < gridWidth;
    }

}
