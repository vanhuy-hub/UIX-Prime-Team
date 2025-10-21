package vibe.com.demo.game.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.animation.AnimationTimer;
import vibe.com.demo.game.animations.AnimationManager;
import vibe.com.demo.game.animations.AnimationType;
import vibe.com.demo.game.levels.LevelConfig;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.entities.bricks.ExplosiveBrick;
import vibe.com.demo.game.objects.entities.bricks.StrongBrick;
import vibe.com.demo.game.objects.entities.bricks.UnbreakableBrick;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.game.objects.entities.powerups.CoinPowerUp;
import vibe.com.demo.game.objects.entities.powerups.ExpandPaddlePowerUp;
import vibe.com.demo.game.objects.entities.powerups.ExtraLifePowerUp;
import vibe.com.demo.game.objects.entities.powerups.FireBallPowerUp;
import vibe.com.demo.game.objects.entities.powerups.MagnetPowerUp;
import vibe.com.demo.game.objects.entities.powerups.PowerUp;
import vibe.com.demo.game.objects.entities.powerups.PowerUpManager;
import vibe.com.demo.game.objects.entities.powerups.PowerUpType;
import vibe.com.demo.game.objects.entities.powerups.SlowBallPowerUp;

/**
 * Logic xử lí logic game chính (update, game loop)
 */
public class GameEngine {

    private AnimationManager animationManager;
    private GameManager gameManager;
    private CollisionDetector collisionDetector;
    private AnimationTimer gameLoop;
    private PowerUpManager powerUpManager;
    private List<PowerUp> powerUps;
    //các đối tượng trong game 
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private double elapsedTime;
    private final int duration = 2;

    //Game state 
    private boolean isRunning = false;

    public GameEngine(GameManager gameManager) {
        elapsedTime = 0;
        this.gameManager = gameManager;
        this.animationManager = gameManager.getAnimationManager();
        this.powerUpManager = gameManager.getPowerUpManager();
        powerUps = this.powerUpManager.getPowerUps();
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
        if (gameLoop != null) {
            stopGameLoop();
        }
        isRunning = true;
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameManager.isOnGoingGame()) {
                    update();
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
        animationManager.update(ball);
        paddle.update();
        powerUpManager.update();
        if (ball.isActive()) {
            elapsedTime += (double) 1 / 60;
            System.out.println(elapsedTime);
            if (elapsedTime >= duration) {
                ball.increaseVeclocity(1.02);
                System.out.println("ball: " + ball.getDx() + ":" + ball.getDy());
                elapsedTime = 0;
            }
            ball.update();
        } else {
            ball.reset(paddle);
        }
        checkCollision();
        checkBallLost();
        checkLevelCompletion();
        checkCollisionPowerUp();
    }

    /**
     * logic kiểm tra level đã hoàn thành hay chưa , kiểm tra tất cả viên gạch
     * đã bị phá hủy trừ gạch không thể phá hủy
     */
    private void checkLevelCompletion() {
        // TODO Auto-generated method stub
        boolean isCompleted = true;
        for (Brick brickItem : bricks) {
            if (!brickItem.isDestroyed() && !(brickItem instanceof UnbreakableBrick)) {
                isCompleted = false;
                break;
            }
        }
        if (isCompleted) {
            gameManager.handleLevelComplete();
        }
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
        List<Brick> bricksToRemove = new ArrayList<>();
        for (int i = bricks.size() - 1; i >= 0; i--) {
            if (this.collisionDetector.isBallBrickCollision(ball, bricks.get(i))) {//kiểm tra va chạm 
                if (bricks.get(i) instanceof UnbreakableBrick) {
                    collisionDetector.handleCollisionBrick(this.ball, bricks.get(i), this.collisionDetector.determineCollisionSide(ball, bricks.get(i)));
                    powerUpManager.addPowerUp(PowerUpType.FIREBALL, bricks.get(i).getX(), bricks.get(i).getY());
                    continue;
                } else if (bricks.get(i) instanceof ExplosiveBrick) {
                    powerUpManager.addPowerUp(PowerUpType.MAGNET, bricks.get(i).getX(), bricks.get(i).getY());
                    collisionDetector.handleCollisionBrick(this.ball, bricks.get(i), this.collisionDetector.determineCollisionSide(ball, bricks.get(i)));
                    addAdjacentBrick(bricks.get(i), bricksToRemove);

                    continue;
                } else if (bricks.get(i) instanceof StrongBrick) {
                    powerUpManager.addPowerUp(PowerUpType.FIREBALL, bricks.get(i).getX(), bricks.get(i).getY());
                } else {
                    powerUpManager.addPowerUp(PowerUpType.FIREBALL, bricks.get(i).getX(), bricks.get(i).getY());
                }

                Brick newBrick = this.collisionDetector.getDeradeBrick(this.ball, bricks.get(i), this.collisionDetector.determineCollisionSide(ball, bricks.get(i)));
                if (newBrick == null) {//kiểm tra degradeBrick xem là gì , nếu null thì xóa luôn , ko null thì gán 
                    bricks.remove(i);
                } else {
                    bricks.set(i, newBrick);//sửa phần tử thứ i thành newBrick 
                }
            }
        }
        bricks.removeAll(bricksToRemove);
    }

