package vibe.com.demo.game.core;

import vibe.com.demo.game.objects.abstractions.GameObject;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.paddle.Paddle;

public class CollisionDetector {

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
     * Hàm check+xử lí nếu có va chạm giữa bóng và paddle
     */
    public boolean checkBallPaddleCollision(Ball ball, Paddle paddle) {
        if (!ball.isActive() || !basicCollision(ball, paddle)) {
            return false;
        }
        return true;
    }

    // /**
    //  * Hàm check nếu có va chạm giữa bóng và brick
    //  */
    // public boolean isBallBrickCollision(Ball ball, Brick brick) {
    //     return (ball.isActive() && !brick.isDestroyed() && basicCollision(ball, brick));
    // }
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

}
