package com.dyspersja.minesweeper.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Tile extends Label {
    boolean isRevealed;
    boolean isBomb;

    public Tile() {
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setAlignment(Pos.CENTER);

        this.applyStyle("#aaaaaa");
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public void reveal(String symbol, String color) {
        this.setText(symbol);
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
