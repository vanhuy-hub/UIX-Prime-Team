package vibe.com.demo.game.objects.entities.bricks;

import javafx.scene.paint.Color;

public class StrongBrick extends Brick {

    public StrongBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.setImg("/vibe/com/demo/assets/img/brick_strong.png");
    }


    @Override
    public Brick degradeBrick() {
        System.out.println("ha thap xuong normal");
        // tra ve normalbrick do bi ha cap
        return new NormalBrick(x, y, width, height);
    }
}
