package vibe.com.demo.game.core;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import vibe.com.demo.game.animations.AnimationManager;
import vibe.com.demo.game.levels.LevelManager;
import vibe.com.demo.game.objects.entities.ball.BallManager;
import vibe.com.demo.game.objects.entities.overlay.OverlayObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.game.objects.entities.powerups.PowerUpManager;
import vibe.com.demo.game.utils.GameConstants;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;
import vibe.com.demo.service.game.GameProgressService;
//lớp GameManager quản lý tất cả các đối tương trong game session 

public class GameManager {

    private Paddle paddle;

    private BallManager ballManager;
    private GameState gameState;

    private GraphicsContext gc;
    private OverlayObject overlay;
    private AnimationManager animationManager;
    private Renderer renderer;
    private GameEngine gameEngine;
    private LevelManager levelManager;//quản lý level , thằng List<Brick>do ông này quản lý 
    private GameDataModel gameDataModel;//quản lý dữ liệu trong gameSession (lives , coinEarned )
    private PowerUpManager powerUpManager;

    private double gameWidth;
    private double gameHeight;

    // Dịch vụ
    private final GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();
    private final AudioService audioService = ServiceLocator.getInstance().getAudioService();
    private final User currentUser = ServiceLocator.getInstance().getAuthService().getCurrentUser();

    //constructor 
    public GameManager(GraphicsContext gc, double gameWidth, double gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.gc = gc;
        init();
    }

    /**
     * GameState ~ Trạng thái của game : là kiểu enum vì chỉ có vài trạng thái
     * nhất định
     */
    public enum GameState {
        PLAYING,
        PAUSED,
        GAME_OVER,
        GAME_WIN
    }

    public void init() {
        animationManager = new AnimationManager();
        powerUpManager = new PowerUpManager();
        gameEngine = new GameEngine(this);
        renderer = new Renderer(gc, gameWidth, gameHeight);
        gameDataModel = new GameDataModel();
        this.levelManager = new LevelManager(gameProgressService.getSelectedLevel(), gameWidth);
        initializeGameObjects();
        //truyền paddle và ball vào setGameObject của gameEngine  
        this.gameEngine.setGameObjects(paddle, ballManager, levelManager.getCurrentBricks());
        //render lần đầu 
        render();

    }

    // /**
    //  * Khởi tạo lại map, lưu ý cần phải xóa hết phần tử cũ trong bricks trước
    //  * rồi mới addAll , không sẽ bị sai khi restartGame , (nghĩa là dữ liệu
    //  * bricks cũ vẫn còn , rồi nó add thêm map mới lấy từ LevelDesign)
    //  */
    // public void initializeLevel() {
    //     levelManager.resetCurrentLevel();//phải reset lại (giống như clear)
    // }
    /**
     * Khởi tạo vị trí các phần tử : ball, paddle, overlay
     */
    public void initializeGameObjects() {
        // Initialize paddle at bottom center
        double paddleWidth = GameConstants.PADDLE_NORMAL_WIDTH;
        double paddleHeight = GameConstants.PADDLE_NORMAL_HEIGHT;
        double paddleX = (gameWidth - paddleWidth) / 2;
        double paddleY = gameHeight - paddleHeight - 30;
        paddle = new Paddle(paddleX, paddleY, GameConstants.PADDLE_NORMAL_WIDTH, GameConstants.PADDLE_NORMAL_HEIGHT);
        //Lấy paddle của người chơi .
        paddle.setImgFromURL(gameProgressService.getCurrentPaddleImageURL(currentUser));
        // Initialize ball on top of paddle
        ballManager = new BallManager(paddle);
        //OverlayInit
        overlay = new OverlayObject(0, 0, gameWidth, gameHeight);
        showOverlay("Nhấn SPACE để bắt đầu");

        PauseTransition pause = new PauseTransition(Duration.millis(1500));
        pause.setOnFinished(e -> {
            this.hideOverlay();
            gameState = GameState.PLAYING;
            gameEngine.startGameLoop();
            powerUpManager.startTimer();
        });
        pause.play();
    }

    /**
     * Vẽ các đối tượng trong game.
     */
    public void render() {
        renderer.render(ballManager, paddle, overlay, levelManager.getCurrentBricks(), animationManager, powerUpManager);
    }

