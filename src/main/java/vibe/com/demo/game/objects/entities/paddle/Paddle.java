package vibe.com.demo.game.objects.entities.paddle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import vibe.com.demo.game.objects.abstractions.MovableObject;
//paddle chỉ di truyển sang trái hoặc phải nên chỉ cần tác động vào giá trị dx, t cộng speed / -speed 

public class Paddle extends MovableObject {

    private double speed = 6;
    private Color color;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    public static final int PADDLE_NORMAL_WIDTH = 140;
    public static final int PADDLE_NORMAL_HEIGHT = 40;
    public static final int PADDLE_EXPAND_WIDTH = 180;
    private boolean isDisapper;

    // timeline để kiểm soát shrink 
    private Timeline shrinkTimeLine;

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
        speed = 4;
        this.color = Color.ANTIQUEWHITE;
        image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/paddle3.png"));
        isDisapper = false;
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

    public void disapper() {
        this.isDisapper = true;
    }

    @Override
    public void render(GraphicsContext renderer) {
        if (isDisapper) {
            return;
        }
        renderer.drawImage(image, x, y, getWidth(), getHeight());
    }

    @Override
    public void update() {
        if (isDisapper) {
            return;
        }
        this.dx = 0;//reset trước rồi mới cập nhật 

        if (isMovingLeft && !isMovingRight) {

            dx = -speed;
        } else if (!isMovingLeft && isMovingRight) {

            dx = speed;
        }

        //gọi đến hàm update của cha để cập nhật vị trí 
        super.update();
        //giữ để paddle không vượt quá giới hạn 
        if (this.x < 0) {
            this.x = 0;
        }
    }

    public void expand() {
        if (this.width == PADDLE_NORMAL_WIDTH) {
            this.width = PADDLE_EXPAND_WIDTH;
            this.x = this.x - (PADDLE_EXPAND_WIDTH - PADDLE_NORMAL_WIDTH) / 2;
        }
        startShrinkTimeline();
    }

    public void startShrinkTimeline() {
        if (shrinkTimeLine != null) {
            shrinkTimeLine.stop();
        }
        shrinkTimeLine = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    this.shrink();
                })
        );
        shrinkTimeLine.play();
    }

    public void shrink() {
        if (this.width == PADDLE_EXPAND_WIDTH) {
            this.width = PADDLE_NORMAL_WIDTH;
            this.x = this.x + (PADDLE_EXPAND_WIDTH - PADDLE_NORMAL_WIDTH) / 2;
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

    public Image getImagemage() {
        return image;
    }

    public void setImgFromURL(String url) {
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/" + url + ""));
    }

}
