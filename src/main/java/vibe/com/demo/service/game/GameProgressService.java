package vibe.com.demo.service.game;
//quản lí tiến trình game 

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import vibe.com.demo.model.user.User;

public class GameProgressService {

    //tham số user sẽ là currentUser được truyền vào 
    //======coins @ trophies ======
    public int getCoins(User user) {
        return user != null ? user.getPlayerProgress().getCoins() : 0;
    }

    public int getTrophies(User user) {
        return user != null ? user.getPlayerProgress().getTrophies() : 0;
    }

    public void addCoins(User user, int coins) {
        if (user != null) {
            user.getPlayerProgress().addCoins(coins);
        }
    }

    // logic add coin khi hoan thanh level : 
    public void completeLevel(User user, int level) {
        if (user != null && level == user.getPlayerProgress().getCurrentLevel()) {
            // Chỉ +1 trophy nếu hoàn thành level hiện tại
            user.getPlayerProgress().addTrophy();

            // Thưởng coins
            int coinsReward = level * 50;
            user.getPlayerProgress().addCoins(coinsReward);
        }
    }
    //logic - coin khi mua do 

    public int getCurrentLevel(User user) {
        return (user != null) ? user.getPlayerProgress().getCurrentLevel() : 1;
    }

    // property selectedlevel 
    private IntegerProperty selectedLevel = new SimpleIntegerProperty(1);

    public IntegerProperty getSelectedLevelProperty() {
        return selectedLevel;
    }

    public int getSelectedLevel() {
        return selectedLevel.get();
    }

    public void setSelectedLevel(int level) {
        this.selectedLevel.set(level);
    }

    // property coins 
    private IntegerProperty coins = new SimpleIntegerProperty(0);

    public IntegerProperty getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins.set(coins);
    }

    // kiem tra 
    public boolean isLockedNextButton(User user) {
        return getCurrentLevel(user) == 20 || selectedLevel.get() == getCurrentLevel(user);
    }
}
