package vibe.com.demo.game.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.animation.AnimationTimer;
import vibe.com.demo.game.animations.AnimationManager;
import vibe.com.demo.game.animations.AnimationType;
import vibe.com.demo.game.core.CollisionDetector.CollisionSide;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.ball.BallManager;
import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.entities.bricks.ExplosiveBrick;
import vibe.com.demo.game.objects.entities.bricks.UnbreakableBrick;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.game.objects.entities.powerups.CoinPowerUp;
import vibe.com.demo.game.objects.entities.powerups.ExpandPaddlePowerUp;
import vibe.com.demo.game.objects.entities.powerups.ExtraLifePowerUp;
import vibe.com.demo.game.objects.entities.powerups.FireBallPowerUp;
import vibe.com.demo.game.objects.entities.powerups.MultiplyBall;
import vibe.com.demo.game.objects.entities.powerups.PowerUp;
import vibe.com.demo.game.objects.entities.powerups.PowerUpManager;
import vibe.com.demo.game.objects.entities.powerups.SlowBallPowerUp;
import vibe.com.demo.game.utils.GameConstants;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;

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
    private BallManager ballManager;
    private List<Brick> bricks;

    //audio 
    private AudioService audioManager = ServiceLocator.getInstance().getAudioService();

    //Game state 
    public GameEngine(GameManager gameManager) {
        this.gameManager = gameManager;
        this.animationManager = gameManager.getAnimationManager();
        this.powerUpManager = gameManager.getPowerUpManager();
        powerUps = this.powerUpManager.getPowerUps();
        collisionDetector = new CollisionDetector();
    }

    public void setGameObjects(Paddle paddle, BallManager ballManager, List<Brick> bricks) {
        this.paddle = paddle;
        this.ballManager = ballManager;
        this.bricks = bricks;
    }

    /**
     * Bắt đầu gameLoop
     */
    public void startGameLoop() {
        if (gameLoop != null) {
            stopGameLoop();
        }
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameManager.getGameState() == GameManager.GameState.PLAYING) {
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
        if (gameLoop != null) {
            gameLoop.stop();
            gameLoop = null;
        }
    }

    /**
     * Update game.
     */
    public void update() {
        animationManager.update();
        paddle.update();
        powerUpManager.update();
        if (ballManager.isActive()) {
            powerUpManager.startTimer();
            ballManager.update();
        } else {
            powerUpManager.stopTimer();
            ballManager.resetPosition(paddle);
        }
        constrainObjects();
        checkCollision();
        checkBallLost();
        checkLevelCompletion();
    }

    /**
     * logic kiểm tra level đã hoàn thành hay chưa , kiểm tra tất cả viên gạch
     * đã bị phá hủy trừ gạch không thể phá hủy
     */
    private void checkLevelCompletion() {
        // TODO Auto-generated method stub
        boolean isCompleted = true;
        for (Brick brickItem : bricks) {
            if (!(brickItem instanceof UnbreakableBrick)) {
                isCompleted = false;
                break;
            }
        }
        if (isCompleted) {
            gameManager.handleLevelComplete();
        }

    }

    /**
     * check va chạm ...
     */
    public void checkCollision() {
        for (Ball ball : ballManager.getBalls()) {
            if (this.collisionDetector.checkBallPaddleCollision(ball, paddle)) {
                double hitPosition = this.collisionDetector.calculateHitPosition(ball, paddle);
                handlePaddleCollision(ball, hitPosition);
            }
        }
        checkBrickCollision();
        checkCollisionPowerUp();
    }

    /**
     * Xử lý va chạm với paddle .
     */
    public void handlePaddleCollision(Ball ball, double hitPosition) {
        //Điều chỉnh vị trí bắt đầu nảy của ball để tránh bị stuck ~ kẹt trong paddle 
        ball.setY(paddle.getY() - ball.getHeight() - 2);

        //Tính góc bật dựa trên vị trí va chạm 
        //-0.5 - 0.5 : trai -> phải , nhân với hệ số góc 
        double angleFactor = (hitPosition - 0.5) * 2;//-1 ->1

        //góc bật : tối đa -75 - 75 deg so với phương thẳng đứng , nếu bóng đập về phía bên trái thì nảy về phía trái , ngược lại ở phía bên phải 
        double maxAngle = Math.toRadians(75);
        double angle = maxAngle * angleFactor;
        //tính toán dx mới , dùng sin cho dx 
        double newDx = Math.sin(angle) * ball.getSpeed();
        double newDy = -ball.getDy();
        //setVec
        ball.setVelocity(newDx, newDy);
    }

    /**
     * xử lý chi tiết va chạm với gạch
     */
    public void checkBrickCollision() {
        List<Brick> bricksToRemove = new ArrayList<>();
        for (int i = bricks.size() - 1; i >= 0; i--) {
            for (Ball ball : ballManager.getBalls()) {
                if (this.collisionDetector.basicCollision(ball, bricks.get(i))) {//kiểm tra va chạm 
                    CollisionSide side = this.collisionDetector.determineCollisionSide(ball, bricks.get(i));
                    if (bricks.get(i) instanceof ExplosiveBrick) {
                        addAdjacentBrick(bricks.get(i), bricksToRemove);
                    }
                    handleCollisionBrick(ball, bricks.get(i), side);
                    Brick newBrick = bricks.get(i).degradeBrick();
                    if (newBrick == null) {//kiểm tra degradeBrick xem là gì , nếu null thì xóa luôn , ko null thì gán 
                        bricksToRemove.add(bricks.get(i));
                    } else {
                        bricks.set(i, newBrick);//sửa phần tử thứ i thành newBrick 
                    }
                }
            }
        }
        bricks.removeAll(bricksToRemove);
    }

    /**
     * Xử lý va chạm giữa bóng và gạch
     */
    public void handleCollisionBrick(Ball ball, Brick brick, CollisionSide side) {
        if (ball.isPowerFull()) {
            if (brick instanceof UnbreakableBrick == false) {
                return;
            }
        }
        if (side == CollisionSide.BOTTOM || side == CollisionSide.TOP) {
            ball.bounceVertical();
        } else if (side == CollisionSide.LEFT || side == CollisionSide.RIGHT) {
            ball.bounceHorizontal();
        }
    }

    /**
     * Thêm gạch xung quanh gạch nổ .
     */
    public void addAdjacentBrick(Brick explosiveBrick, List<Brick> bricksToRemove) {
        Queue<Brick> explosiveQueue = new LinkedList<>();
        explosiveQueue.offer(explosiveBrick);//thêm bằng offer
        //while thay vì đệ quy 
        while (!explosiveQueue.isEmpty()) {

            //lấy viên gạch  đầu tiên đồng thời xóa 
            Brick currentBrick = explosiveQueue.poll();

            if (bricksToRemove.contains(currentBrick)) {

                //dừng lại nếu có trong mảng remove rồi tránh bị lặp vô hạn lần 
                continue;
            }
            animationManager.addSpriteAnimation(AnimationType.BRICK_DESTROY, currentBrick.getX() - 70, currentBrick.getY() - 80);
            bricksToRemove.add(currentBrick);
            double brickX = currentBrick.getX();//vị trí hiện tại ở trong cha của nó (game canvas)
            double brickY = currentBrick.getY();
            double brickWidth = currentBrick.getWidth();
            double brickHeight = currentBrick.getHeight();
            double colGap = GameConstants.COL_GAP;
            double rowGap = GameConstants.ROW_GAP;
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

    /**
     * Timf gạch dựa vào vị trí trên map.
     */
    public Brick findBrickAtPosition(double x, double y) {
        for (Brick brick : bricks) {
            if (Math.abs(brick.getX() - x) < 1 && Math.abs(brick.getY() - y) < 1) {
                return brick;
            }
            //  kiểm tra collision bounds chính xác hơn
            if (brick.getX() == x && brick.getY() == y) {
                return brick;
            }
        }
        return null;
    }

    /**
     * Check mất bóng .
     */
    public void checkBallLost() {
        //Nếu duyệt vòng lặp bình thường rồi gọi remove(ball) sẽ văng lỗi ConcurrentModification ~ sửa arrayList trong lúc lặp
        //Giải pháp : dùng danh sách phụ 
        List<Ball> toRemove = new ArrayList<>();
        for (Ball ball : ballManager.getBalls()) {
            if (ball.getY() + ball.getHeight() >= gameManager.getGameHeight() + 50) {
                toRemove.add(ball);
            }
        }
        ballManager.getBalls().removeAll(toRemove);
        if (ballManager.getBalls().isEmpty()) {
            this.gameManager.handleBallLost();
        }
    }

    /**
     * Collision powerUp
     */
    public void checkCollisionPowerUp() {
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUpItem = powerUps.get(i);
            if (this.collisionDetector.basicCollision(paddle, powerUpItem)) {
                audioManager.playSoundEffect("collect");
                powerUps.remove(i);

                if (powerUpItem.getClass() == CoinPowerUp.class) {
                    gameManager.addCoinEarn(GameConstants.BONUS);
                } else if (powerUpItem instanceof ExpandPaddlePowerUp) {
                    paddle.expand();
                } else if (powerUpItem instanceof SlowBallPowerUp) {
                    ballManager.decreaseVeclocity(GameConstants.ACCELERATION);
                } else if (powerUpItem instanceof ExtraLifePowerUp) {
                    gameManager.increaseLives();
                } else if (powerUpItem instanceof FireBallPowerUp) {
                    ballManager.fireBallActive();
                } else if (powerUpItem instanceof MultiplyBall) {
                    this.ballManager.addBall(1, ballManager.getBalls().get(0).getX(), ballManager.getBalls().get(0).getY(), ballManager.getBalls().get(0).getWidth() / 2);
                }

            }
        }
    }

    /**
     * giới hạn các đối tượng trong màn hình chơi game .
     */
    public void constrainObjects() {
        constrainPaddle();
        constrainBalls();
    }

    /**
     * giới hạn bóng .
     */
    public void constrainBalls() {
        for (Ball ball : ballManager.getBalls()) {
            if (ball.getX() <= 0) {
                ball.setX(0);
                ball.bounceHorizontal();
            } else if (ball.getWidth() + ball.getX() >= gameManager.getGameWidth()) {
                ball.setX(gameManager.getGameWidth() - ball.getWidth());
                ball.bounceHorizontal();
            }

            //TOP 
            if (ball.getY() <= 0) {
                ball.setY(0);
                ball.bounceVertical();
            }
        }
    }

    /**
     * Giới hạn paddle.
     */
    public void constrainPaddle() {
        if (paddle.getX() < 10) {
            paddle.setX(10);
        } else if (paddle.getX() + paddle.getWidth() >= gameManager.getGameWidth() - 10) {
            paddle.setX(gameManager.getGameWidth() - paddle.getWidth() - 10);
        }
    }
}
