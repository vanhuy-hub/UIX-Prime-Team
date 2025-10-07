package vibe.com.demo.game.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import vibe.com.demo.game.levels.LevelManager;
import vibe.com.demo.game.objects.entities.ball.Ball;

import vibe.com.demo.game.objects.entities.overlay.OverlayObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.game.GameProgressService;
//lớp GameManager quản lý tất cả các đối tương trong game session 

public class GameManager {

    private Paddle paddle;
    private Ball ball;

    private GameState gameState;

    private GraphicsContext gc;
    private OverlayObject overlay;

    private Renderer renderer;
    private GameEngine gameEngine;
    private LevelManager levelManager;//quản lý level , thằng List<Brick>do ông này quản lý 
    private GameDataModel gameDataModel;//quản lý dữ liệu trong gameSession (lives , coinEarned )
    //
    private double gameWidth;
    private double gameHeight;

    //GameProgressService ~ đối tượng quản lý cả game 
    private GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();

    public GameManager(GraphicsContext gc, double gameWidth, double gameHeight) {

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.gameState = GameState.READY;
        this.gc = gc;
        gameEngine = new GameEngine(this);
        renderer = new Renderer(gc, gameWidth, gameHeight);

        gameDataModel = new GameDataModel();
        init();

    }

    public void init() {

        levelManager = new LevelManager(gameProgressService.getSelectedLevel(), gameWidth);
        initializeGameObjects();
        //truyền paddle và ball vào setGameObject của gameEngine  
        gameEngine.setGameObjects(paddle, ball, levelManager.getCurrentBricks());
        //render lần đầu 
        renderer.render(ball, paddle, overlay, levelManager.getCurrentBricks());

    }

    /**
     * Khởi tạo vị trí các phần tử : ball, paddle, overlay
     */
    public void initializeGameObjects() {
        // Initialize paddle at bottom center
        double paddleWidth = 100;
        double paddleHeight = 20;
        double paddleX = (gameWidth - paddleWidth) / 2;
        double paddleY = gameHeight - paddleHeight - 30;

        paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);

        // Initialize ball on top of paddle
        double ballRadius = 10;
        ball = new Ball(0, 0, ballRadius);
        resetballPosition();
        //OverlayInit
        overlay = new OverlayObject(0, 0, gameWidth, gameHeight);
        showOverlay("Nhấn SPACE để bắt đầu");

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

    public void startGame() {
        if (gameState == GameState.READY) {
            gameState = GameState.PLAYING;
            ball.launch();
            hideOverlay();
            System.out.println("bat dau");
            gameEngine.startGameLoop();
        }
    }

    public void handleBallLost() {
        this.gameDataModel.decreaseSessionLives();//gọi hàm giảm mạng sống 
        if (this.gameDataModel.getSessionLives() == 0) {
            gameState = GameState.GAME_OVER;
            gameDataModel.setNextLevelUnlocked(true);
            System.out.println("thua");
            showOverlay(" ~ Nhấn R để chơi lại nào ~ ");
        } else {//dừng game 
            resetballPosition();//chỉ reset lại vị trí quả bóng 
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
        renderer.render(ball, paddle, overlay, levelManager.getCurrentBricks());
    }

    /**
     * ✅ OVERLAY MANAGEMENT METHODS
     */
    public void showOverlay(String message) {

        overlay.show(message, Color.WHITE);
        render(); // Re-render ngay lập tức, nếu chỉ overlay.show (message) mà không render thì sẽ không vẽ lại đâu 
    }

    public void hideOverlay() {

        overlay.hide();

        // render();
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
                if (gameState == GameState.READY) {
                    startGame();//bắt đầu game 
                } else if (gameState == GameState.PLAYING || gameState == GameState.PAUSED) {
                    togglePauseGame();
                }
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
