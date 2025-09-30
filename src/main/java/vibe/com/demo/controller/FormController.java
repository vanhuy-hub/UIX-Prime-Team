package vibe.com.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import vibe.com.demo.MainApp;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;
import vibe.com.demo.service.auth.AuthService;

public abstract class FormController implements BaseController {

    protected MainApp mainApp;
    protected AuthService authService = ServiceLocator.getInstance().getAuthService();
    protected AudioService audioManager = ServiceLocator.getInstance().getAudioService();
    @FXML
    protected VBox errorContainer;
    @FXML
    protected Label errorMessageLabel;

    //hàm khởi tạo phải dùng ở bên ngoài package khác nên public 
    public FormController() {

    }

    //setMainApp do dùng ở package khác nên cần khai báo public 
    @Override //ghi de tu BaseController
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
//====PHƯƠNG THỨC CHUNG CHO HIỂN THỊ MESSAGE ===== , ta chuyển về protected để chỉ lớp con dùng được 
    //showError và showSuccess chỉ dùng trong lớp con nên khai báo protected 

    protected void showError(String errorMessage) {
        this.errorContainer.setManaged(true);
        this.errorContainer.setVisible(true);
        this.errorMessageLabel.setText(errorMessage);
    }

    protected void showSuccess(String successMessage) {

        this.errorContainer.setManaged(true);
        this.errorContainer.setVisible(true);
        //thay đổi style 
        errorContainer.setStyle("-fx-background-color: #e8f5e8; -fx-border-color: #4caf50;");
        errorMessageLabel.setStyle("-fx-text-fill: #2e7d32;");
        this.errorMessageLabel.setText(successMessage);

    }

    // =======Phuong thuc trừu tượng cần ghi đè ở từng lớp con kế thừa 
    @FXML
    protected abstract void handleForm(ActionEvent event);
}
