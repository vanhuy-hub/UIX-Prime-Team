package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import vibe.com.demo.MainApp;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;
import vibe.com.demo.service.auth.AuthService;
import vibe.com.demo.service.game.GameProgressService;

public class LevelMenuController implements BaseController {

    // ---------------- FXML fields ----------------
    @FXML
    private Label completeLabel;

    @FXML
    private Button backButton;

    @FXML
    private GridPane levelGrid;

    // ---------------- Internal fields ----------------
    private MainApp mainApp;
    private AudioService audioService = ServiceLocator.getInstance().getAudioService();
    private AuthService authService = ServiceLocator.getInstance().getAuthService();
    private User currentUser = authService.getCurrentUser();
    private GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();

    private int totalLevels;
    private int completedLevels;

    private static final int LEVELS_PER_ROW = 5; // mỗi hàng có 5 level

    // ---------------- Lifecycle ----------------
    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        totalLevels = 20;
        completedLevels = gameProgressService.getTrophies(currentUser);
        renderLevelGrid();
        updateCompleteLabel();
    }

    // ---------------- Event handlers ----------------
    @FXML
    public void backToMainMenu() {
        if (mainApp != null) {
            PauseTransition delay = new PauseTransition(Duration.millis(50));
            delay.setOnFinished(e -> mainApp.loadLobbyView());
            delay.play();
        }
    }

    // ---------------- Business logic ----------------
    private void renderLevelGrid() {
        int row = 0, col = 0;

        for (int i = 1; i <= totalLevels; i++) {
            Button levelItem = new Button();
            levelItem.getStyleClass().add("level-button");

            if (i <= completedLevels + 1) {
                levelItem.setText(String.valueOf(i));
                levelItem.getStyleClass().add(
                        (i == completedLevels + 1) ? "playable" : "completed"
                );
                //load gameview khi select level 
                levelItem.setOnAction(e -> handleOpenGameView(Integer.valueOf(levelItem.getText())));
                // ...
            } else {
                levelItem.setText("🔒");
                levelItem.getStyleClass().add("locked");
            }

            levelGrid.add(levelItem, col, row);

            // update col/row
            col++;
            if (col == LEVELS_PER_ROW) {
                col = 0;
                row++;
            }

        }
    }

    //mở gameview khi chọn level 
    public void handleOpenGameView(int level) {
        this.gameProgressService.setSelectedLevel(level);
        this.mainApp.loadGameView();
    }

    private void updateCompleteLabel() {
        completeLabel.setText("Hoàn thành: " + completedLevels + "/" + totalLevels);
    }

    // ---------------- Getter/Setter ----------------
    public int getTotalLevels() {
        return totalLevels;
    }

    public void setTotalLevels(int totalLevels) {
        this.totalLevels = totalLevels;
    }

    public int getCompletedLevels() {
        return completedLevels;
    }

    public void setCompletedLevels(int completedLevels) {
        this.completedLevels = completedLevels;
    }
}
