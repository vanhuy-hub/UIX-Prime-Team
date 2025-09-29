package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.audio.AudioManager;
import vibe.com.demo.model.User;
import vibe.com.demo.service.AuthService;

public class LobbyController implements BaseController {

    @FXML
    private Label coinLabel;

    @FXML
    private Label trophyLabel;

    @FXML
    private Button playButton;

    @FXML
    private Button shopButton;

    @FXML
    private Button rankButton;

    @FXML
    private Button helpButton;

    @FXML
    private Label avatarLabel;

    @FXML
    private Button profileButton;

    private MainApp mainApp;//dùng đối tượng mainApp để có thể chuyển khung hình 
    private AuthService authService;
    private User currentUser;
    private AudioManager audioManager;

    @Override // ghi de ham tu BaseController
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    //hàm để load tên trong game của người vừa đăng nhập / tạo tài khoản 
    public void loadCurrentuserData() {
        // set playerName
        currentUser = authService.getCurrentUser();
        if (currentUser != null) {
            System.out.println("Ten nguoi choi hien tai la: " + currentUser.getPlayerName());
            usernameLabel.setText(currentUser.getPlayerName());
        }
        //set trophi=countOfCompletedLevel
        int trophi = authService.getCompletedLevels();
        trophyLabel.setText("🏆 " + trophi);

    }
    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutButton;

    @FXML
    public void logout(ActionEvent event) {
        // play click sound
        audioManager.playSoundEffect("clicksound");
        // load login view
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
        audioManager = AudioManager.getInstance();
        authService = AuthService.getInstance();
        //tự động lấy USER hiện tại 
        loadCurrentuserData();
    }

    @FXML
    public void openGameHelp() {
        System.out.println("Open game help");
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(50));
            delay.setOnFinished(e -> this.mainApp.loadGameHelpView());
            delay.play();
        }
    }

    @FXML
    public void openLevelMenu() {
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(50));
            delay.setOnFinished(e -> this.mainApp.loadLevelMenuView());
            delay.play();
        }
    }
}
