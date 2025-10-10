package vibe.com.demo.game.objects.entities.bricks;

import javafx.scene.paint.Color;

public class UnbreakableBrick extends Brick {

    public UnbreakableBrick(double x, double y, double width, double height) {
        super(x, y, width, height, Integer.MAX_VALUE, Color.DARKGRAY);
        this.setImg("/vibe/com/demo/assets/img/brick_unbreak.png");
    }

    /**
     * Ghi de ham degredeBrick
     */
    @Override
    public Brick degradeBrick() {
        return this;
    }
}
