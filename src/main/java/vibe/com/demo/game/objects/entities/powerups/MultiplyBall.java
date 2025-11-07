package vibe.com.demo.game.objects.entities.powerups;

import javafx.scene.image.Image;

public class MultiplyBall extends PowerUp {

    public MultiplyBall(double x, double y) {
        super(x, y, 32, 32);
        this.image = new Image(getClass().getResourceAsStream("/vibe/com/demo/assets/img/multiply_ball.png")); //TODO Auto-generated constructor stub
        //TODO Auto-generated constructor stub
    }

}
