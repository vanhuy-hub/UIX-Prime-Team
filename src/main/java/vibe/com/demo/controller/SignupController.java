package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.service.AuthService;

public class SignupController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField playerNameField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private VBox errorContainer;
    @FXML
    private Label errorMessageLabel;

    //MainApp~ View
    private MainApp mainApp;
    //AuthService ~ Checkout
    private AuthService authService;

    ///
    public SignupController() {
        authService = AuthService.getInstance();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private VBox signUpBox;

    @FXML
    public void switchToLoginView(ActionEvent event) {
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(150));//0.1s
            delay.setOnFinished(e -> this.mainApp.loadLoginView());
            delay.play();
        }
    }

    @FXML
    public void handleSignup(ActionEvent event) {
        String username = this.usernameField.getText().trim();
        String playerName = this.playerNameField.getText().trim();
        String password = this.passwordField.getText().trim();
        String confirmPassword = this.confirmPasswordField.getText().trim();
        if (!isValidateSignUp(username, playerName, password, confirmPassword)) {
            return;
        }
        if (!this.authService.signup(username, password)) {
            showError("Tên đăng nhập đã tồn tại ❌!");
            return;
        }
        //thỏa mãn hết điều kiện ~ lưu 
        showSuccess("Đăng kí thành công ✅!");

    }

    public boolean isValidateSignUp(String username, String playerName, String password, String confirmPassword) {

        if (!username.matches("^[a-zA-Z0-9]+$")) {//dùng biểu thức chính quy để kiểm tra
            // System.out.println("Loi user name");
            showError("Tên đăng nhập không phù hợp ❌!");
            return false;
        }
        if (playerName.isEmpty()) {
            showError("Tên trong game không được bỏ trống ❌!");
            return false;
        }
        if (!password.matches("^.{8,20}$")) {
            showError("Mật khẩu ít nhất có 8 ký tự ❌!");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showError("Mật khẩu nhập lại không khớp ❌!");
            return false;
        }
        return true;
    }

    public void showError(String errorMessage) {
        this.errorContainer.setManaged(true);
        this.errorContainer.setVisible(true);
        this.errorMessageLabel.setText(errorMessage);
    }

    public void showSuccess(String successMessage) {

        this.errorContainer.setManaged(true);
        this.errorContainer.setVisible(true);
        //thay đổi style 
        errorContainer.setStyle("-fx-background-color: #e8f5e8; -fx-border-color: #4caf50;");
        errorMessageLabel.setStyle("-fx-text-fill: #2e7d32;");
        this.errorMessageLabel.setText(successMessage);
        // Delay 1.5 giây để user đọc message trước khi chuyển trang
        PauseTransition successDelay = new PauseTransition(Duration.millis(1200));
        successDelay.setOnFinished(e -> {
            // Gọi switchToLoginView sau khi hiển thị message
            if (this.mainApp != null) {
                this.mainApp.loadLoginView();
            }
        });
        successDelay.play();
    }
}
