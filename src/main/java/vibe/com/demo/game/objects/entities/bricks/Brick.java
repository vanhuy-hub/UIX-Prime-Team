package vibe.com.demo.game.objects.entities.bricks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vibe.com.demo.game.objects.abstractions.GameObject;

public abstract class Brick extends GameObject {

    private boolean destroyed;
    private Color color;
    private int maxHitPoints;
    private int hitPoint;

    public Brick(double x, double y, double width, double height, int maxHitPoints, Color color) {
        super(x, y, width, height);

        this.destroyed = false;
        this.maxHitPoints = maxHitPoints;
        this.hitPoint = 0;
        this.color = color;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            drawBrick(gc);
            drawBorder(gc);
        }
    }

    /**
     * Vẽ brick chính
     */
    public void drawBrick(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);
        //hiệu ứng 3d 
        gc.setFill(color.brighter());
        gc.fillRect(x, y, width, 2); // Top highlight
        gc.fillRect(x, y, 2, height); // Left highlight

        // Shadow dưới và phải
        gc.setFill(color.darker());
        gc.fillRect(x, y + height - 2, width, 2); // Bottom shadow
        gc.fillRect(x + width - 2, y, 2, height); // Right shadow
    }

    /**
     * Vẽ Border
     */
    public void drawBorder(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(x, y, width, height);
    }

    /**
     * Xử lý khi bị ball đánh trúng - TRẢ VỀ BRICK MỚI NẾU CÓ DEGRADATION
     */
    public Brick takeHit() {
        this.hitPoint++;
        if (hitPoint == maxHitPoints) {
            destroy();
            return null;
        } else {
            System.out.println("ha thap brick");
            return (degradeBrick() != null) ? degradeBrick() : this;
        }
    }

    /**
     * Hàm trả về Brick ở mức hạ thấp hơn , các hàm con có thể kế thừa (đặc biệt
     * là lop Strong)
     */
    protected Brick degradeBrick() {
        return null;
    }

    /**
     * Hàm phá hủy gạch
     */
    public void destroy() {
        this.destroyed = true;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

}
