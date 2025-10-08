package vibe.com.demo.service.game;
//quản lí tiến trình game 

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import vibe.com.demo.model.user.User;

public class GameProgressService {
    // property coins 

    private IntegerProperty coinsOfCurrentUser = new SimpleIntegerProperty(0);

    public IntegerProperty getCoinsProperty() {
        return coinsOfCurrentUser;
    }

    public void setCoins(int coins) {
        this.coinsOfCurrentUser.set(coins);
    }

    //========
    // property selectedlevel 
    private IntegerProperty selectedLevel = new SimpleIntegerProperty(1);

    public IntegerProperty getSelectedLevelProperty() {
        return selectedLevel;
    }

    public int getSelectedLevel() {
        return selectedLevel.get();
    }

    public void setSelectedLevel(int level) {
        selectedLevel.set(level);
    }

    //helper func
    public int getCoins(User user) {
        return user != null ? user.getPlayerProgress().getCoins() : 0;
    }

    public int getTrophies(User user) {
        return user != null ? user.getPlayerProgress().getTrophies() : 0;
    }

    public void addCoins(User user, int coins) {
        if (user != null) {
            user.getPlayerProgress().addCoins(coins);
            updateCoinsProperty(user);
        }
    }

    // logic add coin khi hoan thanh level : 
    public void completeLevel(User user, int level) {
        if (user != null) {
            System.out.println("hoan thanh 1");
            // Chỉ +1 trophy nếu hoàn thành level hiện tại
            user.getPlayerProgress().addTrophy();
            // Thưởng coins
            int coinsReward = level * 50;
            user.getPlayerProgress().addCoins(coinsReward);
            updateCoinsProperty(user);
        }
    }

    //logic - coin khi mua do 
    public void updateCoinsProperty(User user) {
        setCoins(user.getPlayerProgress().getCoins());
    }

    public int getCurrentLevel(User user) {
        return (user != null) ? user.getPlayerProgress().getCurrentLevel() : 1;
    }

    // kiem tra 
    public boolean isLockedNextButton(User user) {
        System.out.println("selected: " + selectedLevel);
        System.out.println("currentLevel: " + getCurrentLevel(user));
        return getSelectedLevel() == 20 || selectedLevel.get() == getCurrentLevel(user);
    }

    public boolean canBuyPaddle(User user, int amount) {
        return user.getPlayerProgress().getCoins() >= amount;
    }

    public void buyPaddle(User user, int amount) {
        user.getPlayerProgress().subtractCoin(amount);

        updateCoinsProperty(user);
    }

    //helper  paddle 
    /**
     * Thêm id mới vào list id
     */
    public List<String> getPurchasedPaddles(User user) {
        return user.getPlayerProgress().getIdPurchasedPaddles();
    }

    public void addNewPaddle(User user, String id) {
        user.getPlayerProgress().addIdNewPaddleI(id);
    }

    public String getIdCurrentPaddle(User user) {
        return user.getPlayerProgress().getIdCurrentPaddle();
    }

    public void setIdCurrentPaddle(User user, String newId) {
        user.getPlayerProgress().setIdCurrentPaddle(newId);
    }

}
