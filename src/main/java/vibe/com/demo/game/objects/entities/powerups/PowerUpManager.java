package vibe.com.demo.game.objects.entities.powerups;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import vibe.com.demo.game.objects.entities.ball.BallManager;
import vibe.com.demo.game.objects.factories.PowerUpFactory;
import vibe.com.demo.game.utils.GameConstants;

public class PowerUpManager {

    private List<PowerUp> powerUps;
    /**
     * timeline (sau mỗi ..giây sẽ thêm 1 powerup mới)
     */
    private Timeline timer;

    public PowerUpManager() {
        powerUps = new ArrayList<>();
        setupTimer();
    }

    /**
     * thiết lập timer cứ duration giây thì thêm power up.
     */
    public void setupTimer() {
        double duration = GameConstants.POWERUP_DURATION;
        timer = new Timeline(new KeyFrame(Duration.seconds(duration), e -> addPowerUp()));
        timer.setCycleCount(Timeline.INDEFINITE);//lặp mãi 
    }

    /**
     * Thêm power up.
     */
    public void addPowerUp() {
        int randNumber = (int) Math.round((Math.random() * 6));
        double positionX = 50 + Math.random() * (GameConstants.GAME_WIDTH - 100);
        double positionY = -100;
        powerUps.add(PowerUpFactory.createPowerUp(5, positionX, positionY));
    }

    public void render(GraphicsContext gc) {
        powerUps.forEach(powerUp -> powerUp.render(gc));
    }

    public void update() {
        powerUps.removeIf(powerup -> powerup.IsOutSideGame(GameConstants.GAME_HEIGHT));
        powerUps.forEach(PowerUp::update);
    }

    /**
     * Bắt đầu timer , gọi khi khởi tạo gamemanager.
     */
    public void startTimer() {
        timer.play();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void clear() {
        stopTimer();
        powerUps.clear();
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(List<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }
}
