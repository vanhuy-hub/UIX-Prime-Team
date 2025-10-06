package vibe.com.demo.game.objects.entities.bricks;

import javafx.scene.paint.Color;

public class NormalBrick extends Brick {

    public NormalBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, Color.RED);
    }
    /**
     * Ghi de ham degredeBrick 
     */
    @Override
     public Brick degradeBrick() {
        return null;
    }
}
