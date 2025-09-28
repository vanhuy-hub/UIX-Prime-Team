package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.util.Duration;
import vibe.com.demo.MainApp;

public class GameHelpController implements BaseController {

    private MainApp mainApp;

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void backToLobbyView() {
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(150));
            delay.setOnFinished(e -> this.mainApp.loadLobbyView());
            delay.play();
        }
    }

}
