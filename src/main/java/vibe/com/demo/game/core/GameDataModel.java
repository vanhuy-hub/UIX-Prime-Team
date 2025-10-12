package vibe.com.demo.game.core;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Lớp này với nhiệm vụ binding cho phần info (nằm ở sidebar của
 * gameViewController) với đối tượng quản lý phiên chơi game GameManager
 */
public class GameDataModel {

    //SESSION DATA (reset mỗi game)
    private IntegerProperty sessionLivesProperty = new SimpleIntegerProperty(3);//số mạng sống trong 1 phiên chơi game 
    private IntegerProperty sessionCoinEarned = new SimpleIntegerProperty(0);//số tiền kiếm được trong 1 phiên chơi 

    //USER DATA 
    private final IntegerProperty userTotalCoinsProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty selectedLevelProperty = new SimpleIntegerProperty(1);//sẽ được lấy từ GameProgressService 

    //Property cho UI binding 
    public IntegerProperty getSessionLivesProperty() {
        return sessionLivesProperty;
    }

    public IntegerProperty getSessionCoinEarned() {
        return sessionCoinEarned;
    }

    public IntegerProperty getUserTotalCoinsProperty() {
        return userTotalCoinsProperty;
    }

    public IntegerProperty getSelectedLevelProperty() {
        return selectedLevelProperty;
    }
    // Getters

    public int getSessionLives() {
        return sessionLivesProperty.get();
    }

    public int getUserTotalCoins() {
        return userTotalCoinsProperty.get();
    }

    public int getSelectedLevel() {
        return selectedLevelProperty.get();
    }

    // Setters
    public void setSessionLives(int lives) {
        sessionLivesProperty.set(lives);
    }

    public void setUserTotalCoins(int coins) {
        userTotalCoinsProperty.set(coins);
    }

    public void setSelectedLevel(int level) {
        selectedLevelProperty.set(level);
    }

    //Helper method 
    /**
     * giảm 1 mạng sống
     */
    public void decreaseSessionLives() {
        if (this.sessionLivesProperty.get() >= 1) {
            this.sessionLivesProperty.set(this.sessionLivesProperty.get() - 1);
        }
    }

    public void increaseSessionLives() {
        int currentLives = this.sessionLivesProperty.get();
        if (currentLives + 1 <= 3) {
            this.sessionLivesProperty.set(this.sessionLivesProperty.get() + 1);
        }

    }

    /**
     * Hàm thêm coin
     */
    public void addSessionCoinEarned(int coins) {
        this.sessionCoinEarned.set(this.sessionCoinEarned.get() + coins);
    }

    public void resetGameSession() {
        this.sessionCoinEarned.set(0);
        this.sessionLivesProperty.set(3);

    }
    private BooleanProperty nextLevelUnlockedProperty = new SimpleBooleanProperty(false);

    public BooleanProperty nextLevelUnlockedProperty() {
        return nextLevelUnlockedProperty;
    }

    public boolean isNextLevelUnlocked() {
        return nextLevelUnlockedProperty.get();
    }

    public void setNextLevelUnlocked(boolean unlocked) {
        nextLevelUnlockedProperty.set(unlocked);
        System.out.println("🔓 Next level unlocked state: " + unlocked);
    }
}
