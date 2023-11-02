module com.dyspersja.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dyspersja.minesweeper to javafx.fxml;
    exports com.dyspersja.minesweeper;
    exports com.dyspersja.minesweeper.welcomescreen;
    opens com.dyspersja.minesweeper.welcomescreen to javafx.fxml;
    exports com.dyspersja.minesweeper.gamescreen;
    opens com.dyspersja.minesweeper.gamescreen to javafx.fxml;
    exports com.dyspersja.minesweeper.endscreen;
    opens com.dyspersja.minesweeper.endscreen to javafx.fxml;
    exports com.dyspersja.minesweeper.welcomescreen.incorrectinput;
    opens com.dyspersja.minesweeper.welcomescreen.incorrectinput to javafx.fxml;
}