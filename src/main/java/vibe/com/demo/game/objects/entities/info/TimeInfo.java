package vibe.com.demo.game.objects.entities.info;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TimeInfo extends InfoObject {

    public TimeInfo(double x, double y) {

        super(x, y, 200, 40);
        title = "Time :";
        currentTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        //TODO Auto-generated constructor stub
    }

    private long currentTime;
    private long startTime;
    private boolean running;

    @Override
    public void render(GraphicsContext renderer) {
        // TODO Auto-generated method stub
        renderer.setFill(Color.WHITE);
        renderer.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        renderer.fillText(title, x, y + 20);
        double textWidth = getTextWidth(renderer);
        double startX = textWidth + x;
        long time = getTimeValue();
        renderer.fillText(String.valueOf(time), startX, y + 20);
    }

    public long getTimeValue() {
        return (currentTime - startTime) / 1000;
    }

    @Override
    public void update() {
        System.out.println("ben ngoai");
        if (running) {//khi nào game tiếp tục mới cập nhật thời gian hiện tại 
            System.out.println("van chay");
            currentTime = System.currentTimeMillis();
        }
    }

    public void reset() {
        currentTime = startTime;
    }

    public void pauseTime() {
        running = false;
    }

    public void play() {
        startTime = System.currentTimeMillis() - 1000 * getTimeValue();//cập nhật lại start time sau khi dừng được 1 khoảng thời gian 
        running = true;
    }
}
