package vibe.com.demo.model.game;

public class PlayerProgress {

    private int coins;
    private int trophies;

    public PlayerProgress() {
        coins = 0;
        trophies = 0;
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
        this.trophies++;
    }

    // them tien : 
    public void addCoins(int coin) {
        this.coins += coin;
    }

    // level suy ra từ trophies hiện tại : 
    public int getCurrentLevel() {
        return (trophies + 1) <= 20 ? trophies + 1 : 20;
    }

    @Override
    public String toString() {
        return "PlayerProgress [coins=" + coins + ", trophies=" + trophies + "]";
    }
}
