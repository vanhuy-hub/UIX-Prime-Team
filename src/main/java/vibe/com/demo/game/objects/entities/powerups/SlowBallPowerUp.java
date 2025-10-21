package vibe.com.demo.game.objects.entities.powerups;

import javafx.scene.image.Image;

public class SlowBallPowerUp extends PowerUp {

    public SlowBallPowerUp(double x, double y) {
        super(x, y, 44, 44);
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/turtle.png"));
        //TODO Auto-generated constructor stub
    }

}
