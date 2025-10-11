package vibe.com.demo.game.animations;

import javafx.scene.paint.Color;

/**
 * Mảnh vỡ của gạch ~ kế thừa Particle
 */
public class BrickParticle extends Particle {

    private double rotation;//góc xoay : để mảnh vỡ xoay tròn 
    private double rotationSpeed; // tốc độ xoay : bao nhiệu độ/frame 

    public BrickParticle(double x, double y, Color brickColor) {
        super(x, y, brickColor, 3 + Math.random() * 6);//phải gọi hàm super ở đỉnh 
        this.vx = (Math.random() - 0.5) * 8;
        this.vy = (Math.random() - 0.5) * 8;
        this.rotation = Math.random() * 360;
        this.rotationSpeed = (Math.random() - 0.5) * 20;
    }

    @Override
    public void update() {
        x += vy;
        y += vy;
        vy += 0.3;//lực hút trái đất 
        life -= 0.04;
        rotation += rotationSpeed;
        vx *= 0.98;
        vy *= 0.98;
    }

}
