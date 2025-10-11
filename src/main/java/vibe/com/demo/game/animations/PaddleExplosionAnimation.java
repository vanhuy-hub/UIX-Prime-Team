package vibe.com.demo.game.animations;

import javafx.scene.paint.Color;

/**
 * Animation nổ paddle
 */
public class PaddleExplosionAnimation extends GameAnimation {

    public PaddleExplosionAnimation() {
        super(AnimationType.PADDLE_EXPLOSION, 1.5);
    }

    @Override
    public void start(double x, double y, Object... params) {
        createExplosionParticles(x, y);
        this.isActive = true;
        this.elapsedTime = 0;
    }

    private void createExplosionParticles(double centerX, double centerY) {
        int particleCount = 25;

        for (int i = 0; i < particleCount; i++) {
            Color particleColor = Color.rgb(
                    255,
                    (int) (100 + Math.random() * 155),
                    0,
                    0.9
            );

            BrickParticle particle = new BrickParticle(centerX, centerY, particleColor);
            // Paddle explosion mạnh hơn
            particle.vx = (Math.random() - 0.5) * 12;
            particle.vy = (Math.random() - 0.5) * 12;
            particle.size = 4 + Math.random() * 8;

            particles.add(particle);
        }
    }
}
