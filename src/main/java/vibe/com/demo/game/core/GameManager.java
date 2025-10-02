package vibe.com.demo.game.core;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
//lớp GameManager quản lý tất cả các đối tương trong game session 

public class GameManager {

    private Paddle paddle;
    private Ball ball;
    private GameState gameState;
    private AnimationTimer gameLoop;
    private GraphicsContext renderer;

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
        if (ball.collidesWith(paddle) && ball.isIsActive()) {
            handleBallPaddleCollision();
        }
    }

    public void handleBallPaddleCollision() {
        ball.bounceVertical();//dao chieu van toc dy
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
        if (paddle.getY() + paddle.getWidth() >= gameWidth) {
            paddle.setX(gameWidth - paddle.getWidth());
        }
    }

    public void handleBallLost() {
        ball.reset(paddle);
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

    }

    public enum GameState {
        READY,
        PLAYING,
        PAUSED,
        GAME_OVER
    }
}
