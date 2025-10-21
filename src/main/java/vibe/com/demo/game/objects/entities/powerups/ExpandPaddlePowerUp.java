package vibe.com.demo.game.objects.entities.powerups;

import javafx.scene.image.Image;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y, 54, 54);
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/measure.png"));
    }

}
