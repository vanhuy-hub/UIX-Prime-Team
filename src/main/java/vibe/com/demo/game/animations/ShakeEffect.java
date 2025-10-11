package vibe.com.demo.game.animations;

/**
 * Hiệu ứng rung cho các đối tượng
 */
public class ShakeEffect {

    private double originalX, originalY; //vị trí gốc : trước khi rung 
    private double shakeIntensity; //cường độ rung 
    private double shakeDecay;//suy giảm : rung yếu dần
    private boolean isActive;//trạng thái hoạt động  

    public ShakeEffect(double orginalX, double originalY) {
        this.originalX = originalX;
        this.originalY = originalY;
        this.isActive = false;
    }

    /**
     * Hàm bắt đầu animation rung lắc với tham số là cường độ rung và độ suy
     * giảm
     */
    public void start(double intensity, double decay) {
        this.shakeIntensity = intensity;
        this.shakeDecay = decay;
        this.isActive = true;
    }

    /**
     * Hàm update (giảm dần hiệu ứng rung ~ shakeInsentity giảm )
     */
    public void update() {
        if (!isActive) {
            return;
        }
        shakeIntensity *= shakeDecay;//giảm cường độ rung bằng cách nhân nó với độ suy giảm 
        if (shakeIntensity < 0.1) {
            shakeIntensity = 0;
            this.isActive = false;
        }
    }

    public double getShakeX() {
        if (!isActive) {
            return originalX;
        }
        return originalX + (Math.random() - 0.5) * shakeIntensity;
    }

    public double getShakeY() {
        if (!isActive) {
            return originalY;
        }
        return originalY + (Math.random() - 0.5) * shakeIntensity;
    }

    public boolean isActive() {
        return isActive;
    }

    /*
     * hàm stop shake effect 
     */
    public void stop() {
        isActive = false;
        shakeIntensity = 0;
    }
}
