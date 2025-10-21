package vibe.com.demo.game.objects.entities.powerups;

import javafx.scene.image.Image;

public class MultiplyBall extends PowerUp {

    public MultiplyBall(double x, double y) {
        super(x, y, 40, 40);
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assests/img/x2.png"));
        //TODO Auto-generated constructor stub
    }

}
