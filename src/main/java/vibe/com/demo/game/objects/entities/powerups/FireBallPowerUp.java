package vibe.com.demo.game.objects.entities.powerups;

import javafx.scene.image.Image;

public class FireBallPowerUp extends PowerUp {

    public FireBallPowerUp(double x, double y) {
        super(x, y, 40, 40);
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/fire.png")); //TODO Auto-generated constructor stub
    }

}
