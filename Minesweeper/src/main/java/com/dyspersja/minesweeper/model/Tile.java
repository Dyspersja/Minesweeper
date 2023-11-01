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

        this.applyStyle("#aaaaaa");
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

    public void reveal(String color) {
        this.isRevealed = true;

        if (this.isBomb) this.setText("X");
        else if (this.adjacentBombs != 0)
            this.setText(Integer.toString(this.adjacentBombs));

        this.applyStyle(color);
        this.removeOnMouseClickAction();
    }

    private void applyStyle(String color) {
        this.setStyle(
                "-fx-font-weight: bold;" +
                "-fx-background-color: " + color + ";" +
                "-fx-border-color: #666666;" +
                "-fx-border-width: 1px;"
        );
    }

    private void removeOnMouseClickAction() {
        this.setOnMouseClicked(null);
    }
}
