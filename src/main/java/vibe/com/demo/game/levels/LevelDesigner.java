package vibe.com.demo.game.levels;

import java.util.ArrayList;
import java.util.List;

import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.factories.BrickFactory;

public class LevelDesigner {

    private static final double colGap = 15;
    private static final double rowGap = 20;
    private static final double brickWidth = 90;
    private static final double brickHeight = 20;

    /**
     * Hàm dùng chung để chuyển từ modal -> ListBrick
     */
    public static List<Brick> createMapFromModal(Character[][] map, double gameWidth) {
        int cols = map[0].length;
        int rows = map.length;
        double mapWidth = cols * brickWidth + (cols - 1) * colGap;
        double startX = (gameWidth - mapWidth) / 2;
        double startY = 50;
        List<Brick> bricks = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            double x = startX;
            for (int j = 0; j < cols; j++) {
                double y = startY;
                if (map[i][j] != '0') {
                    bricks.add(BrickFactory.createBrickFromChar(map[i][j], x, y, brickWidth, brickHeight));
                }
                x += (colGap + brickWidth);
            }
            startY += (rowGap + brickHeight);
        }
        System.out.println("map da tao xong");
        return bricks;
    }

    /**
     * Hàm để tạo level có tham số gameWidth để giúp ta căn giữa gameWidth để
     * render, bên trong sẽ có mảng 2 chiều char toàn số
     */
    public static List<Brick> createLevel1(double gameWidth) {
        System.out.println("Tao map");
        Character[][] level1 = {{'1', '2', 'U', '0', '1', '2', 'U'}, {'1', '2', 'U', '0', '1', '2', 'U'}, {'1', '2', 'U', '0', '1', '1', 'U'}, {'1', '1', 'U', '0', '1', '1', 'U'}};
        return createMapFromModal(level1, gameWidth);
    }
}
