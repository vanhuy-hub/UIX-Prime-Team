package vibe.com.demo.game.core;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import vibe.com.demo.controller.GameViewController;
import vibe.com.demo.game.animations.AnimationManager;
import vibe.com.demo.game.levels.LevelManager;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.overlay.OverlayObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.game.objects.entities.powerups.PowerUpManager;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.game.GameProgressService;
//lớp GameManager quản lý tất cả các đối tương trong game session 

public class GameManager {

    private Paddle paddle;
    private Ball ball;

    private GameState gameState;

    private GraphicsContext gc;
    private OverlayObject overlay;
    private AnimationManager animationManager;
    private Renderer renderer;
    private GameEngine gameEngine;
    private LevelManager levelManager;//quản lý level , thằng List<Brick>do ông này quản lý 
    private GameDataModel gameDataModel;//quản lý dữ liệu trong gameSession (lives , coinEarned )
    private PowerUpManager powerUpManager;
    //
    private double gameWidth;
    private double gameHeight;

    //GameProgressService ~ đối tượng quản lý cả game 
    private GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();
    private User currentUser = ServiceLocator.getInstance().getAuthService().getCurrentUser();
    private GameViewController gameView;

    public GameManager(GraphicsContext gc, double gameWidth, double gameHeight, GameViewController gameView) {
        System.out.println("Khoi tao lai");
        System.out.println(gameProgressService.getSelectedLevel());
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.gameState = GameState.READY;
        this.gc = gc;
        animationManager = new AnimationManager();
        powerUpManager = new PowerUpManager(gameHeight);
        gameEngine = new GameEngine(this);
        renderer = new Renderer(gc, gameWidth, gameHeight);
        gameDataModel = new GameDataModel();
        this.gameView = gameView;
        init();
    }

    public void init() {
        levelManager = new LevelManager(gameProgressService.getSelectedLevel(), gameWidth);
        initializeGameObjects();
        //truyền paddle và ball vào setGameObject của gameEngine  
        gameEngine.setGameObjects(paddle, ball, levelManager.getCurrentBricks());
        //render lần đầu 
        render();
    }

    /**
     * Khởi tạo vị trí các phần tử : ball, paddle, overlay
     */
    public void initializeGameObjects() {
        // Initialize paddle at bottom center
        double paddleWidth = 140;
        double paddleHeight = 40;
        double paddleX = (gameWidth - paddleWidth) / 2;
        double paddleY = gameHeight - paddleHeight - 30;
        paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);
        System.out.println(gameProgressService.getIdCurrentPaddle(currentUser));
        paddle.setImgFromURL(gameProgressService.getCurrentPaddleImageURL(currentUser));
        // Initialize ball on top of paddle
        double ballRadius = 10;
        ball = new Ball(0, 0, ballRadius);
        ball.reset(paddle);
        //OverlayInit
        overlay = new OverlayObject(0, 0, gameWidth, gameHeight);
        showOverlay("Nhấn SPACE để bắt đầu");
        delayHideOverLay(1300);
    }

    public void delayHideOverLay(long ms) {
        PauseTransition pause = new PauseTransition(Duration.millis(ms));
        pause.setOnFinished(e -> {
            System.out.println("an");
            this.hideOverlay();
            gameState = GameState.PLAYING;
            gameEngine.startGameLoop();
        });
        pause.play();
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

    public void resetballPosition() {
        ball.reset(paddle);
    }

    /**
     * Khởi tạo lại map, lưu ý cần phải xóa hết phần tử cũ trong bricks trước
     * rồi mới addAll , không sẽ bị sai khi restartGame , (nghĩa là dữ liệu
     * bricks cũ vẫn còn , rồi nó add thêm map mới lấy từ LevelDesign)
     */
    public void initializeLevel() {
        levelManager.resetCurrentLevel();//phải reset lại (giống như clear)
    }

    public void delayPaddleDisapper(long ms) {
        PauseTransition pause = new PauseTransition(Duration.millis(ms));
        pause.setOnFinished(e -> {
            paddle.disapper();
        });
        pause.play();
    }

    public void handleBallLost() {
        this.gameDataModel.decreaseSessionLives();//gọi hàm giảm mạng sống 
        if (this.gameDataModel.getSessionLives() == 0) {
            if (!this.gameDataModel.isWon()) {
                this.gameDataModel.setWon(true);
                delayPaddleDisapper(500);
                delayShowOverlay("Nhấn R để chơi lại", 1000, GameState.GAME_OVER);
            }
        } else {//dừng game 
            ball.reset(paddle);//chỉ reset lại vị trí quả bóng 
        }
    }

    /**
     * Hàm xử lý sự kiện
     */
    public void handleLevelComplete() {
        if (!this.gameDataModel.isLost()) {
            this.gameDataModel.setLost(true);
            delayShowOverlay("Chúc mừng bạn đã hoàn thành level " + this.levelManager.getCurrentLevel(), 500, GameState.GAME_WIN);
            gameView.unlockNextButton();
            this.gameProgressService.completeLevel(currentUser, levelManager.getCurrentLevel());

        }
    }

    /**
     * Restart game : dừng vòng lặp game cũ để tránh bị lỗi Multiple Game loops
     * và khởi tạo lại game mới
     *
     */
    public void restartGame() {
        gameDataModel.resetGameSession();
        gameEngine.stopGameLoop();
        init();
        gameState = GameState.READY;
    }

    public void render() {
        renderer.render(ball, paddle, overlay, levelManager.getCurrentBricks(), animationManager, powerUpManager);
    }

    /**
     * ✅ OVERLAY MANAGEMENT METHODS
     */
    public void showOverlay(String message, Color messageColor) {
        overlay.show(message, messageColor);
        render();
    }

    public void showOverlay(String message) {
        overlay.show(message, Color.WHITE);
        render();
    }

    public void hideOverlay() {
        overlay.hide();
    }

    /**
     * Hàm toggle ~ công tắc chuyển đổi trạng thái trong game
     */
    public void togglePauseGame() {
        System.out.println(gameState);
        if (gameState == GameState.PLAYING && ball.isActive()) {
            showOverlay("Nhấn SPACE để tiếp tục");
            gameState = GameState.PAUSED;
        } else if (gameState == GameState.PLAYING && !ball.isActive()) {//khi bị mất bóng  nhưng vẫn còn mạng 
            ball.launch();
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
                System.out.println("an right");
                this.paddle.moveRight();
            }
            case "LEFT" ->
                this.paddle.moveLeft();
            case "SPACE" -> {
                System.out.println("Overlay " + overlay.isVisible());
                togglePauseGame();
            }
            case "R" -> {
                gameState = GameState.READY;
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
     * Ham kiem tra trang thai hoat dong cua game
     */
    public boolean isOnGoingGame() {
        return this.gameState == GameState.PLAYING;
    }

    public boolean isPauseGame() {
        return this.gameState == GameState.PAUSED;
    }

    public boolean isLostGame() {
        return this.gameState == GameState.GAME_OVER;
    }

    public boolean isWonGame() {
        return this.gameState == GameState.GAME_WIN;
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

    /**
     * GameState ~ Trạng thái của game : là kiểu enum vì chỉ có vài trạng thái
     * nhất định
     */
    public enum GameState {
        READY,
        PLAYING,
        PAUSED,
        GAME_OVER,
        GAME_WIN
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
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
