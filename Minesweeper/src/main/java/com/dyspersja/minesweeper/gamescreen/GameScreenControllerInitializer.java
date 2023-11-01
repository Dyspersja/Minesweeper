package com.dyspersja.minesweeper.gamescreen;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameScreenControllerInitializer {
    public void clearGridConstraints(GridPane minefieldGridPane) {
        minefieldGridPane.getRowConstraints().clear();
        minefieldGridPane.getColumnConstraints().clear();
    }

    public void addRowConstraints(GridPane minefieldGridPane, int count) {
        RowConstraints row = new RowConstraints();
        row.setMinHeight(20);
        for (int i = 0; i < count; i++) {
            minefieldGridPane.getRowConstraints().add(row);
        }
    }

    public void addColumnConstraints(GridPane minefieldGridPane, int count) {
        ColumnConstraints column = new ColumnConstraints();
        column.setMinWidth(20);
        for (int i = 0; i < count; i++) {
            minefieldGridPane.getColumnConstraints().add(column);
        }
    }

    public void applyGridPaneStyles(GridPane minefieldGridPane) {
        minefieldGridPane.setStyle(
                "-fx-border-color: #666666;"
        );
    }
}
