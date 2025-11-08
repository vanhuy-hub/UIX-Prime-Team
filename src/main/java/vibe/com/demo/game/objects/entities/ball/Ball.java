package vibe.com.demo.game.objects.entities.ball;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.abstractions.MovableObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.game.utils.GameConstants;

public class Ball extends MovableObject {

    private double speed;
    private boolean isActive;//kiểm tra trạng thái của bóng có hoạt động hay không 
    private boolean isPowerFull;
    private Timeline fireBallTimeline;

    public boolean isActive() {
        return isActive;
    }

    public Ball(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);//do lớp cha chỉ có hàm khởi tạo có tham số, nên lớp con bắt buộc phải có 1 hàm khởi tạo và gọi đến hàm khởi tạo của hàm cha bằng từ khóa super (Params...)
        speed = GameConstants.BALL_BASE_SPEED;
        this.isActive = false;
        this.isPowerFull = false;
        setBallImg();

    }

    public void setBallImg() {
        if (!this.isPowerFull) {
            this.setImg("/vibe/com/demo/assets/img/ball_normal.png");
        } else {
            this.setImg("/vibe/com/demo/assets/img/ball_fire.png");
        }
    }

    @Override
    public void render(GraphicsContext renderer) {
        renderer.drawImage(image, x, y, this.width, this.height);
    }

    @Override
    public void update() {
        if (this.isActive) {
            super.update();//gọi đến phương thức update của thằng Movalable
        }
    }

    public boolean isUpState() {
        return dy < 0;
    }

    public boolean isDownState() {
        return dy > 0;
    }

    public boolean isRightState() {
        return dx > 0;
    }

    public boolean isLeftState() {
        return dx < 0;
    }

    /**
     * Đảo chiều vận tốc theo phương ngang x
     */
    public void bounceHorizontal() {
        dx = -dx;//dx sẽ =-dx
    }

    /**
     * Đảo chiều vận tốc theo phương thẳng đứng y
     */
    public void bounceVertical() {
        dy = -dy;//đổi chiều dy
    }

    //phương thức reset(khi chơi lại từ đầu)
    /**
     * hàm Reset tọa độ của quả bóng để bóng luôn dính vào paddle khi mất bóng
     * lần thứ nhất , lần thứ và khi gameReady.
     *
     * @param paddle
     */
    public void reset(Paddle paddle) {
        setRandomVeclocity();
        isActive = false;//dừng trạng thái nảy 
        x = paddle.getX() + paddle.getWidth() / 2 - this.width / 2;
        y = paddle.getY() - this.height - 1;

    }

    /**
     * Tăng vận tốc bóng .
     *
     * @param increase
     */
    public void increaseVeclocity(double increase) {
        this.dx *= increase;
        this.dy *= increase;
    }

    /**
     * Giảm tốc độ bóng.
     *
     * @param decrease
     */
    public void decreaseVeclocity(double decrease) {
        this.dx /= decrease;
        this.dy /= decrease;
    }

    public void setRandomVeclocity() {
        setVelocity((Math.random() * 8) - GameConstants.BALL_BASE_SPEED, -GameConstants.BALL_BASE_SPEED);
    }

    //phuong thuc hoat dong, do quả bóng sẽ nảy liên tùng tục trong game 
    /**
     * Kích hoạt trạng thái hoạt động của quả bóng
     */
    public void launch() {
        setRandomVeclocity();
        isActive = true;

    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public double getSpeed() {
        return speed;
    }

    //=====Power up 
    public boolean isPowerFull() {
        return isPowerFull;
    }

    public void setIsPowerFull(boolean isPowerFull) {
        this.isPowerFull = isPowerFull;
    }

    public void fireBallActive() {
        this.isPowerFull = true;
        setBallImg();
        if (fireBallTimeline != null) {
            fireBallTimeline.stop();
        }
        fireBallTimeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(GameConstants.FIRE_BALL_DURATION), e -> {
                    this.isPowerFull = false;
                    setBallImg();
                })
        );
        fireBallTimeline.play();
    }

}
