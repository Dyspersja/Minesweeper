package com.dyspersja.minesweeper.model;

public enum TileStyle {
    HIDDEN("#aaaaaa"),
    REVEALED("#eeeeee"),
    BOMB_LOST("#ffff00"),
    BOMB_LOST_STRUCK("#ff0000"),
    BOMB_WON("#00ff00");

    private final String color;

    TileStyle(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.color;
    }
}
