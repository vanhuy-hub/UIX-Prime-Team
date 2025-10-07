package vibe.com.demo.game.objects.entities.info;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import vibe.com.demo.game.objects.abstractions.GameObject;

public abstract class InfoObject extends GameObject {

    protected String title;

    public InfoObject(double x, double y, double width, double height) {
        super(x, y, width, height);
        title = "";
    }

    public double getTextWidth(GraphicsContext renderer){
        Text textNode = new Text(this.title);
        textNode.setFont(renderer.getFont());
        return  textNode.getLayoutBounds().getWidth();
    }
}
