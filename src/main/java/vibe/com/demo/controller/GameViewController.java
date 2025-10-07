package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.game.core.GameDataModel;
import vibe.com.demo.game.core.GameManager;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;
import vibe.com.demo.service.auth.AuthService;
import vibe.com.demo.service.game.GameProgressService;

public class GameViewController implements BaseController {

    @FXML
    private Button changePaddleBtn;

    @FXML
    private Canvas gameCanvas;

    @FXML
    private BorderPane rootPane;

    @FXML
    private StackPane gameArea;

    @FXML
    private Label coinLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private HBox livesBox;

    @FXML
    private Button menuButton;

    @FXML
    private Button nextButton;

    //các service liên quan 
    private MainApp mainApp;
    private AudioService audioService = ServiceLocator.getInstance().getAudioService();
    private AuthService authService = ServiceLocator.getInstance().getAuthService();
    private User currentUser = authService.getCurrentUser();
    private GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();

    //Đôi tượng quản lý game session : 
    private GameManager gameManager;
    private GraphicsContext renderer;
    private GameDataModel gameDataModel;

    // level da chon ~ dùng data binding
    private IntegerProperty selectedLevel = gameProgressService.getSelectedLevelProperty();
    private IntegerProperty totalCoins = new SimpleIntegerProperty(gameProgressService.getCoins(currentUser));

    @Override
    public void setMainApp(MainApp mainApp) {
        // TODO Auto-generated method stub
        this.mainApp = mainApp;
    }

    /**
     * Hàm khởi tạo các ràng buộc dữ liệu
     */
    public void dataBindingInit() {
        //data binding
        levelLabel.textProperty().bind(selectedLevel.asString());
        coinLabel.textProperty().bind(totalCoins.asString());
        //databinding + listener (khi có sự thay đổi dữ liệu thì tự động gọi hàm được định nghĩa )
        gameDataModel.getSessionLivesProperty().addListener((obs, oldVal, newVal) -> {
            playLifeLostAnimation();
            updateLivesDisplay(newVal.intValue());
        });
        //binding + listener khi giá trị boolean isUnclock ở gameData thay đổi từ false sang true  
        gameDataModel.nextLevelUnlockedProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(newVal.booleanValue());
            if (newVal) {
                unlockNextButton();
            }

        });
    }

    @FXML
    private void initialize() {

        //hàm khởi tạo gameSession 
        initializeGameSession();
        //hàm setup khi gõ phím 
        setUpKeyHandles();
        //load phần nằm ngoài game chính 
        dataBindingInit();
        loadNextButtonEffect();

    }

    /**
     * Hàm khởi tạo phiên chơi game
     */
    public void initializeGameSession() {
        //Để có thể vẽ lên renderer của canvas , ta cần lấy được đối tượng graphicsContext của nó : 
        renderer = gameCanvas.getGraphicsContext2D();
        //Lấy chiều dài và chiều rộng gameArea 
        double gameWidth = gameCanvas.getWidth();
        double gameHeight = gameCanvas.getHeight();
        //init 
        gameManager = new GameManager(renderer, gameWidth, gameHeight);
        gameDataModel = gameManager.getGameDataModel();
    }

    /**
     * Hàm xử lý sự kiện ấn bàn phím
     */
    public void setUpKeyHandles() {
        //Cần focus vào gameArea ~ vì đó là thằng chịu tác động, có nghĩa là có những thay đổi khi ta ấn bán phím thì đều là thay đổi trên thằng gameArea 
        gameCanvas.setFocusTraversable(true);//set sự thay đổi chỉ tập trung vào gameCanvas ~ canvas để vẽ đối tượng 
        gameCanvas.requestFocus();//chấp nhận focus

        // ✅ THÊM FOCUS HANDLER
        gameArea.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                System.out.println("🎯 GameArea focused - Ready for input");
            }
        });
        //sự kiện ấn phím 
        gameCanvas.setOnKeyPressed(e -> {

            this.gameManager.handleKeyPressed(e.getCode().toString());
            e.consume();//phương thức consume để tránh sự kiện bị nổi bọt event bubbling 
        });

        //sự kiện nhả phím ~ released 
        gameCanvas.setOnKeyReleased(e -> {
            gameManager.handleKeyReleased(e.getCode().toString());
            e.consume();//phương thức consume để tránh sự kiện bị nổi bọt event bubbling 
        });
    }

    @FXML
    public void handleChangePaddle() {
        this.mainApp.loadLobbyView();
    }

    @FXML
    public void backToLevelMenu() {
        if (this.mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(150));//0.1s
            delay.setOnFinished(e -> this.mainApp.loadLevelMenuView());
            delay.play();
        }
    }

    /**
     * Load hiệu ứng cho button next-level
     */
    public void loadNextButtonEffect() {

        if (this.gameProgressService.isLockedNextButton(currentUser)) {
            lockNextButton();
        } else {
            unlockNextButton();
        }
    }

    /**
     * Unlock nextButton
     */
    public void lockNextButton() {
        nextButton.setDisable(true);
        nextButton.setText("🔒 Complete this level");
        nextButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("unlocked"), false);
        nextButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("locked"), true);
    }

    /**
     * Lock nextButton
     */
    public void unlockNextButton() {
        nextButton.setDisable(false);
        nextButton.setText("Next Level ->");
        nextButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("locked"), false);
        nextButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("unlocked"), true);
    }

    /**
     * Hàm xử lý sự kiện khi ấn button next level
     */
    @FXML
    public void handleNextLevel() {
        if (!nextButton.isDisable()) {
            System.out.println("Khong bi khoa");
            nextButton.setText("Next Level ...");
            nextButton.setDisable(true);
            this.gameManager.togglePauseGame();
            this.gameManager.showOverlay("Đang chuyển tiếp ...");
            PauseTransition delay = new PauseTransition(Duration.millis(800));
            delay.setOnFinished(e -> {
                gameProgressService.setSelectedLevel(this.selectedLevel.get() + 1);
                this.mainApp.loadGameView();
            }
            );
            delay.play();
        }
    }

    /**
     * Cập nhật số mạng
     */
    public void updateLivesDisplay(int lives) {
        livesBox.getChildren().clear();//xóa tất cả heartItem đi 
        for (int i = 1; i <= lives; i++) {
            Label heart = new Label("❤");
            heart.getStyleClass().add("life");
            livesBox.getChildren().add(heart);
        }

        //hiển thị mất mạng ~ trái tim không màu 
        for (int i = 3; i > lives; i--) {
            Label emptyHeart = new Label("❤");
            emptyHeart.getStyleClass().add("life-empty");
            livesBox.getChildren().add(emptyHeart);
        }
    }

    /**
     * Hiệu ứng mất mạng
     */
    public void playLifeLostAnimation() {
        // Thêm hiệu ứng rung hoặc flash cho livesBox
        livesBox.getStyleClass().add("life-lost-animation");

        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(e -> livesBox.getStyleClass().remove("life-lost-animation"));
        pause.play();
    }

}
