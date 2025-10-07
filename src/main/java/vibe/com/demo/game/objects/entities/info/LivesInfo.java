package vibe.com.demo.game.objects.entities.info;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LivesInfo extends InfoObject {

    private int lives;
    private static final int maxLives = 3;
    private static final double HEART_GAP = 10;

    public LivesInfo(double x, double y) {
        super(x, y, 200, 40);//kích thước cho lives displat
        lives = maxLives;
        title = "Lives:";
        //TODO Auto-generated constructor stub
    }

    @Override
    public void render(GraphicsContext renderer) {
        // TODO Auto-generated method stub
        //drawTitle
        renderer.setFill(Color.WHITE);
        renderer.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        renderer.fillText(title, x, y + 20);
        double textWidth = getTextWidth(renderer);
        //drawHearts
        renderer.setFill(Color.RED);
        double startX = x + textWidth;
        for (int i = 1; i <= lives; i++) {
            renderer.fillRect(startX, y + 20, 30, 10);
            startX += HEART_GAP;
        }
    }

    /**
     * Tăng mạng sống
     */
    public void gainLife() {
        if (lives < maxLives) {
            lives++;
        }
    }

    /**
     * Giảm mạng sống
     */
    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    /**
     * Check hết mạng sống
     */
    public boolean isOutOfLife() {
        return lives == 0;
    }

    /**
     * Reset
     */
    public void reset() {
        lives = maxLives;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
