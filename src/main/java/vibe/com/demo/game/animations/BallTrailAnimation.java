package vibe.com.demo.game.animations;

import javafx.scene.paint.Color;
import vibe.com.demo.game.objects.entities.ball.Ball;

/**
 * Animation đuôi bóng
 */
public class BallTrailAnimation extends GameAnimation {

    private static final int MAX_PARTICLES = 20;
    private double lastX, lastY;

    public BallTrailAnimation() {
        super(AnimationType.BALL_TRAIL, 0); // Duration 0 = vĩnh viễn
    }

    public void updateBallPosition(Ball ball) {
        double centerX = ball.getX() + ball.getWidth() / 2;
        double centerY = ball.getY() + ball.getHeight() / 2;
        this.isActive = ball.isActive();

        if (this.isActive()) {
            double distance = Math.sqrt(
                    Math.pow(centerX - lastX, 2) + Math.pow(centerY - lastY, 2)
            );

            if (distance > 2 && particles.size() < MAX_PARTICLES) {
                // Tính hướng di chuyển ngược lại để đặt particle PHÍA SAU
                double dx = centerX - lastX;
                double dy = centerY - lastY;

                // Chuẩn hóa vector hướng, do ta muốn các tâm 1 khoảng offset nên phải như vậy 
                double length = Math.sqrt(dx * dx + dy * dy);
                if (length > 0) {
                    dx /= length;//tìm cos 
                    dy /= length;//tìm sin 
                }

                // Đặt particle LÙI LẠI 1 chút so với hướng di chuyển
                double offset = 16; // Khoảng cách lùi lại
                double trailX = centerX - dx * offset;
                double trailY = centerY - dy * offset;

                Color trailColor = getTrailColor(distance);
                TrailParticle particle = new TrailParticle(trailX, trailY, trailColor);
                particles.add(particle);
            }

            lastX = centerX;
            lastY = centerY;
        }

        update();
    }

    private Color getTrailColor(double speed) {
        if (speed >= 6.5) {
            return Color.rgb(255, 100, 100, 0.7); // Đỏ - tốc độ cao
        } else if (speed > 5) {
            return Color.rgb(255, 200, 100, 0.6); // Cam - tốc độ trung bình
        } else {
            return Color.rgb(255, 255, 255, 0.5); // Trắng - tốc độ thấp
        }
    }

    @Override
    public void update() {
        if (!this.isActive) {
            particles.clear();
            return;
        }

        super.update();
    }

    @Override
    public void start(double x, double y, Object... params) {
        this.lastX = x;
        this.lastY = y;
        this.isActive = true;
    }

    @Override
    public void stop() {
        this.isActive = false;
        super.stop();
    }
}
