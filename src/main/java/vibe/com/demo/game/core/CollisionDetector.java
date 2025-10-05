package vibe.com.demo.game.core;

import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.paddle.Paddle;

public class CollisionDetector {

    /**
     * Hàm check va chạm cơ bản
     */
    public boolean basicCollision(Ball ball, Paddle paddle) {
        double minR = Math.min(ball.getX() + ball.getWidth(), paddle.getX() + paddle.getWidth());
        double maxL = Math.max(ball.getX(), paddle.getX());
        double maxT = Math.max(ball.getY(), paddle.getY());
        double minB = Math.min(ball.getY() + ball.getHeight(), paddle.getY() + paddle.getHeight());
        return minR >= maxL && maxT <= minB;
    }

    /**
     * Hàm check+xử lí nếu có va chạm giữa bóng và paddle
     */
    public boolean checkBallPaddleCollision(Ball ball, Paddle paddle) {
        if (!ball.isActive() || !basicCollision(ball, paddle)) {
            return false;
        }
        //tính toán vi trí va chạm để xác định góc bật 
        double hitPosition = calculateHitPosition(ball, paddle);
        //gọi hàm xử lý khi 
        handlePaddleCollision(ball, paddle, hitPosition);
        return true;
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
        ball.setY(paddle.getY() - ball.getHeight() - 5);

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
        return ball.getY() + ball.getHeight() >= gameHeight;
    }

    /**
     * Hàm giới hạnh paddle trong màn hình
     */
    public void constrainPaddle(Paddle paddle, double gameWidth) {
        if (paddle.getX() < 0) {
            paddle.setX(0);
        } else if (paddle.getX() + paddle.getWidth() >= gameWidth) {
            paddle.setX(gameWidth - paddle.getWidth());
        }
    }
}
