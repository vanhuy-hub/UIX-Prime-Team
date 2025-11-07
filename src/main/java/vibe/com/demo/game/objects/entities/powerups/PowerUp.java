package vibe.com.demo.game.objects.entities.powerups;

import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.abstractions.MovableObject;

public abstract class PowerUp extends MovableObject {

    private static final double DY = 5;
    protected Timeline timeline;
    protected double duration;

    public PowerUp(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = DY;
    }

    @Override
    public void render(GraphicsContext renderer) {
        renderer.drawImage(image, x, y, width, height);
    }

    public boolean IsOutSideGame(double gameWith) {
        return this.y + this.getHeight() >= gameWith + 20;
    }

    public void active(Object... params) {
    }

}
