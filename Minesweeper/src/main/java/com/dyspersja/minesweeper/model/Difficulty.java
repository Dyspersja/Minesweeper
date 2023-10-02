package com.dyspersja.minesweeper.model;

public enum Difficulty {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    EXPERT("Expert");

    private final String label;

    Difficulty(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}