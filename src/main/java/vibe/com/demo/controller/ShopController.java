package vibe.com.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import vibe.com.demo.MainApp;

public class ShopController implements BaseController {

    private MainApp mainApp;
    @FXML
    private Button btnBack;

    @FXML
    private HBox coinBox;

    @FXML
    private Label coinLabel;

    @FXML
    private void initialize() {
        // Hint: initialize() will be called when the associated FXML has been completely loaded.

    }

    @Override
    public void setMainApp(MainApp mainApp) {
        // TODO Auto-generated method stub
        this.mainApp = mainApp;
    }

    @FXML
    public void handleBackButton() {
        this.mainApp.loadLobbyView();
    }
}
