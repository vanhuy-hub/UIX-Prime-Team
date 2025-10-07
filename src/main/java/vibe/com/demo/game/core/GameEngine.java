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
     *
     */
    public void playGameLoop() {
        if (this.gameLoop != null) {
            gameLoop.start();
        }
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

        paddle.update();
        if (ball.isActive()) {
            ball.update();
        } else {
            this.gameManager.resetballPosition();
        }
        checkCollision();
        checkBallLost();
    }

    public void checkCollision() {
        this.collisionDetector.checkBallPaddleCollision(ball, paddle);
        this.collisionDetector.checkWallCollision(ball, gameManager.getGameWidth(), gameManager.getGameHeight());
        this.collisionDetector.constrainPaddle(paddle, gameManager.getGameWidth());
        checkBrickCollision();
    }

    /**
     * xử lý chi tiết va chạm với gạch
     */
    public void checkBrickCollision() {
        for (int i = bricks.size() - 1; i >= 0; i--) {
            if (this.collisionDetector.isBallBrickCollision(ball, bricks.get(i))) {//kiểm tra va chạm 
                Brick newBrick = this.collisionDetector.getDeradeBrick(this.ball, bricks.get(i), this.collisionDetector.determineCollisionSide(ball, bricks.get(i)));
                if (newBrick == null) {//kiểm tra degradeBrick xem là gì , nếu null thì xóa luôn , ko null thì gán 
                    bricks.remove(i);
                } else {
                    bricks.set(i, newBrick);//sửa phần tử thứ i thành newBrick 
                }
            }
        }
    }

    public void checkBallLost() {
        if (this.collisionDetector.checkBallLost(ball, gameManager.getGameHeight())) {
            gameManager.handleBallLost();
        }
    }
}
