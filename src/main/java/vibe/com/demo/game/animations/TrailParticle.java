package vibe.com.demo.game.animations;

import javafx.scene.paint.Color;

/**
 * Particle cho hiệu ứng đuôi bóng
 */
public class TrailParticle extends Particle {

    public TrailParticle(double x, double y, Color color) {
        super(x, y, color, 12);
        // Đuôi bóng ít chuyển động hơn
        this.vx = (Math.random() - 0.5) * 1;
        this.vy = (Math.random() - 0.5) * 1;
    }

    @Override
    public void update() {
        x += vx;
        y += vy;
        life -= 0.04; // Biến mất nhanh hơn
        size *= 0.92; // Co lại dần
    }
}
