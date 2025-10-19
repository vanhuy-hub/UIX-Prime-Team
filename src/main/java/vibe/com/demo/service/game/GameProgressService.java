package vibe.com.demo.service.game;
//quản lí tiến trình game 

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.database.dao.objectdao.UserDao;

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
            System.out.println(level);
            int coinsReward = level * 5000;
            user.getPlayerProgress().addCoins(coinsReward);
            updateCoinsProperty(user);
            // cap nhat co so du lieu 
            UserDao.getInstance().update(user);
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
        user.getPlayerProgress().addPaddleToInventory(id);
        System.out.println(id);
        user.getPlayerProgress().setIdCurrentPaddle(id);
        // cap nhat co so du lieu 
        UserDao.getInstance().update(user);
        UserDao.getInstance().addPaddleIntoInventory(user, id);
    }

    public String getIdCurrentPaddle(User user) {
        return user.getPlayerProgress().getIdCurrentPaddle();
    }

    public void setIdCurrentPaddle(User user, String newId) {
        user.getPlayerProgress().setIdCurrentPaddle(newId);
        // cap nhat co so du lieu 
        UserDao.getInstance().update(user);
    }

    //get imgURL với id item tương ứng 
    public String getCurrentPaddleImageURL(User user) {
        switch (user.getPlayerProgress().getIdCurrentPaddle()) {
            case "item1":
                return "paddle1.png";
            case "item2":
                return "paddle2.png";
            case "item3":
                return "paddle3.png";
            case "item4":
                return "paddle4.png";
            case "item5":
                return "paddle5.png";
            case "item6":
                return "paddle6.png";
            case "item7":
                return "paddle7.png";
            case "item8":
                return "paddle8.png";
            case "item9":
                return "paddle9.png";
            default:
                return "paddle1.png";
        }
    }
}
