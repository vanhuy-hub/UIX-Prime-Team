package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class SignupController extends FormController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField playerNameField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button registerButton;

    @FXML
    public void switchToLoginView(ActionEvent event) {
        //play am thanh click
        audioManager.playSoundEffect("clicksound");
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(150));//0.1s
            delay.setOnFinished(e -> this.mainApp.loadLoginView());
            delay.play();
        }
    }

    @Override
    @FXML
    public void handleForm(ActionEvent event) {
        //play am thanh click
        audioManager.playSoundEffect("clicksound");
        //xu li input 
        String username = this.usernameField.getText().trim();
        String playerName = this.playerNameField.getText().trim();
        String password = this.passwordField.getText().trim();
        String confirmPassword = this.confirmPasswordField.getText().trim();
        if (!isValidateSignUp(username, playerName, password, confirmPassword)) {
            return;
        }
        if (this.authService.isUsernameExists(username)) {
            showError("Tên đăng nhập đã tồn tại ❌!");
            return;
        }
        if (this.authService.isPlayerNameExists(playerName)) {
            showError("Tên trong game đã có người dùng ❌!");
            return;
        }

        //thỏa mãn hết điều kiện ~ lưu 
        this.authService.addUser(username, password, playerName);
        // Thỏa mãn hết điều kiện ~ lưu 
        {
            registerButton.setText("ĐANG TẠO TÀI KHOẢN...");
        }
        registerButton.setDisable(true); // Vô hiệu hóa button trong khi xử lý

        // Bước 1: Hiển thị success message NGAY LẬP TỨC
        showSuccess("Đăng kí thành công ✅!");

        // Bước 2: Delay 1.2 giây để user đọc message, sau đó chuyển trang
        PauseTransition switchDelay = new PauseTransition(Duration.millis(1200));
        switchDelay.setOnFinished(e -> {
            // Reset button text trước khi chuyển trang
            registerButton.setText("ĐĂNG KÝ");
            registerButton.setDisable(false);
            this.mainApp.loadLobbyView();
        });
        switchDelay.play();
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

}
