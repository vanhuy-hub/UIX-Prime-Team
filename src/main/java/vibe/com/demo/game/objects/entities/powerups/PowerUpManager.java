package vibe.com.demo.game.objects.entities.powerups;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.factories.PowerUpFactory;

public class PowerUpManager {

    private List<PowerUp> powerUps;

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

    public void addPowerUp(PowerUpType type, double x, double y) {
        powerUps.add(PowerUpFactory.createPowerUp(type, x, y));
    }

    public void render(GraphicsContext gc) {
        powerUps.forEach(powerUp -> powerUp.render(gc));
    }

    public void update() {
        powerUps.removeIf(powerup -> powerup.IsOutSideGame(gameHeight));
        System.out.println(powerUps.size());
        powerUps.forEach(PowerUp::update);
    }

}
