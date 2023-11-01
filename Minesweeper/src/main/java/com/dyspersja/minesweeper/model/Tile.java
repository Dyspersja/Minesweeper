package com.dyspersja.minesweeper.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Tile extends Label {
    public Tile() {
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setAlignment(Pos.CENTER);
    }

    public void reveal(String symbol, String color) {
        this.setText(symbol);
        this.applyStyle(color);
        this.removeOnMouseClickAction();
    }

    public void applyStyle(String color) {
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
