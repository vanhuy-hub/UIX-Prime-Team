package vibe.com.demo.game.objects.entities.ball;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import vibe.com.demo.game.objects.abstractions.MovableObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;

public class Ball extends MovableObject {

    private Color color;
    private double speed;
    private boolean isActive;
    private Image img;

    // Hiệu ứng đuôi
    private List<TrailParticle> trailParticles;
    private static final int MAX_TRAIL_LENGTH = 30;
    private static double TRAIL_SPAWN_RATE = 0.9; // Tỷ lệ spawn particle

    public Ball(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);
        this.color = Color.WHITE;
        speed = 4;
        this.isActive = false;
        this.img = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/ball2.png"));

        // Khởi tạo hiệu ứng đuôi
        this.trailParticles = new ArrayList<>();
    }

    @Override
    public void render(GraphicsContext renderer) {
        // Render đuôi trước
        renderTrail(renderer);

        // Render bóng
        renderer.drawImage(img, x, y, width, height);
    }

    @Override
    public void update() {
        if (this.isActive) {
            super.update();

            // Cập nhật hiệu ứng đuôi
            updateTrail();
        } else {
            // Nếu bóng không active, xóa hết đuôi
            trailParticles.clear();
        }
    }

    /**
     * Hiệu ứng đuôi particle
     */
    private class TrailParticle {

        double x, y;
        double life; // 1.0 -> 0.0
        double size;
        Color color;

        public TrailParticle(double x, double y) {
            this.x = x;
            this.y = y;
            this.life = 1.0;
            this.size = width * 0.7; // Kích thước bằng 70% bóng
            this.color = Color.rgb(255, 255, 255, 0.6); // Màu trắng trong suốt
        }

        public void update() {
            life -= 0.15; // Tốc độ biến mất
        }

        public boolean isDead() {
            return life <= 0;
        }
    }

    /**
     * Cập nhật hiệu ứng đuôi
     */
    private void updateTrail() {
        // Thêm particle mới với tỷ lệ nhất định
        if (Math.random() < TRAIL_SPAWN_RATE && trailParticles.size() < MAX_TRAIL_LENGTH) {
            trailParticles.add(new TrailParticle(x + width / 2, y + height / 2));
        }

        // Cập nhật tất cả particles
        trailParticles.forEach(TrailParticle::update);

        // Xóa particles đã chết
        trailParticles.removeIf(TrailParticle::isDead);
    }

    /**
     * Render hiệu ứng đuôi
     */
    private void renderTrail(GraphicsContext renderer) {
        for (TrailParticle particle : trailParticles) {
            // Alpha giảm dần theo life
            double alpha = particle.life * 0.5;

            renderer.setGlobalAlpha(alpha);
            renderer.setFill(particle.color);

            // Vẽ particle với kích thước giảm dần
            double currentSize = particle.size * particle.life;
            renderer.fillOval(
                    particle.x - currentSize / 2,
                    particle.y - currentSize / 2,
                    currentSize,
                    currentSize
            );
        }

        // Reset alpha về mặc định
        renderer.setGlobalAlpha(1.0);
    }

    /**
     * Đảo chiều vận tốc theo phương ngang x
     */
    public void bounceHorizontal() {
        dx = -dx;
    }

    /**
     * Đảo chiều vận tốc theo phương thẳng đứng y
     */
    public void bounceVertical() {
        dy = -dy;
    }

    /**
     * Reset bóng về vị trí paddle
     */
    public void reset(Paddle paddle) {
        isActive = false;
        x = paddle.getX() + paddle.getWidth() / 2 - this.width / 2;
        y = paddle.getY() - this.height - 1;

        // Xóa đuôi khi reset
        trailParticles.clear();
    }

    public void setRandomVelocity() {
        setVelocity((Math.random() * 8) - 4, -4);
    }

    /**
     * Kích hoạt trạng thái hoạt động của quả bóng
     */
    public void launch() {
        setRandomVelocity();
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        if (!isActive) {
            trailParticles.clear(); // Xóa đuôi khi dừng
        }
    }

    public double getSpeed() {
        return speed;
    }

    /**
     * Tăng tốc độ bóng và điều chỉnh hiệu ứng đuôi
     */
    public void increaseSpeed(double multiplier) {
        this.speed *= multiplier;
        dx *= multiplier;
        dy *= multiplier;

        // Tăng tỷ lệ spawn đuôi khi tốc độ cao
        TRAIL_SPAWN_RATE = Math.min(0.9, TRAIL_SPAWN_RATE * 1.2);
    }

    /**
     * Giảm tốc độ bóng và điều chỉnh hiệu ứng đuôi
     */
    public void decreaseSpeed(double multiplier) {
        this.speed *= multiplier;
        dx *= multiplier;
        dy *= multiplier;

        // Giảm tỷ lệ spawn đuôi khi tốc độ thấp
        TRAIL_SPAWN_RATE = Math.max(0.3, TRAIL_SPAWN_RATE * 0.8);
    }
}
