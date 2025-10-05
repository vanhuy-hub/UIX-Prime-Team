package vibe.com.demo.game.objects.entities.overlay;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import vibe.com.demo.game.objects.abstractions.GameObject;

/**
 * Overlay là đối tượng để GameManager quản lý
 */
public class OverlayObject extends GameObject {

    private boolean visible;

    private String text;
    private Color textColor;

    public OverlayObject(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.textColor = Color.WHITE;
        text = "";
        visible = false;
    }

    @Override
    public void render(GraphicsContext renderer) {
        // TODO Auto-generated method stub
        if (this.visible) {
            renderBackground(renderer);
            renderText(renderer);
        }

    }

    public void renderBackground(GraphicsContext renderer) {
        // Background gradient
        LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(0, 0, 0, 0.7)),
                new Stop(1, Color.rgb(30, 41, 59, 0.8))
        );

        renderer.setFill(gradient);
        renderer.fillRoundRect(x, y, width, height, 20, 20);

        // Border
        renderer.setStroke(Color.rgb(255, 255, 255, 0.1));
        renderer.setLineWidth(1);
        renderer.strokeRoundRect(x, y, width, height, 20, 20);

        // Shadow effect
        renderer.setFill(Color.rgb(0, 0, 0, 0.3));
        renderer.fillRoundRect(x + 4, y + 8, width, height, 20, 20);
    }

    public void renderText(GraphicsContext renderer) {
        if (text == null || text.trim().equals("")) {
            return;
        }

        renderer.setFill(textColor);
        renderer.setFont(Font.font("System", FontWeight.BOLD, 24));

        //Center text
        //lấy độ rộng của text 
        double textWidth = getTextWidth(renderer);
        double textX = x + (width - textWidth) / 2;
        double textY = y + height / 2;

        //text shadow 
        renderer.setFill(Color.rgb(0, 0, 0, 0.5));
        renderer.fillText(text, textX + 2, textY + 2);

        // Main text
        renderer.setFill(textColor);
        renderer.fillText(text, textX, textY);
    }

    public double getTextWidth(GraphicsContext renderer) {
        Text textNode = new Text(this.text);
        textNode.setFont(renderer.getFont());
        return textNode.getLayoutBounds().getWidth();
    }

    public void show(String message) {
        this.text = message;
        this.visible = true;
    }

    public void show(String message, Color textColor) {
        this.textColor = textColor;
        this.text = message;
        this.visible = true;
    }

    public void hide() {
        this.visible = false;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

}
