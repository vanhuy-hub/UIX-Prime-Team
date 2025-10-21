package vibe.com.demo.game.objects.factories;

import vibe.com.demo.game.objects.entities.powerups.CoinPowerUp;
import vibe.com.demo.game.objects.entities.powerups.ExpandPaddlePowerUp;
import vibe.com.demo.game.objects.entities.powerups.ExtraLifePowerUp;
import vibe.com.demo.game.objects.entities.powerups.FireBallPowerUp;
import vibe.com.demo.game.objects.entities.powerups.LaserPowerUp;
import vibe.com.demo.game.objects.entities.powerups.MagnetPowerUp;
import vibe.com.demo.game.objects.entities.powerups.MultiplyBall;
import vibe.com.demo.game.objects.entities.powerups.PowerUp;
import vibe.com.demo.game.objects.entities.powerups.PowerUpType;
import vibe.com.demo.game.objects.entities.powerups.SlowBallPowerUp;

public class PowerUpFactory {

    public static PowerUp createPowerUp(PowerUpType type, double x, double y) {
        switch (type) {
            case COIN:
                return new CoinPowerUp(x, y);
            case EXPANDPADDLE:
                return new ExpandPaddlePowerUp(x, y);
            case FIREBALL:
                return new FireBallPowerUp(x, y);
            case TURTLE:
                return new SlowBallPowerUp(x, y);
            case MAGNET:
                return new MagnetPowerUp(x, y);
            case MULTIPLYBALL:
                return new MultiplyBall(x, y);
            case HEART:
                return new ExtraLifePowerUp(x, y);
            case LASER:
                return new LaserPowerUp(x, y);
            default:
                return new CoinPowerUp(x, y);
        }
    }

}
