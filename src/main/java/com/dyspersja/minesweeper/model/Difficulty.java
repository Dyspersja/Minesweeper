package com.dyspersja.minesweeper.model;

public enum Difficulty {
    BEGINNER("Beginner",11),
    INTERMEDIATE("Intermediate",9),
    EXPERT("Expert",7);

    private final String label;
    private final int bombDensityFactor;

    Difficulty(String label, int bombDensityFactor) {
        this.label = label;
        this.bombDensityFactor = bombDensityFactor;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public int getBombDensityFactor() {
        return this.bombDensityFactor;
    }
}