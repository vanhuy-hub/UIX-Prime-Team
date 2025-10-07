package vibe.com.demo.game.objects.entities.info;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CoinInfo extends InfoObject {

    private int coins;

    public CoinInfo(double x, double y) {
        super(x, y, 200, 40);//kích thước cho lives displat
        title = "Coins: ";
        coins = 0;
        //TODO Auto-generated constructor stub
    }

    @Override
    public void render(GraphicsContext renderer) {
        // TODO Auto-generated method stub
        renderer.setFill(Color.WHITE);
        renderer.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        renderer.fillText(title, x, y + 20);
        double textWidth = getTextWidth(renderer);
        double startX = textWidth + x;
        renderer.fillText(String.valueOf(coins), startX, y + 20);
    }

    public void addCoin(int count) {
        coins += count;
    }

    public void addCoin() {
        coins++;
    }

    public void reset() {
        coins = 0;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
