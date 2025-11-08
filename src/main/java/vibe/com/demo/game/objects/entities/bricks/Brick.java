package vibe.com.demo.game.objects.entities.bricks;

import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.abstractions.GameObject;

public abstract class Brick extends GameObject {

    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.clearRect(x, y, getWidth(), getHeight());
        gc.drawImage(image, x, y);

    }

    /**
     * Hàm trả về Brick ở mức hạ thấp hơn , các hàm con có thể kế thừa (đặc biệt
     * là lop Strong)
     */
    public Brick degradeBrick() {
        return null;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
