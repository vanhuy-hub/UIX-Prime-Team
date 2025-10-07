package vibe.com.demo.game.core;

import vibe.com.demo.game.objects.abstractions.GameObject;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.entities.paddle.Paddle;

public class CollisionDetector {

    /**
     * Hàm check va chạm cơ bản
     */
    public boolean basicCollision(GameObject obj1, GameObject obj2) {
        double minR = Math.min(obj1.getX() + obj1.getWidth(), obj2.getX() + obj2.getWidth());
        double maxL = Math.max(obj1.getX(), obj2.getX());
        double maxT = Math.max(obj1.getY(), obj2.getY());
        double minB = Math.min(obj1.getY() + obj1.getHeight(), obj2.getY() + obj2.getHeight());
        return minR >= maxL && maxT <= minB;
    }

    /**
     * Enum miền giá trị của CollisionSide
     */
    public enum CollisionSide {
        TOP,
        RIGHT,
        LEFT,
        BOTTOM
    }

    /**
     * Hàm check+xử lí nếu có va chạm giữa bóng và paddle
     */
    public void checkBallPaddleCollision(Ball ball, Paddle paddle) {
        if (!ball.isActive() || !basicCollision(ball, paddle)) {
            return;
        }
        //tính toán vi trí va chạm để xác định góc bật 
        double hitPosition = calculateHitPosition(ball, paddle);
        //gọi hàm xử lý khi 
        handlePaddleCollision(ball, paddle, hitPosition);
    }

    /**
     * Hàm check nếu có va chạm giữa bóng và brick
     */
    public boolean isBallBrickCollision(Ball ball, Brick brick) {
        return (ball.isActive() && !brick.isDestroyed() && basicCollision(ball, brick));
    }

    /**
     * Ham xác định hướng va chạm
     */
    public CollisionSide determineCollisionSide(Ball ball, GameObject other) {
        double overlapLeft = ball.getX() + ball.getWidth() - other.getX();
        double overlapRight = other.getX() + other.getWidth() - ball.getX();
        double overlapTop = ball.getY() + ball.getHeight() - other.getY();
        double overlapBottom = other.getY() + other.getHeight() - ball.getY();

        double minOverlap = Math.min(overlapBottom, Math.min(overlapLeft, Math.min(overlapRight, overlapTop)));
        return minOverlap == overlapLeft ? CollisionSide.LEFT : (minOverlap == overlapBottom ? CollisionSide.BOTTOM : (minOverlap == overlapTop ? CollisionSide.TOP : CollisionSide.RIGHT));
    }

    /**
     * Hàm tính toán vị trí va chạm ~ vị trí tương đối so với paddle : 0->1
     */
    public double calculateHitPosition(Ball ball, Paddle paddle) {
        double ballX = ball.getX() + ball.getWidth() / 2;
        double paddleX = paddle.getX();
        double relativePosition = (ballX - paddleX) / paddle.getWidth();
        return Math.max(0, Math.min(relativePosition, 1));
    }

    /**
     * Hàm handle xử lí góc nảy ra (điều chỉnh tốc )
     */
    public void handlePaddleCollision(Ball ball, Paddle paddle, double hitPosition) {
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
     * Hàm xử lí khi va chạm với gạch
     */
    public Brick getDeradeBrick(Ball ball, Brick brick, CollisionSide side) {
        if (side == CollisionSide.BOTTOM || side == CollisionSide.TOP) {
            ball.bounceVertical();
        } else if (side == CollisionSide.LEFT || side == CollisionSide.RIGHT) {
            ball.bounceHorizontal();
        }
        return brick.takeHit();

    }

    /**
     * Kiểm tra va chạm với tường
     */
    public void checkWallCollision(Ball ball, double gameWidth, double gameHeight) {
        if (ball.getX() <= 0) {
            ball.setX(0);
            ball.bounceHorizontal();
        } else if (ball.getWidth() + ball.getX() >= gameWidth) {
            ball.setX(gameWidth - ball.getWidth());
            ball.bounceHorizontal();
        }

        //TOP 
        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.bounceVertical();
        }
    }

    public boolean checkBallLost(Ball ball, double gameHeight) {
        return ball.getY() + ball.getHeight() >= gameHeight + 50;
    }

    /**
     * Hàm giới hạnh paddle trong màn hình
     */
    public void constrainPaddle(Paddle paddle, double gameWidth) {
        if (paddle.getX() < 10) {
            paddle.setX(10);
        } else if (paddle.getX() + paddle.getWidth() >= gameWidth - 10) {
            paddle.setX(gameWidth - paddle.getWidth() - 10);
        }
    }
    /**
     * Hàm check
     */
}
