package vibe.com.demo.game.core;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.overlay.OverlayObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
//lớp GameManager quản lý tất cả các đối tương trong game session 

public class GameManager {

    private Paddle paddle;
    private Ball ball;
    private GameState gameState;
    private AnimationTimer gameLoop;
    private GraphicsContext renderer;
    private OverlayObject overlay;

    //
    private double gameWidth;
    private double gameHeight;

    public GameManager(double gameWidth, double gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.gameState = GameState.READY;
        init();
    }

    public void init() {
        // Initialize paddle at bottom center
        double paddleWidth = 100;
        double paddleHeight = 20;
        double paddleX = (gameWidth - paddleWidth) / 2;
        double paddleY = gameHeight - paddleHeight - 30;

        paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);

        // Initialize ball on top of paddle
        double ballRadius = 10;
        ball = new Ball(0, 0, ballRadius);
        ball.reset(paddle);

        //OverlayInit
        overlay = new OverlayObject(0, 0, gameWidth, gameHeight);
        showOverlay("Nhấn SPACE để bắt đầu");

        System.out.println("🎯 Paddle position: " + paddleX + "," + paddleY);
    }

    public GraphicsContext getRenderer() {
        return renderer;
    }

    public void setRenderer(GraphicsContext renderer) {
        this.renderer = renderer;
    }

    public void startGame() {
        if (gameState == GameState.READY) {
            gameState = GameState.PLAYING;
            ball.launch();
            hideOverlay();
            startGameLoop();
        }
    }

    public void startGameLoop() {
        gameLoop = new AnimationTimer() {//tach biet voi luong chuong trinh
            @Override
            public void handle(long now) {
                if (gameState == GameState.PLAYING) {
                    update();
                    render();
                }
            }
        };
        gameLoop.start();
    }

    public void update() {
        this.paddle.update();
        this.ball.update();
        //check collision
        checkCollision();
        //check var cham bien
        checkBoundaties();
    }

    public void checkCollision() {
        // System.out.println("ballIsActive: " + ball.isActive());
        if (ball.collidesWith(paddle) && ball.isIsActive()) {
            ball.bounceVertical();//dao chieu van toc dy
        }
    }

    public void checkBoundaties() {
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= gameWidth) {
            ball.bounceHorizontal();
        }
        if (ball.getY() <= 0) {
            ball.bounceVertical();
        }
        if (ball.getY() + ball.getHeight() >= gameHeight) {
            handleBallLost();
        }

        if (paddle.getX() <= 0) {
            paddle.setX(0);
        }
        if (paddle.getX() + paddle.getWidth() >= gameWidth) {
            paddle.setX(gameWidth - paddle.getWidth());
        }
    }

    public void handleBallLost() {
        restartGame();
        gameState = GameState.READY;
    }

    /**
     * Restart game : dừng vòng lặp game cũ để tránh bị lỗi Multiple Game loops
     * và khởi tạo lại game mới
     *
     */
    public void restartGame() {
        if (this.gameLoop != null) {
            gameLoop.stop();
        }
        init();
        gameState = GameState.READY;
    }

    public void render() {
        if (renderer == null) {
            System.out.println("renderer is null");
            return;
        }
        //clear canvas
        renderer.clearRect(0, 0, gameWidth, gameHeight);

        // Draw background
        renderer.setFill(javafx.scene.paint.Color.BLACK);
        renderer.fillRect(0, 0, gameWidth, gameHeight);
        //render elements
        this.paddle.render(renderer);
        this.ball.render(renderer);
        this.overlay.render(renderer);
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
        if (gameState == GameState.PLAYING) {
            showOverlay("Nhấn SPACE để tiếp tục");
            gameState = GameState.PAUSED;
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

    public AnimationTimer getGameLoop() {
        return gameLoop;
    }

    public void setGameLoop(AnimationTimer gameLoop) {
        this.gameLoop = gameLoop;
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
