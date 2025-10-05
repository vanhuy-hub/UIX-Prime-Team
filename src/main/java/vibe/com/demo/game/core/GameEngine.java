package vibe.com.demo.game.core;

import java.util.List;

import javafx.animation.AnimationTimer;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.entities.paddle.Paddle;

/**
 * Logic xử lí logic game chính (update, game loop)
 */
public class GameEngine {

    private GameManager gameManager;
    private CollisionDetector collisionDetector;
    private AnimationTimer gameLoop;

    //các đối tượng trong game 
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    //Game state 
    private boolean isRunning = false;

    public GameEngine(GameManager gameManager) {
        this.gameManager = gameManager;
        collisionDetector = new CollisionDetector();
    }

    public void setGameObjects(Paddle paddle, Ball ball, List<Brick> bricks) {
        this.paddle = paddle;
        this.ball = ball;
        this.bricks = bricks;
    }

    /**
     * Bắt đầu gameLoop
     */
    public void startGameLoop() {

        isRunning = true;
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameManager.isOnGoingGame()) {
                    update();
                    System.out.println("chay");
                    gameManager.render();
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Dừng gameLoop
     */
    public void stopGameLoop() {
        isRunning = false;
        if (gameLoop != null) {
            gameLoop.stop();
            gameLoop = null;
        }
    }

    public void update() {
        ball.update();
        paddle.update();
        checkCollision();
        checkBallLost();
    }

    public void checkCollision() {
        this.collisionDetector.checkBallPaddleCollision(ball, paddle);
        this.collisionDetector.checkWallCollision(ball, gameManager.getGameWidth(), gameManager.getGameHeight());
        this.collisionDetector.constrainPaddle(paddle, gameManager.getGameWidth());
        for (Brick brick : bricks) {
            //chỉ kiểm tra những viên gạch nào không phải null 
            if (brick != null) {
                this.collisionDetector.checkBallBrickCollision(ball, brick);
            }
        }
    }

    public void checkBallLost() {
        if (this.collisionDetector.checkBallLost(ball, gameManager.getGameHeight())) {
            gameManager.handleBallLost();
        }
    }
}
