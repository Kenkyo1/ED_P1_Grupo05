module ec.edu.espol.bscars {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.logging;
    requires de.jensd.fx.glyphs.fontawesome;
    
    opens ec.edu.espol.bscars to javafx.fxml;
    exports ec.edu.espol.bscars;
}
