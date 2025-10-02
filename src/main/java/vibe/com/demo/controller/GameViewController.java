package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;
import vibe.com.demo.service.auth.AuthService;
import vibe.com.demo.service.game.GameProgressService;

public class GameViewController implements BaseController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private BorderPane rootPane;

    @FXML
    private StackPane overlay;

    @FXML
    private Label overlayTitle;

    @FXML
    private StackPane gameArea;

    @FXML
    private Label coinLabel;

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
    private AudioService audioService = ServiceLocator.getInstance().getAudioService();
    private AuthService authService = ServiceLocator.getInstance().getAuthService();
    private User currentUser = authService.getCurrentUser();
    private GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();

    // level da chon ~ dùng data binding
    private IntegerProperty selectedLevel = gameProgressService.getSelectedLevelProperty();
    private IntegerProperty totalCoins = new SimpleIntegerProperty(gameProgressService.getCoins(currentUser));

    @Override
    public void setMainApp(MainApp mainApp) {
        // TODO Auto-generated method stub
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        //data binding
        levelLabel.textProperty().bind(selectedLevel.asString());
        coinLabel.textProperty().bind(totalCoins.asString());
        // Hint: initialize() will be called when the associated FXML has been completely loaded.
        loadCurrentData();

        // xử lý sự kiên bàn phím : 
        gameArea.setFocusTraversable(true);
        gameArea.requestFocus();
        isStopState = false;
        gameArea.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (!isStopState) {
                    hideOverLay();
                } else {
                    showOverLay("Space để tiếp tục");
                }
            }
        });
    }
    private boolean isStopState;

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
        if (!isStopState) {
            hideOverLay();
        } else {
            showOverLay("Space để tiếp tục");
        }
    }

    public void hideOverLay() {
        isStopState = !isStopState;
        this.pauseButton.setText("⏸ Pause");
        this.overlay.setVisible(false);
    }

    public void showOverLay(String message) {
        isStopState = !isStopState;
        this.pauseButton.setText("▶ Tiếp tục");
        this.overlayTitle.setText(message);
        this.overlay.setVisible(true);
    }

    //lấy dữ liệu level mà người dùng đã chọn và dùng data binding cho label tương ứng 
    public void loadCurrentData() {
        // load hiệu ứng cho button next level 
        loadNextButton();
    }

    public void loadNextButton() {

        if (this.gameProgressService.isLockedNextButton(currentUser)) {
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
                gameProgressService.setSelectedLevel(this.selectedLevel.get() + 1);
                this.mainApp.loadGameView();
            }
            );
            delay.play();
        }
    }

}
