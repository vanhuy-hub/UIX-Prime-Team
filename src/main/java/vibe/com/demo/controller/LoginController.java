package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.service.AuthService;

public class LoginController {

    //do tạo 1 hàm khởi tạo có tham số thì cũng  có hàm khởi tạo mặc định vì để tránh lỗi khởi tạo do từ file fxml sẽ chạy vào hàm khởi tạo mặc định của các controller  
    public LoginController() {
        //không khởi tạo đối tượng mới new nữa mà lấy thằng đối tượng chung của lớp  
        authService = AuthService.getInstance();
    }

    public void setMainApp(MainApp mainApp) {
        //cần hàm setMainApp vì ta muốn liên kết Ứng dụng hiẻn thị với controller để xử lý , chuyển giao diện 
        this.mainApp = mainApp;
    }

    private MainApp mainApp;
    private AuthService authService;
    @FXML
    private VBox loginBox;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button switchToSignUpButton;

    @FXML
    private VBox errorContainer;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private void switchToSignUpView(ActionEvent event) {
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(130));
            delay.setOnFinished(e -> this.mainApp.loadSignUpView());
            delay.play();
        }
    }

    @FXML
    private void handleLogin() {
        String username = this.usernameField.getText().trim();
        String password = this.passwordField.getText().trim();
        if (!isValidateLogin(username, password)) {
            return;
        }
        if (!this.authService.login(username, password)) {
            showError("Tên đăng nhập hoặc mật khẩu không đúng ❌!");
            return;
        }
        showSuccess("Đăng nhập thành công ✅, đang chuyển hướng...");
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

        });
        successDelay.play();
    }
}
