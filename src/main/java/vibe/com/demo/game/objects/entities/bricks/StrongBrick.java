package vibe.com.demo.game.objects.entities.bricks;

import javafx.scene.paint.Color;

public class StrongBrick extends Brick {

    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 2, Color.DODGERBLUE);
    }

    @Override
    public Brick degradeBrick() {
        System.out.println("ha thap xuong normal");
        return new NormalBrick(x, y, width, height);
    }
}
