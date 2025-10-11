package vibe.com.demo.game.animations;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

/**
 * Lớp cơ sở cho các animation ~ bộ khung cho các hiệu ứng animation như nổ
 * gạch, nổ paddle
 */
public abstract class GameAnimation {

    protected List<Particle> particles;//danh sách các hạt / mảnh vỡ 
    protected boolean isActive;
    protected AnimationType type;//loại animation 
    protected double duration;//thời gian 
    protected double elapsedTime;//thời gian tự động tắt 

    public GameAnimation(AnimationType type, double duration) {
        this.particles = new ArrayList<>();
        this.isActive = false;
        this.type = type;
        this.duration = duration;
        this.elapsedTime = 0;//reset đồng hồ đếm ngược về 0    }
    }

    /**
     * hàm start cần phải ghi đè ở mọi Animation cụ thể, tham số thứ 3 là mảng
     * params (tham số )
     */
    public abstract void start(double x, double y, Object... params);

    public void update() {
        if (!isActive) {
            return;
        }
        // elapsedTime += 1 / 60;//giả sử 60FPS ~ 1 giây tải được 60 frame ~mỗi frame tăng thêm ~ 0.0167s
        particles.forEach(Particle::update);//từng hạt được update thông qua foreach 
        particles.removeIf(Particle::isDead);//dọn dẹp particle khi hết tuổi thọ 
    }

    /**
     * Phương thức vẽ
     */
    public void render(GraphicsContext gc) {
        if (!isActive) {
            return;
        }
        for (Particle particle : particles) {
            double alpha = particle.getLife();//lấy độ trong suốt ~ chính là vòng đời của hạt 
            gc.setGlobalAlpha(alpha);
            gc.setFill(particle.getColor());   // Chọn màu vẽ
            gc.fillOval( // Vẽ hình oval (hạt)
                    particle.getX() - particle.getSize() / 2, // Tọa độ X (căn giữa)
                    particle.getY() - particle.getSize() / 2, // Tọa độ Y (căn giữa)
                    particle.getSize(), // Chiều rộng
                    particle.getSize() // Chiều cao
            );
        }
        gc.setGlobalAlpha(1.0); // Reset về không trong suốt
    }

    /**
     * Hàm dừng animation ~ dọn dẹp hoàn toàn
     */
    public void stop() {
        isActive = false;
        particles.clear();
        elapsedTime = 0;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
