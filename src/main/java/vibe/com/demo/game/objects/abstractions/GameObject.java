package vibe.com.demo.game.objects.abstractions;
//lớp trừu tượng GameObject: là cơ sở cho tất cả đối tượng trong game có vị trí, kích thược và có thể vẽ được trên màn hình : 

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {

    protected double x;//xPosition
    protected double y;//yPosition
    protected double width;
    protected double height;

    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(GraphicsContext renderer);//lớp trừu tượng cần được ghi đè bởi các thực thể

    public abstract void update();//phương thức để cập nhật thực thể : như vị trí, ... 

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    //Collision detection
    public boolean collidesWith(GameObject other) {
        double minR = Math.min(x + width, other.x + other.getWidth());
        double maxL = Math.min(x, other.x);
        double maxT = Math.max(y, other.y);
        double minB = Math.min(y + height, other.y + other.getHeight());
        return minR >= maxL && maxT <= minB;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);//trả về hình chữ nhật 
    }
}
