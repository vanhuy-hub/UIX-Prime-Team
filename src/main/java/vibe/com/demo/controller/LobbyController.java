package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;
import vibe.com.demo.service.auth.AuthService;
import vibe.com.demo.service.game.GameProgressService;

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

    private AudioService audioService = ServiceLocator.getInstance().getAudioService();
    private AuthService authService = ServiceLocator.getInstance().getAuthService();
    private User currentUser = authService.getCurrentUser();
    private GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();

    @Override // ghi de ham tu BaseController

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    //hàm để load tên trong game của người vừa đăng nhập / tạo tài khoản 
    public void loadCurrentuserData() {
        // set playerName
        System.out.println(currentUser.getPlayerProgress());
        if (currentUser != null) {
            System.out.println("Ten nguoi choi hien tai la: " + currentUser.getPlayerName());
            usernameLabel.setText(currentUser.getPlayerName());
        }
        //set trophi
        int trophi = gameProgressService.getTrophies(currentUser);
        trophyLabel.setText("🏆 " + trophi);

        //set coins
        int coins = gameProgressService.getCoins(currentUser);
        coinLabel.setText("💰 " + coins);
    }
    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutButton;

    @FXML
    public void logout(ActionEvent event) {
        // play click sound
        audioService.playSoundEffect("clicksound");
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

        System.out.println(currentUser);
        // Hint: initialize() will be called when the associated FXML has been completely loaded.

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
