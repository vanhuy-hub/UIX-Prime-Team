package vibe.com.demo.game.objects.entities.ball;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.game.utils.GameConstants;

public class BallManager {

    private List<Ball> balls;
    private boolean isActive;
// constructor de tao ball va cho vao paddle 
    public BallManager(Paddle paddle) {
        balls = new ArrayList<>();
        addBallAtPaddle(paddle);
        this.isActive = false;
        setActiveBalls();
    }
//""
    public void start() {
        balls.get(0).launch();
    }

    public void setActiveBalls() {
        for (Ball ball : balls) {
            ball.setIsActive(this.isActive);
        }

    }

    public void render(GraphicsContext renderer) {
        for (Ball ball : balls) {
            ball.render(renderer);
        }
    }

    public void update() {
        for (Ball ball : balls) {
            ball.update();
        }
    }

    public void addBall(int count, double x, double y, double radius) {
        while (this.isActive && count-- > 0 && balls.size() < GameConstants.MAX_BALLS_SIZE) {
            Ball newBall = new Ball(x, y, radius);
            balls.add(newBall);
            newBall.launch();
        }
    }

    public void addBallAtPaddle(Paddle paddle) {
        Ball newBall = new Ball(0, 0, GameConstants.BALL_RADIUS);
        newBall.reset(paddle);
        balls.add(newBall);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
        setActiveBalls();
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public void increaseVeclocity(double d) {
        // TODO Auto-generated method stub
        balls.forEach(ball -> ball.increaseVeclocity(d));
    }

    public void resetPosition(Paddle paddle) {
        // TODO Auto-generated method stub
        balls.get(0).reset(paddle);
    }

    public void decreaseVeclocity(double d) {
        // TODO Auto-generated method stub
        balls.forEach(ball -> ball.decreaseVeclocity(d));
    }

    public void fireBallActive() {
        // TODO Auto-generated method stub
        balls.forEach(ball -> ball.fireBallActive());
    }

}
