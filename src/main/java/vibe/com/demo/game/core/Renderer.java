package vibe.com.demo.game.core;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.entities.ball.Ball;
import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.entities.overlay.OverlayObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;

public class Renderer {

    private GraphicsContext gc;
    private double gameWidth;
    private double gameHeight;

    public Renderer(GraphicsContext gc, double gameWidth, double gameHeight) {
        this.gc = gc;
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
    }

    public void render(Ball ball, Paddle paddle, OverlayObject overlay, List<Brick> bricks) {
        gc.clearRect(0, 0, gameWidth, gameHeight);//clear canvas 
        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : bricks) {
            brick.render(gc);
        }
        overlay.render(gc);

    }
}