    /**
     * Hàm xử lý khi mất bóng .
     */
    public void handleBallLost() {
        this.gameDataModel.decreaseSessionLives();//gọi hàm giảm mạng sống 
        if (this.gameDataModel.getSessionLives() == 0) {
            if (!this.gameDataModel.isLost()) {
                this.gameDataModel.setLost(true);
                this.audioService.playSoundEffect("lose");
                delayPaddleDisappear(500);
                delayShowOverlay("Nhấn R để chơi lại", 1000, GameState.GAME_OVER);
            }
        } else {//dừng game 
            ballManager.setIsActive(false);
            ballManager.addBallAtPaddle(paddle);

        }
    }

    /**
     * Restart game : dừng vòng lặp game cũ để tránh bị lỗi Multiple Game loops
     * và khởi tạo lại game mới
     *
     */
    public void restartGame() {
        clear();
        init();
    }

    // --- Dọn dẹp tài nguyên (gọi khi restart hoặc thoát game) ---
    public void clear() {
        gameEngine.stopGameLoop();
        powerUpManager.clear();
        animationManager.clear();

    }

    /**
     * Hàm xử lý sự kiện khi win game
     */
    public void handleLevelComplete() {
        if (!this.gameDataModel.isWin()) {
            //Lý do phải có if vì đợi 1 thời gian sau thì mới hiển thị win , trong lúc ấy update vẫn chạy vào hàm này .
            this.gameDataModel.setWon();
            this.audioService.playSoundEffect("win");
            delayShowOverlay("Chúc mừng bạn đã hoàn thành level " + this.levelManager.getCurrentLevel(), 500, GameState.GAME_WIN);
            this.gameProgressService.completeLevel(currentUser, levelManager.getCurrentLevel());
        }
    }

    public void delayShowOverlay(String message, long ms, GameState gameState) {
        PauseTransition pause = new PauseTransition(Duration.millis(ms));
        pause.setOnFinished(e -> {
            animationManager.stopAll();
            this.showOverlay(message);
            this.gameState = gameState;

        });
        pause.play();
    }

    public void showOverlay(String message) {
        overlay.show(message, Color.WHITE);
        render();
    }

    public void hideOverlay() {
        overlay.hide();
    }

    /**
     * Xử lý paddle biến mất .
     */
    public void delayPaddleDisappear(long ms) {
        PauseTransition pause = new PauseTransition(Duration.millis(ms));
        pause.setOnFinished(e -> {
            paddle.disappear();
        });
        pause.play();
    }

    /**
     * Hàm toggle ~ công tắc chuyển đổi trạng thái trong game
     */
    public void togglePauseGame() {
        if (gameState == GameState.PLAYING && ballManager.isActive()) {
            showOverlay("Nhấn SPACE để tiếp tục");
            gameState = GameState.PAUSED;
            powerUpManager.stopTimer();
        } else if (gameState == GameState.PLAYING && !ballManager.isActive()) {//khi bị mất bóng  nhưng vẫn còn mạng 
            ballManager.setIsActive(true);
            ballManager.start();
        } else if (gameState == GameState.PAUSED) {
            hideOverlay();
            gameState = GameState.PLAYING;
        }
    }

    /**
     * Hàm xử lý khi ấn phím : để paddle di chuyển
     */
    public void handleKeyPressed(String code) {
        switch (code) {
            case "RIGHT" -> {
                this.paddle.moveRight();
            }
            case "LEFT" ->
                this.paddle.moveLeft();
            case "SPACE" -> {
                togglePauseGame();
            }
            case "R" -> {
                restartGame();
            }
        }
    }

    /**
     * Hàm releasedKey (nhả phím) ~ kết hợp cùng hàm keyPressed (đè phím )=>để
     * làm tăng độ mượt khi paddle di chuyển
     */
    public void handleKeyReleased(String code) {
        switch (code) {
            case "LEFT" ->
                paddle.stopLeft();
            case "RIGHT" ->
                paddle.stopRight();
        }
    }

    /**
     * StopGameLoop
     */
    public void stopGameLoop() {
        this.gameEngine.stopGameLoop();
    }

    public void resetballPosition() {
        ballManager.resetPosition(paddle);
    }

    /**
     * tăng vàng /
     *
     * @param coins
     */
    public void addCoinEarn(int coins) {
        this.gameProgressService.addCoins(currentUser, coins);
    }

    public void increaseLives() {
        this.gameDataModel.increaseSessionLives();
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public GameDataModel getGameDataModel() {
        return gameDataModel;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public void setAnimationManager(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }

    public void setPowerUpManager(PowerUpManager powerUpManager) {
        this.powerUpManager = powerUpManager;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public double getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(double gameWidth) {
        this.gameWidth = gameWidth;
    }

    public double getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(double gameHeight) {
        this.gameHeight = gameHeight;
    }
}
