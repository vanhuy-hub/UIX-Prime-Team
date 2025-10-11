package vibe.com.demo.game.animations;

import javafx.scene.canvas.GraphicsContext;

/**
 * Animation rung gạch khi va chạm với gạch không phá vỡ
 */
public class BrickShakeAnimation extends GameAnimation {

    private ShakeEffect shakeEffect;

    public BrickShakeAnimation() {
        super(AnimationType.BRICK_HIT_UNBREAKABLE, 0.5);
    }

    @Override
    public void start(double x, double y, Object... params) {
        this.shakeEffect = new ShakeEffect(x, y);
        this.shakeEffect.start(5.0, 0.9); // Intensity 5.0, decay 0.9
        this.isActive = true;
        this.elapsedTime = 0;
    }

    @Override
    public void update() {
        if (!isActive) {
            return;
        }

        elapsedTime += 1.0 / 60.0;
        shakeEffect.update();

        if (elapsedTime >= duration || !shakeEffect.isActive()) {
            stop();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        // Shake effect không render particle, chỉ cung cấp vị trí rung
        // Render sẽ được xử lý bởi Brick class sử dụng getShakeX(), getShakeY()
    }

    public double getShakeX() {
        return shakeEffect != null ? shakeEffect.getShakeX() : 0;
    }

    public double getShakeY() {
        return shakeEffect != null ? shakeEffect.getShakeY() : 0;
    }
}
