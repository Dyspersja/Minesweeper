package com.dyspersja.minesweeper.gamescreen;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameScreenInitializer {

    private final GridPane minefieldGridPane;

    public GameScreenInitializer(GridPane minefieldGridPane) {
        this.minefieldGridPane = minefieldGridPane;
    }

    public void clearGridConstraints() {
        minefieldGridPane.getRowConstraints().clear();
        minefieldGridPane.getColumnConstraints().clear();
    }

    public void addRowConstraints(int count) {
        RowConstraints row = new RowConstraints();
        row.setMinHeight(20);
        for (int i = 0; i < count; i++) {
            minefieldGridPane.getRowConstraints().add(row);
        }
    }

    public void addColumnConstraints(int count) {
        ColumnConstraints column = new ColumnConstraints();
        column.setMinWidth(20);
        for (int i = 0; i < count; i++) {
            minefieldGridPane.getColumnConstraints().add(column);
        }
    }

    public void applyGridPaneStyles() {
        minefieldGridPane.setStyle(
                "-fx-border-color: #666666;"
        );
    }
}
