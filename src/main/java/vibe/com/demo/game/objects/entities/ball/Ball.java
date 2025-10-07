package vibe.com.demo.game.objects.entities.ball;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vibe.com.demo.game.objects.abstractions.MovableObject;
import vibe.com.demo.game.objects.entities.paddle.Paddle;

public class Ball extends MovableObject {

    private Color color;
    private double speed;
    private boolean isActive;//kiểm tra trạng thái của bóng có hoạt động hay không 

    public boolean isActive() {
        return isActive;
    }

    public Ball(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);//do lớp cha chỉ có hàm khởi tạo có tham số, nên lớp con bắt buộc phải có 1 hàm khởi tạo và gọi đến hàm khởi tạo của hàm cha bằng từ khóa super (Params...)
        this.color = Color.WHITE;
        speed = 4;
        this.isActive = false;
    }

    @Override
    public void render(GraphicsContext renderer) {
        renderer.setFill(Color.GRAY);
        renderer.fillOval(x, y, width, height);//vẽ 1 hình tròn 
        // Add a shine effect ~ thêm hiệu ứng ánh sáng  
        renderer.setFill(Color.rgb(255, 255, 255, 0.7));
        renderer.fillOval(x + width * 0.3, y + height * 0.2, width * 0.4, height * 0.4);
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
        y = paddle.getY() - this.height - 5;

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
