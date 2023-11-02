package com.dyspersja.minesweeper.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Tile extends Label {

    private boolean isRevealed;
    private boolean isBomb;
    private int adjacentBombs;

    public Tile() {
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setAlignment(Pos.CENTER);

        this.applyStyle(TileStyle.HIDDEN);
    }

    public boolean isRevealed() {
        return this.isRevealed;
    }

    public boolean isBomb() {
        return this.isBomb;
    }

    public int getAdjacentBombs() {
        return this.adjacentBombs;
    }

    public void setBomb(boolean bomb) {
        this.isBomb = bomb;
    }

    public void incrementAdjacentBombs() {
        this.adjacentBombs += 1;
    }

    public void reveal(TileStyle style) {
        this.isRevealed = true;

        if (this.isBomb) this.setText("X");
        else if (this.adjacentBombs != 0)
            this.setText(Integer.toString(this.adjacentBombs));

        this.applyStyle(style);
        this.removeOnMouseClickAction();
    }

    private void applyStyle(TileStyle style) {
        this.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-background-color: " + style + ";" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 1px;"
        );
    }

    private void removeOnMouseClickAction() {
        this.setOnMouseClicked(null);
    }
}
