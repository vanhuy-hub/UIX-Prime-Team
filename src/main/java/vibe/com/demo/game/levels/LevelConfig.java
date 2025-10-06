package vibe.com.demo.game.levels;

public class LevelConfig {

    public static final double BRICK_WIDTH = 80; // Giảm nhẹ để fit 20 levels
    public static final double BRICK_HEIGHT = 18;

    // Khoảng cách
    public static final double COL_GAP = 12;
    public static final double ROW_GAP = 15;

    // Vị trí bắt đầu
    public static final double START_Y = 40;

    // Số level tối đa
    public static final int MAX_LEVELS = 20;

    /**
     * Tìm
     */
    public static double calculateMapWidth(int cols) {
        return cols * BRICK_WIDTH + (cols - 1) * COL_GAP;
    }

    /**
     * Tìm điểm bắt đầu của map
     */
    public static double calculateStartX(int cols, double gameWidth) {
        return (gameWidth - calculateMapWidth(cols)) / 2;
    }
}
