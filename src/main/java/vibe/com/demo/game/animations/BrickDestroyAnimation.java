package vibe.com.demo.game.animations;

import javafx.scene.paint.Color;

public class BrickDestroyAnimation extends GameAnimation {

    private Color brickColor;//lưu màu viên gạch bị vỡ 

    public BrickDestroyAnimation() {
        super(AnimationType.BRICK_DESTROY, 2);
    }

    /**
     * x,y là tâm vụ nổ , Object ... params là mảng tham số tùy chọn ~ màu gạch
     */
    @Override
    public void start(double x, double y, Object... params) {
        //xác định màu 
        if (params.length > 0 && params[0] instanceof Color) {
            brickColor = (Color) params[0];
        } else {
            brickColor = Color.AQUA;
        }

        createParticles(x, y);//tạo các mảnh vỡ 
        this.isActive = true;
        this.elapsedTime = 0;

    }

    private void createParticles(double x, double y) {
        int particleCount = 15;
        for (int i = 0; i < particleCount; i++) {
            // Tạo màu particle dựa trên màu gạch
            Color particleColor = Color.hsb(
                    brickColor.getHue() + (i * 10) % 360,//Lấy "sắc độ" màu gốc (0-360 độ)
                    brickColor.getSaturation(),// Giữ nguyên độ bão hòa màu
                    brickColor.getBrightness(),//Giữ nguyên độ sáng màu
                    0.8
            );
            BrickParticle particle = new BrickParticle(x, y, particleColor);
            particles.add(particle);
        }
    }

}
