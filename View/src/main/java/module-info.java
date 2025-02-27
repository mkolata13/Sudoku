module pl.comp.view {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires sudoku;
    requires org.slf4j;
    requires java.logging;

    opens pl.comp.view to javafx.fxml;
    exports pl.comp.view;
}