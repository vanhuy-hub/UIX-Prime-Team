package vibe.com.demo.game.objects.entities.paddle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import vibe.com.demo.game.objects.abstractions.MovableObject;
import vibe.com.demo.game.utils.GameConstants;
//paddle chỉ di truyển sang trái hoặc phải nên chỉ cần tác động vào giá trị dx, t cộng speed / -speed 

public class Paddle extends MovableObject {

    private double speed;
    private Color color;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isDisapper;

    // timeline để kiểm soát shrink 
    private Timeline shrinkTimeLine;

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
        speed = GameConstants.PADDLE_BASE_SPEED;
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

    public void disappear() {
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

    }

    public void expand() {
        if (this.width == GameConstants.PADDLE_NORMAL_WIDTH) {
            this.width = GameConstants.PADDLE_EXPAND_WIDTH;
            this.x = this.x - (GameConstants.PADDLE_EXPAND_WIDTH - GameConstants.PADDLE_NORMAL_WIDTH) / 2;
        }
        startShrinkTimeline();
    }

    public void startShrinkTimeline() {
        if (shrinkTimeLine != null) {
            shrinkTimeLine.stop();
        }
        shrinkTimeLine = new Timeline(
                new KeyFrame(Duration.seconds(GameConstants.EXPAND_PADDLE_DURATION), e -> {
                    this.shrink();
                })
        );
        shrinkTimeLine.play();
    }

    public void shrink() {
        if (this.width == GameConstants.PADDLE_EXPAND_WIDTH) {
            this.width = GameConstants.PADDLE_NORMAL_WIDTH;
            this.x = this.x + (GameConstants.PADDLE_EXPAND_WIDTH - GameConstants.PADDLE_NORMAL_WIDTH) / 2;
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

    public void setImgFromURL(String path) {
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/" + path + ""));
    }

}
