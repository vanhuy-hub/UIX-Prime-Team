package vibe.com.demo.game.objects.entities.paddle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import vibe.com.demo.game.objects.abstractions.MovableObject;
//paddle chỉ di truyển sang trái hoặc phải nên chỉ cần tác động vào giá trị dx, t cộng speed / -speed 

public class Paddle extends MovableObject {

    private double speed = 6;
    private Color color;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;

    private Image paddleImg;

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
        speed = 4;
        this.color = Color.ANTIQUEWHITE;
        paddleImg = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/paddle3.png"));
    }

    public void moveLeft() {
        isMovingLeft = true;
    }

    public void moveRight() {
        isMovingRight = true;
    }

    public void stopLeft() {
        isMovingLeft = false;
    }

    public void stopRight() {
        isMovingRight = false;

    }

    @Override
    public void render(GraphicsContext renderer) {
        renderer.clearRect(x, y, getWidth(), getHeight());
        renderer.drawImage(paddleImg, x, y);
    }

    @Override
    public void update() {
        this.dx = 0;//reset trước rồi mới cập nhật 

        if (isMovingLeft && !isMovingRight) {
            System.out.println("move left in update" + speed);
            dx = -speed;
        } else if (!isMovingLeft && isMovingRight) {
            System.out.println("move right in update" + speed);
            dx = speed;
        }

        //gọi đến hàm update của cha để cập nhật vị trí 
        super.update();
        //giữ để paddle không vượt quá giới hạn 
        if (this.x < 0) {
            this.x = 0;
        }
    }

    /**
     * Hàm dừng việc di chuyển của paddle~ điều chỉnh
     */
    public void stopMoving() {
        dx = 0;//gán tốc độ dx bằng 0 chứ đừng gán speed (hằng số vận tôc)
        stopLeft();
        stopRight();
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

    public Image getPaddleImg() {
        return paddleImg;
    }

    public void setPaddleImgFromURL(String url) {
        this.paddleImg = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/" + url + ""));

    }

}
