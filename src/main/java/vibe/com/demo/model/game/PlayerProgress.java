package vibe.com.demo.model.game;

import java.util.ArrayList;
import java.util.List;

public class PlayerProgress {

    private int coins;
    private int trophies;

    private List<String> idPurchasedPaddles;
    private String idCurrentPaddle;

    public PlayerProgress() {
        coins = 0;
        trophies = 0;
        idPurchasedPaddles = new ArrayList<>();
        idPurchasedPaddles.add("item1");
        idCurrentPaddle = "item1";
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    // Hoàn thành 1 level : 
    public void addTrophy() {
        if (trophies < 20) {
            this.trophies++;
        }
    }

    // helper ~ them tien : 
    public void addCoins(int coin) {
        this.coins += coin;
    }

    public void subtractCoin(int amount) {
        this.coins -= amount;
    }

    // level suy ra từ trophies hiện tại : 
    public int getCurrentLevel() {
        return (trophies + 1) <= 20 ? trophies + 1 : 20;
    }

    @Override
    public String toString() {
        return "PlayerProgress [coins=" + coins + ", trophies=" + trophies + "]";
    }
    //

    public List<String> getIdPurchasedPaddles() {
        return idPurchasedPaddles;
    }

    public void setIdPurchasedPaddles(List<String> idPurchasedPaddles) {
        this.idPurchasedPaddles = idPurchasedPaddles;
    }

    public String getIdCurrentPaddle() {
        return idCurrentPaddle;
    }

    public void setIdCurrentPaddle(String idCurrentPaddle) {
        this.idCurrentPaddle = idCurrentPaddle;
    }

    //helper list purchaedPaddle
    public void addIdNewPaddleI(String id) {
        idPurchasedPaddles.add(id);
    }
}
