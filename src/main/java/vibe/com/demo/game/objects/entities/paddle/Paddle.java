package vibe.com.demo.game.objects.entities.paddle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vibe.com.demo.game.objects.abstractions.MovableObject;
//paddle chỉ di truyển sang trái hoặc phải nên chỉ cần tác động vào giá trị dx, t cộng speed / -speed 

public class Paddle extends MovableObject {

    private double speed;
    private Color color;
    private boolean isMovingLeft;
    private boolean isMovingRight;

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.speed = 4;
        this.color = Color.ANTIQUEWHITE;
    }

    public void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;//stop move right
    }

    public void moveRight() {
        isMovingRight = true;
        isMovingLeft = false;//stop move left
    }

    @Override
    public void render(GraphicsContext renderer) {
        renderer.setFill(color);//xét màu
        renderer.fillRoundRect(x, y, width, height, 15, 15); //2 giá trị cuối cùng là giá trị bor góc (bán kính bo tròn theo chiều ngang và dọc) 

        renderer.setStroke(Color.WHITE);//màu border
        renderer.setLineWidth(2);//độ dày viền 
        renderer.strokeRoundRect(x, y, width, height, 15, 15);//để xét đúng vị trí hình chữ nhật mà ta phủ màu trước đó 
    }

    @Override
    public void update() {
        if (isMovingLeft) {
            dx = -speed;
        } else if (isMovingRight) {
            dx = speed;
        } else {
            dx = 0;//dừng di chuyển 
        }
        super.update();//gọi đến phương thức update của thằng Movalable 
        //giữ để paddle không vượt quá giới hạn 
        if (this.x < 0) {
            this.x = 0;
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public void setMovingLeft(boolean isMovingLeft) {
        this.isMovingLeft = isMovingLeft;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean isMovingRight) {
        this.isMovingRight = isMovingRight;
    }

}
