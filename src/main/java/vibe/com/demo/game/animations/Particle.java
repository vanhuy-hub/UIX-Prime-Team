package vibe.com.demo.game.animations;

import javafx.scene.paint.Color;

/**
 * Lớp abstract~ nguyên tử của hiệu ứng ~ là lớp base của các animation nổ tương
 * ứng hạt nhỏ để xây dựng các hiệu ứng hình ảnh phức tạp , ví dụ như hiểu ứng
 * nổ gạch thì bay ra các hạt ... , các animation khác như Ẽplosive có thể có
 * nhiều hạt để tạo hiệu ứng nổ phức tạp hơn
 */
public abstract class Particle {

    protected double x, y; //vị trí : tọa độ trên màn hình 
    protected double vx, vy;   //chuyển động : tốc dộ theo truc x,y 
    protected double life;//vòng đời 1->0  (1:mới , 0 : chết ) , ~ bản chất nó sẽ tương ứng với độ trong suốt 
    protected double size;//kích thước 
    protected Color color;//màu sắc

    public Particle(double x, double y, Color color, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.life = 1;
    }

    /**
     * Hàm trừu tượng update (các lớp con bắt buộc phải ghi đè)
     */
    public abstract void update();

    public boolean isDead() {
        return life <= 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
