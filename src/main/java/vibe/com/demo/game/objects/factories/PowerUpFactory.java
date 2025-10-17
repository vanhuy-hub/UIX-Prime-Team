package vibe.com.demo.game.objects.factories;

import vibe.com.demo.game.objects.entities.powerups.CoinPowerUp;
import vibe.com.demo.game.objects.entities.powerups.PowerUp;
import vibe.com.demo.game.objects.entities.powerups.PowerUpType;

public class PowerUpFactory {

    public static PowerUp createPowerUp(PowerUpType type, double x, double y) {
        switch (type) {
            case COIN:
                return new CoinPowerUp(x, y);
            default:
                return new CoinPowerUp(x, y);
        }
    }

}
