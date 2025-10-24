package vibe.com.demo.game.objects.factories;

import vibe.com.demo.game.objects.entities.powerups.CoinPowerUp;
import vibe.com.demo.game.objects.entities.powerups.ExpandPaddlePowerUp;
import vibe.com.demo.game.objects.entities.powerups.ExtraLifePowerUp;
import vibe.com.demo.game.objects.entities.powerups.FireBallPowerUp;
import vibe.com.demo.game.objects.entities.powerups.LaserPowerUp;
import vibe.com.demo.game.objects.entities.powerups.MagnetPowerUp;
import vibe.com.demo.game.objects.entities.powerups.MultiplyBall;
import vibe.com.demo.game.objects.entities.powerups.PowerUp;
import vibe.com.demo.game.objects.entities.powerups.SlowBallPowerUp;

public class PowerUpFactory {

    public static PowerUp createPowerUp(int number, double x, double y) {
        switch (number) {
            case 1:
                return new CoinPowerUp(x, y);
            case 2:
                return new ExpandPaddlePowerUp(x, y);
            case 3:
                return new FireBallPowerUp(x, y);
            case 4:
                return new SlowBallPowerUp(x, y);
            case 5:
                return new MagnetPowerUp(x, y);
            case 6:
                return new MultiplyBall(x, y);
            case 7:
                return new ExtraLifePowerUp(x, y);
            case 8:
                return new LaserPowerUp(x, y);
            default:
                return new CoinPowerUp(x, y);
        }
    }

}
