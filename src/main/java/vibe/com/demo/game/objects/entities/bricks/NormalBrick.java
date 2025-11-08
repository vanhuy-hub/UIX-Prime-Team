package vibe.com.demo.game.objects.entities.bricks;

public class NormalBrick extends Brick {

    public NormalBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.setImg("/vibe/com/demo/assets/img/brick_normal.png");
    }

    /**
     * Ghi de ham degredeBrick
     */
    @Override
    public Brick degradeBrick() {
        return null;
    }
}
