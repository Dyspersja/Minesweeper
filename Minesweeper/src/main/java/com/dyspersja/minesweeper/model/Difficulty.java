package com.dyspersja.minesweeper.model;

public enum Difficulty {
    BEGINNER("Beginner",8),
    INTERMEDIATE("Intermediate",7),
    EXPERT("Expert",6);

    private final String label;
    private final int bombDensityFactor;

    Difficulty(String label, int bombDensityFactor) {
        this.label = label;
        this.bombDensityFactor = bombDensityFactor;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getBombDensityFactor() {
        return bombDensityFactor;
    }
}