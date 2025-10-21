package vibe.com.demo.game.objects.entities.powerups;

import javafx.scene.image.Image;

public class LaserPowerUp extends PowerUp {

    public LaserPowerUp(double x, double y) {
        super(x, y, 40, 40);
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assests/img/gun.png"));
        //TODO Auto-generated constructor stub
    }

}
