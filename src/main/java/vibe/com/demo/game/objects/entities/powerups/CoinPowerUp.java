package vibe.com.demo.game.objects.entities.powerups;

import javafx.scene.image.Image;

public class CoinPowerUp extends PowerUp {

    public CoinPowerUp(double x, double y) {
        super(x, y, 32, 32);
        this.setImg("/vibe/com/demo/assets/img/coin.png");
    }

}
