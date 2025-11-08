package vibe.com.demo.game.core;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.animations.AnimationManager;
import vibe.com.demo.game.objects.entities.ball.BallManager;
import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.entities.overlay.OverlayObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.game.objects.entities.powerups.PowerUpManager;

public class Renderer {

    private GraphicsContext gc;
    private double gameWidth;
    private double gameHeight;

    public Renderer(GraphicsContext gc, double gameWidth, double gameHeight) {
        this.gc = gc;
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
    }

    /**
     * Render tất cả đối tượng .
     *
     * @param ball
     * @param paddle
     * @param overlay
     * @param bricks
     * @param animationManager
     * @param powerUps
     */
    public void render(BallManager ball, Paddle paddle, OverlayObject overlay, List<Brick> bricks, AnimationManager animationManager, PowerUpManager powerUps) {
        gc.clearRect(0, 0, gameWidth, gameHeight);//clear canvas 
        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : bricks) {
            brick.render(gc);
        }
        animationManager.render(gc);
        powerUps.render(gc);
        overlay.render(gc);
    }
}
