package vibe.com.demo.game.objects.entities.powerups;

import javafx.scene.image.Image;

public class ExtraLifePowerUp extends PowerUp {

    public ExtraLifePowerUp(double x, double y) {
        super(x, y, 34, 34);
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/heart.png"));
        //TODO Auto-generated constructor stub

    }

}
