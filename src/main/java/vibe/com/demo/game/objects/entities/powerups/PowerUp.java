package vibe.com.demo.game.objects.entities.powerups;

import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.abstractions.MovableObject;
import vibe.com.demo.game.utils.GameConstants;

public abstract class PowerUp extends MovableObject {

    protected Timeline timeline;

    public PowerUp(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.dx = GameConstants.POWERUP_DX;
        this.dy = GameConstants.POWERUP_DY;
    }

    @Override
    public void render(GraphicsContext renderer) {
        renderer.drawImage(image, x, y, width, height);
    }

    public boolean IsOutSideGame(double gameWith) {
        return this.y + this.getHeight() >= gameWith + 20;
    }

}
