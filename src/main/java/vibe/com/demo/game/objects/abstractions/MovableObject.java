package vibe.com.demo.game.objects.abstractions;
//lớp trừu tượng cho các thực thể di chuyển được như paddle, ball 
//được kế thừa từ GameObject

public abstract class MovableObject extends GameObject {

    protected double dx = 0;//toc do theo phuong x
    protected double dy = 0;//toc do theo phuong y

    public MovableObject(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        this.x += dx;
        this.y += dy;
    }

    //phuong thuc de cap nhat lai toc do
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

}
