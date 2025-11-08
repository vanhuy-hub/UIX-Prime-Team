package vibe.com.demo.game.objects.entities.bricks;

import javafx.scene.paint.Color;

public class ExplosiveBrick extends Brick {

    public ExplosiveBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.setImg("/vibe/com/demo/assets/img/brick_bom.png");
    }

}
