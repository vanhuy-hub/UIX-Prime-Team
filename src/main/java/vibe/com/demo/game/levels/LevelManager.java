package vibe.com.demo.game.levels;

import java.util.List;

import vibe.com.demo.game.objects.entities.bricks.Brick;

public class LevelManager {

    private int currentLevel;
    private double gameWidth;
    private List<Brick> currentBricks;

    public List<Brick> getCurrentBricks() {
        return currentBricks;
    }

    public LevelManager(int currentLevel, double gameWidth) {
        this.currentLevel = currentLevel;
        this.gameWidth = gameWidth;
        loadCurrentLevel();
    }

    public LevelManager(double gameWidth) {
        this.currentLevel = 1;
        this.gameWidth = gameWidth;
        loadCurrentLevel();
    }

    /**
     * Hàm load này chỉ dùng ở gameManager, còn ở GameManager sẽ dùng chính
     * thằng currentBricks để làm map ~ đúng như ý nghĩa quản lí Level của
     * LevelManager
     */
    private void loadCurrentLevel() {
        this.currentBricks = LevelLoader.createMapFromModal(currentLevel, gameWidth);
    }

    /**
     * Reset level hiện tại
     */
    public void resetCurrentLevel() {
        loadCurrentLevel();
    }

    /**
     * hàm next level , chuyển tiếp sang level
     */
    public void nextLevel() {
        currentLevel++;
        if (LevelLoader.canLoadLevel(currentLevel)) {
            loadCurrentLevel();
        }
    }

    /**
     * Chuyển đến level cụ thể
     */
    public boolean goToLevel(int level) {
        if (LevelLoader.canLoadLevel(level)) {
            currentLevel = level;
            loadCurrentLevel();
            return true;
        }
        return false;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public double getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(double gameWidth) {
        this.gameWidth = gameWidth;
    }

    public void setCurrentBricks(List<Brick> currentBricks) {
        this.currentBricks = currentBricks;
    }
}
