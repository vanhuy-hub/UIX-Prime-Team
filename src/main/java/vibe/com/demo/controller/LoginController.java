package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;
import vibe.com.demo.service.auth.AuthService;

public class LoginController extends FormController {

    @FXML
    private VBox loginBox;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    private AudioService audioService = ServiceLocator.getInstance().getAudioService();
private AuthService authService = ServiceLocator.getInstance().getAuthService();
    @FXML
    private void switchToSignUpView(ActionEvent event) {
        //play am thanh click
        audioService.playSoundEffect("clicksound");
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(130));
            delay.setOnFinished(e -> this.mainApp.loadSignUpView());
            delay.play();
        }
    }

    public boolean isValidateLogin(String username, String password) {

        if (username.length() == 0) {//dùng biểu thức chính quy để kiểm tra
            // System.out.println("Loi user name");
            showError("Tên đăng nhập không hợp lệ ❌!");
            return false;
        }

        if (password.length() == 0) {
            showError("Mật khẩu không hợp lệ ❌!");
            return false;
        }
        return true;
    }

    @Override
    @FXML
    protected void handleForm(ActionEvent event) {
        //play am thanh click
        audioService.playSoundEffect("clicksound");
        // TODO Auto-generated method stub
        String username = this.usernameField.getText().trim();
        String password = this.passwordField.getText().trim();
        if (!isValidateLogin(username, password)) {
            return;
        }

        if (!this.authService.login(username, password)) {
            showError("Tên đăng nhập hoặc mật khẩu không đúng ❌!");
            return;
        }

        //Lấy index của User vừa đăng nhập để truyền vào loadLobbyView 
        // Delay 1.5 giây để user đọc message trước khi chuyển trang
        loginButton.setText("ĐANG ĐĂNG NHẬP ...");
        loginButton.setDisable(true);//vô hiệu hóa bằng việc setDisable 
        showSuccess("Đăng nhập thành công ✅");
        PauseTransition successDelay = new PauseTransition(Duration.millis(1500));
        successDelay.setOnFinished(e -> {
            //chuyển loginButton về trạng thái đầu
            loginButton.setText("ĐĂNG NHẬP");
            loginButton.setDisable(false);
            this.mainApp.loadLobbyView();
            //chuyển cảnh 

        });
        successDelay.play();
    }

}
