module com.dyspersja.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dyspersja.minesweeper to javafx.fxml;
    exports com.dyspersja.minesweeper;
}