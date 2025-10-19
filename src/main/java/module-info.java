module vibe.com.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires java.sql;

    opens vibe.com.demo.controller to javafx.fxml; // Cho FXML load controller
    exports vibe.com.demo;
}
