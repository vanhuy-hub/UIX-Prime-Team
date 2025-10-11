package vibe.com.demo.game.objects.entities.ball;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import vibe.com.demo.game.objects.abstractions.MovableObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;

public class Ball extends MovableObject {

    private Color color;
    private double speed;
    private boolean isActive;//kiểm tra trạng thái của bóng có hoạt động hay không 
    private Image img;

    public boolean isActive() {
        return isActive;
    }

    public Ball(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);//do lớp cha chỉ có hàm khởi tạo có tham số, nên lớp con bắt buộc phải có 1 hàm khởi tạo và gọi đến hàm khởi tạo của hàm cha bằng từ khóa super (Params...)
        this.color = Color.WHITE;
        speed = 4;
        this.isActive = false;
        this.img = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/ball2.png"));
    }

    @Override
    public void render(GraphicsContext renderer) {
        renderer.clearRect(0, 0, width, height);
        renderer.drawImage(img, x, y);
        renderTrail(renderer);
    }

    public void renderTrail(GraphicsContext renderer) {
        //b1 : phủ 1 lớp bán trong suốt để làm mờ dần hình cũ 
        renderer.setFill(Color.rgb(1, 1, 1, 1));
        renderer.fillRect(0, 0, width, height);

        //B2 : Vẽ lại quả bóng ở vị trí hiện tại 
        renderer.setFill(Color.rgb(1, 1, 1, 1));
        renderer.fillOval(x, y, width / 2, width / 2);
    }

    @Override
    public void update() {
        if (this.isActive) {
            super.update();//gọi đến phương thức update của thằng Movalable 
        }
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
     * Gọi isActive=false, đồng thời chỉnh lại vị trí của ball
     */
    public void reset(Paddle paddle) {

        isActive = false;//dừng trạng thái nảy 
        x = paddle.getX() + paddle.getWidth() / 2 - this.width / 2;
        y = paddle.getY() - this.height - 1;

    }

    public void setRandomVeclocity() {
        setVelocity((Math.random() * 8) - 4, -4);
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
}
