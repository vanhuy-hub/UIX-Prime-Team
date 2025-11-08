package vibe.com.demo.game.core;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import vibe.com.demo.game.utils.GameConstants;

/**
 * Lớp này với nhiệm vụ binding cho phần info (nằm ở sidebar của
 * gameViewController) với đối tượng quản lý phiên chơi game GameManager
 */
public class GameDataModel {

    //SESSION DATA (reset mỗi game)
    private IntegerProperty sessionLivesProperty = new SimpleIntegerProperty(GameConstants.LIVES);//số mạng sống trong 1 phiên chơi game 
    private boolean lost;
    private BooleanProperty isWinGameSession = new SimpleBooleanProperty(false);

    //Property cho UI binding 
    public IntegerProperty getSessionLivesProperty() {
        return sessionLivesProperty;
    }

    // Getters
    public int getSessionLives() {
        return sessionLivesProperty.get();
    }

    // Setters
    public void setSessionLives(int lives) {
        sessionLivesProperty.set(lives);
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
        if (currentLives + 1 <= GameConstants.LIVES) {
            this.sessionLivesProperty.set(this.sessionLivesProperty.get() + 1);
        }

    }

    /**
     * reset thông số mạng phiên chơi game.
     */
    public void resetGameSession() {
        this.lost = false;
        this.sessionLivesProperty.set(GameConstants.LIVES);
        this.isWinGameSession.set(false);
    }

    public boolean isWin() {
        return this.isWinGameSession.get();
    }

    public void setWon() {
        this.isWinGameSession.set(true);
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public BooleanProperty getIsWinGameSessionProperty() {
        return isWinGameSession;
    }

}