    public void addAdjacentBrick(Brick explosiveBrick, List<Brick> bricksToRemove) {
        Queue<Brick> explosiveQueue = new LinkedList<>();
        explosiveQueue.offer(explosiveBrick);//thêm bằng offer
        //while thay vì đệ quy 
        while (!explosiveQueue.isEmpty()) {
            //lấy viên gạch  đầu tiên đồng thời xóa 
            Brick currentBrick = explosiveQueue.poll();
            System.out.println("gach no tai: " + currentBrick.getX() + "-" + currentBrick.getY());
            if (bricksToRemove.contains(currentBrick)) {
                //dừng lại nếu có trong mảng remove rồi tránh bị đẹ quy vô hạn lần 
                continue;
            }
            animationManager.addSpriteAnimation(AnimationType.BRICK_DESTROY, currentBrick.getX() - 70, currentBrick.getY() - 80);

            bricksToRemove.add(currentBrick);
            double brickX = currentBrick.getX();//vị trí hiện tại ở trong cha của nó (game canvas)
            double brickY = currentBrick.getY();
            double brickWidth = LevelConfig.BRICK_WIDTH;
            double brickHeight = LevelConfig.BRICK_HEIGHT;
            double colGap = LevelConfig.COL_GAP;
            double rowGap = LevelConfig.ROW_GAP;
            //direction~ vị trí tương đối so với gạch hiện tại : Trên , Dưới , Trái Phải  
            double[][] directions = {{0, -(brickHeight + rowGap)}, {0, (brickHeight + rowGap)}, {-(brickWidth + colGap), 0}, {(brickWidth + colGap), 0}};

            for (double[] direction : directions) {
                double checkX = brickX + direction[0];
                double checkY = brickY + direction[1];
                Brick adjacentBrick = findBrickAtPosition(checkX, checkY);
                if (adjacentBrick != null) {
                    if (adjacentBrick instanceof UnbreakableBrick) {
                        continue;//bỏ qua nếu là gạch cứng 
                    } //đệ quy nếu gạch liền kề lại là Explosive
                    else if (adjacentBrick instanceof ExplosiveBrick) {
                        explosiveQueue.offer(adjacentBrick);
                    } else {
                        bricksToRemove.add(adjacentBrick);
                    }
                }
            }
        }
    }

    public Brick findBrickAtPosition(double x, double y) {
        for (Brick brick : bricks) {
            if (Math.abs(brick.getX() - x) < 1 && Math.abs(brick.getY() - y) < 1) {
                return brick;
            }
            // Hoặc kiểm tra collision bounds chính xác hơn
            if (brick.getX() == x && brick.getY() == y) {
                return brick;
            }
        }
        return null;
    }

    public void checkBallLost() {
        if (this.collisionDetector.checkBallLost(ball, gameManager.getGameHeight())) {
            gameManager.handleBallLost();
        }
    }

    // ========== Collision powerUp 
    public void checkCollisionPowerUp() {
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUpItem = powerUps.get(i);
            if (this.collisionDetector.basicCollision(paddle, powerUpItem)) {
                powerUps.remove(i);
                if (powerUpItem.getClass() == CoinPowerUp.class) {
                    gameManager.addCoinEarn(100);
                } else if (powerUpItem instanceof ExpandPaddlePowerUp) {
                    paddle.expand();
                } else if (powerUpItem instanceof SlowBallPowerUp) {
                    ball.decreaseVeclocity(1.3);
                } else if (powerUpItem instanceof ExtraLifePowerUp) {
                    gameManager.increaseLives();
                } else if (powerUpItem instanceof FireBallPowerUp) {
                    ball.fireBallActive();
                } else if (powerUpItem instanceof MagnetPowerUp) {
                    ball.setIsSticky(true);
                }
            }

        }
    }
}
