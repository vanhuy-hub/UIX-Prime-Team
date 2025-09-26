package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.model.User;
import vibe.com.demo.service.AuthService;

public class LobbyController {

    private MainApp mainApp;//dùng đối tượng mainApp để có thể chuyển khung hình 
    private AuthService authService;
    private User currentUser;

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;
        authService = AuthService.getInstance();
        //tự động lấy USER hiện tại 
        loadCurrentuserData();
    }

    public void loadCurrentuserData() {
        currentUser = authService.getCurrentUser();
        if (currentUser != null) {
            System.out.println("Ten nguoi choi hien tai la: " + currentUser.getPlayerName());
            usernameLabel.setText(currentUser.getPlayerName());
        }

    }
    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutButton;

    @FXML
    public void logout(ActionEvent event) {
        currentUser = null;//đưa current về null

        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(150));//0.1s
            delay.setOnFinished(e -> this.mainApp.loadLoginView());
            delay.play();
        }
    }

    @FXML
    private void initialize() {
        // Hint: initialize() will be called when the associated FXML has been completely loaded.

    }
}
