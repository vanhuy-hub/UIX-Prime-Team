package vibe.com.demo.game.levels;

import vibe.com.demo.game.utils.GameConstants;

public class LevelConfig {

    /**
     * Tìm
     */
    public static double calculateMapWidth(int cols) {
        return cols * GameConstants.BRICK_WIDTH + (cols - 1) * GameConstants.COL_GAP;
    }

    /**
     * Tìm điểm bắt đầu của map
     */
    public static double calculateStartX(int cols, double gameWidth) {
        return (gameWidth - calculateMapWidth(cols)) / 2;
    }
}
