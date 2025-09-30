package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.beans.property.IntegerProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.audio.AudioManager;
import vibe.com.demo.service.AuthService;

public class GameViewController implements BaseController {

    @FXML
    private AnchorPane gameArea;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private HBox livesBox;

    @FXML
    private Button pauseButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button nextButton;

    private MainApp mainApp;
    private AudioManager audioManager;
    private AuthService authService;

    // level da chon ~ dùng data binding
    private IntegerProperty selectedLevel;
    private IntegerProperty totalCoins;

    @Override
    public void setMainApp(MainApp mainApp) {
        // TODO Auto-generated method stub
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        // gán giá trị cho authService và selectedLevel (property) 
        authService = AuthService.getInstance();
        selectedLevel = authService.getSelectedLevel();//data binding ~ là giá trị ràng buộc  để label sẽ thay đổi theo
        // Hint: initialize() will be called when the associated FXML has been completely loaded.
        loadCurrentData();
    }

    @FXML
    public void backToLevelMenu() {
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(150));//0.1s
            delay.setOnFinished(e -> this.mainApp.loadLevelMenuView());
            delay.play();
        }
    }

    @FXML
    public void pauseGame() {

    }

    //lấy dữ liệu level mà người dùng đã chọn và dùng data binding cho label tương ứng 
    public void loadCurrentData() {

        System.out.println(authService.getSelectedLevel());
        levelLabel.textProperty().bind(selectedLevel.asString());
        // số tiền hiện tại 

        // load hiệu ứng cho button next level 
        loadNextButton();
    }

    public void loadNextButton() {

        if (authService.isLockedNextButton()) {
            nextButton.setDisable(true);
            nextButton.setText("🔒 Complete this level");
            nextButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("unlocked"), false);
            nextButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("locked"), true);
        } else {
            nextButton.setDisable(false);
            nextButton.setText("Next Level ->");
            nextButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("locked"), false);
            nextButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("unlocked"), true);
        }
    }

    @FXML
    public void handleNextLevel() {
        if (!nextButton.isDisable()) {
            System.out.println("Khong bi khoa");
            nextButton.setText("Next Level ...");
            nextButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.millis(800));
            delay.setOnFinished(e -> {
                authService.setSelectedLevel(this.selectedLevel.get() + 1);
                loadCurrentData();
            }
            );
            delay.play();
        }
    }
}
