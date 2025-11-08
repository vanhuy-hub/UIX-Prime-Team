package vibe.com.demo.game.levels;

import java.util.ArrayList;
import java.util.List;

import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.factories.BrickFactory;
import vibe.com.demo.game.utils.GameConstants;

public class LevelLoader {

    public static boolean canLoadLevel(int level) {
        return level <= LevelDesigner.getMaxLevel();
    }

    /**
     * Hàm dùng chung để chuyển từ modal -> ListBrick
     */
    public static List<Brick> createMapFromModal(int level, double gameWidth) {
        Character[][] map = LevelDesigner.getLevelDesign(level);
        int cols = map[0].length;
        int rows = map.length;
        double mapWidth = LevelConfig.calculateMapWidth(cols);
        double startX = LevelConfig.calculateStartX(cols, gameWidth);
        double startY = GameConstants.START_Y;
        List<Brick> bricks = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            double x = startX;
            for (int j = 0; j < cols; j++) {
                double y = startY;
                if (map[i][j] != '0') {
                    bricks.add(BrickFactory.createBrickFromChar(map[i][j], x, y, GameConstants.BRICK_WIDTH, GameConstants.BRICK_HEIGHT));
                }
                x += (GameConstants.COL_GAP + GameConstants.BRICK_WIDTH);
            }
            startY += (GameConstants.ROW_GAP + GameConstants.BRICK_HEIGHT);
        }
        System.out.println("map da tao xong");
        return bricks;
    }

}
