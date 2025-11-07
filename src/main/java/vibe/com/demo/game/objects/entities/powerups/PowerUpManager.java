package vibe.com.demo.game.objects.entities.powerups;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.entities.ball.BallManager;
import vibe.com.demo.game.objects.factories.PowerUpFactory;

public class PowerUpManager {

    private List<PowerUp> powerUps;
    private double resetTime = 0;
    private static final int timeAddNewPowerUp = 4;

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(List<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    private double gameHeight;

    public PowerUpManager(double gameHeight) {
        powerUps = new ArrayList<>();
        this.gameHeight = gameHeight;
    }

    public void addPowerUp() {
        int randNumber = (int) Math.round((Math.random() * 8));

        powerUps.add(PowerUpFactory.createPowerUp(randNumber, 50 + Math.random() * 650, -50));
    }

    public void render(GraphicsContext gc) {
        powerUps.forEach(powerUp -> powerUp.render(gc));
    }

    public void update(BallManager ballManager) {
        powerUps.removeIf(powerup -> powerup.IsOutSideGame(gameHeight));
        System.out.println(powerUps.size());
        powerUps.forEach(PowerUp::update);
        resetTime += (double) 1 / 100;
        if (resetTime > timeAddNewPowerUp && ballManager.isIsActive()) {
            resetTime = 0;
            System.out.println("them power up");
            addPowerUp();
        }
    }

    public void clear() {
        resetTime = 0;
        powerUps.clear();
    }

}
