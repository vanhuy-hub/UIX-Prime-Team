package vibe.com.demo.game.levels;

import java.util.HashMap;
import java.util.Map;

/**
 * Lớp chứa bản thiết kế thô của map
 */
public class LevelDesigner {

    private static final Map<Integer, Character[][]> LEVEL_DESIGNS = new HashMap<>();

    /**
     * static thuộc về lớp, là khối khởi tạo tĩnh , là 1 khối mã đặc việt được
     * thực thi khi lớp được tải vào bộ nhớ lần đầu + Chạy 1 lần duy nhất +
     * Trước khi bất kỳ đối tượng nào được tạo + Trước khi phương thức static
     * nào được gọi
     */
    static {
        initialize();
    }

    public static void initialize() {
        // Level 1 - Cơ bản (Easy)
        LEVEL_DESIGNS.put(1, new Character[][]{
            {'1'}
        });

        // Level 2 - Hàng rào (Easy)
        LEVEL_DESIGNS.put(2, new Character[][]{
            {'1', '1'}
        });

        // Level 3 - Pyramid (Easy)
        LEVEL_DESIGNS.put(3, new Character[][]{
            {'1', '1', '1'}
        });

        // Level 4 - Checkerboard (Easy-Medium)
        LEVEL_DESIGNS.put(4, new Character[][]{
            {'E', 'E', 'E', 'E', 'E', 'E', 'E'},
            {'E', 'E', 'E', 'E', 'E', 'E', 'E'},
            {'E', 'E', 'E', 'E', 'E', 'E', 'E'},
            {'E', 'E', 'E', 'E', 'E', 'E', 'E'}
        });

        // Level 5 - Strong Foundation (Medium)
        LEVEL_DESIGNS.put(5, new Character[][]{
            {'2', '2', '2', '2', '2', '2', '2'},
            {'1', '1', '1', '1', '1', '1', '1'},
            {'1', '1', '1', '1', '1', '1', '1'}
        });

        // Level 6 - Hollow Square (Medium)
        LEVEL_DESIGNS.put(6, new Character[][]{
            {'2', '2', '2', '2', '2', '2', '2'},
            {'2', '1', '1', '1', '1', '1', '2'},
            {'2', '1', '1', '1', '1', '1', '2'},
            {'2', '2', '2', '2', '2', '2', '2'}
        });

        // Level 7 - Cross Pattern (Medium)
        LEVEL_DESIGNS.put(7, new Character[][]{
            {'0', '0', '2', '2', '2', '0', '0'},
            {'0', '0', '2', '1', '2', '0', '0'},
            {'2', '2', '2', '1', '2', '2', '2'},
            {'2', '1', '1', '1', '1', '1', '2'},
            {'2', '2', '2', '1', '2', '2', '2'},
            {'0', '0', '2', '1', '2', '0', '0'},
            {'0', '0', '2', '2', '2', '0', '0'}
        });

        // Level 8 - Diamond (Medium)
        LEVEL_DESIGNS.put(8, new Character[][]{
            {'0', '0', '0', '2', '0', '0', '0'},
            {'0', '0', '2', '1', '2', '0', '0'},
            {'0', '2', '1', '1', '1', '2', '0'},
            {'2', '1', '1', '1', '1', '1', '2'},
            {'0', '2', '1', '1', '1', '2', '0'},
            {'0', '0', '2', '1', '2', '0', '0'},
            {'0', '0', '0', '2', '0', '0', '0'}
        });

        // Level 9 - Unbreakable Maze (Medium-Hard)
        LEVEL_DESIGNS.put(9, new Character[][]{
            {'U', '1', 'U', '1', 'U', '1', 'U'},
            {'1', 'U', '1', 'U', '1', 'U', '1'},
            {'U', '1', 'U', '1', 'U', '1', 'U'},
            {'1', 'U', '1', 'U', '1', 'U', '1'},
            {'U', '1', 'U', '1', 'U', '1', 'U'}
        });

        // Level 10 - Explosive Intro (Medium-Hard)
        LEVEL_DESIGNS.put(10, new Character[][]{
            {'1', 'E', 'U', 'E', 'U', 'E', '1'},
            {'E', '2', 'E', '2', 'E', '2', 'E'},
            {'1', 'E', 'U', 'E', 'U', 'E', '1'},
            {'E', '2', 'E', '2', 'E', '2', 'E'}
        });

        // Level 11 - Fortress (Hard)
        LEVEL_DESIGNS.put(11, new Character[][]{
            {'U', 'U', 'U', 'U', 'U', 'U', 'U'},
            {'U', '2', '2', '2', '2', '2', 'U'},
            {'U', '2', '1', '1', '1', '2', 'U'},
            {'U', '2', '1', '1', '1', '2', 'U'},
            {'U', '2', '2', '2', '2', '2', 'U'},
            {'U', 'U', 'U', 'U', 'U', 'U', 'U'}
        });

        // Level 12 - Zigzag (Hard)
        LEVEL_DESIGNS.put(12, new Character[][]{
            {'2', '2', '2', '0', '2', '2', '2'},
            {'1', '1', '0', '2', '0', '1', '1'},
            {'2', '0', '2', '2', '2', '0', '2'},
            {'0', '2', '1', '1', '1', '2', '0'},
            {'2', '0', '2', '2', '2', '0', '2'},
            {'1', '1', '0', '2', '0', '1', '1'},
            {'2', '2', '2', '0', '2', '2', '2'}
        });

        // Level 13 - Spiral (Hard)
        LEVEL_DESIGNS.put(13, new Character[][]{
            {'2', '2', '2', '2', '2', '2', '2'},
            {'2', '1', '1', '1', '1', '1', '2'},
            {'2', '1', '2', '2', '2', '1', '2'},
            {'2', '1', '2', '1', '2', '1', '2'},
            {'2', '1', '2', '2', '2', '1', '2'},
            {'2', '1', '1', '1', '1', '1', '2'},
            {'2', '2', '2', '2', '2', '2', '2'}
        });

        // Level 14 - Checkerboard Advanced (Hard)
        LEVEL_DESIGNS.put(14, new Character[][]{
            {'2', 'U', '2', 'U', '2', 'U', '2'},
            {'U', '2', 'U', '2', 'U', '2', 'U'},
            {'2', 'U', 'E', 'U', 'E', 'U', '2'},
            {'U', '2', 'U', '2', 'U', '2', 'U'},
            {'2', 'U', 'E', 'U', 'E', 'U', '2'},
            {'U', '2', 'U', '2', 'U', '2', 'U'},
            {'2', 'U', '2', 'U', '2', 'U', '2'}
        });

        // Level 15 - X Pattern (Very Hard)
        LEVEL_DESIGNS.put(15, new Character[][]{
            {'2', '0', '0', '0', '0', '0', '2'},
            {'0', '2', '0', '0', '0', '2', '0'},
            {'0', '0', '2', '0', '2', '0', '0'},
            {'0', '0', '0', 'U', '0', '0', '0'},
            {'0', '0', '2', '0', '2', '0', '0'},
            {'0', '2', '0', '0', '0', '2', '0'},
            {'2', '0', '0', '0', '0', '0', '2'}
        });

        // Level 16 - Concentric Circles (Very Hard)
        LEVEL_DESIGNS.put(16, new Character[][]{
            {'0', '0', 'U', 'U', 'U', '0', '0'},
            {'0', 'U', '2', '2', '2', 'U', '0'},
            {'U', '2', '1', '1', '1', '2', 'U'},
            {'U', '2', '1', 'E', '1', '2', 'U'},
            {'U', '2', '1', '1', '1', '2', 'U'},
            {'0', 'U', '2', '2', '2', 'U', '0'},
            {'0', '0', 'U', 'U', 'U', '0', '0'}
        });

        // Level 17 - Maze (Very Hard)
        LEVEL_DESIGNS.put(17, new Character[][]{
            {'U', 'U', 'U', 'U', 'U', 'U', 'U'},
            {'U', '1', '1', '1', '1', '1', 'U'},
            {'U', '1', 'U', 'U', 'U', '1', 'U'},
            {'U', '1', 'U', 'E', 'U', '1', 'U'},
            {'U', '1', 'U', 'U', 'U', '1', 'U'},
            {'U', '1', '1', '1', '1', '1', 'U'},
            {'U', 'U', 'U', 'U', 'U', 'U', 'U'}
        });

        // Level 18 - Binary Pattern (Very Hard)
        LEVEL_DESIGNS.put(18, new Character[][]{
            {'1', '0', '1', '0', '1', '0', '1'},
            {'0', '1', '0', '1', '0', '1', '0'},
            {'1', '0', '2', '0', '2', '0', '1'},
            {'0', '1', '0', 'U', '0', '1', '0'},
            {'1', '0', '2', '0', '2', '0', '1'},
            {'0', '1', '0', '1', '0', '1', '0'},
            {'1', '0', '1', '0', '1', '0', '1'}
        });

        // Level 19 - Boss Level (Extreme)
        LEVEL_DESIGNS.put(19, new Character[][]{
            {'U', 'U', 'U', 'U', 'U', 'U', 'U'},
            {'U', '2', '2', '2', '2', '2', 'U'},
            {'U', '2', 'E', 'E', 'E', '2', 'U'},
            {'U', '2', 'E', 'U', 'E', '2', 'U'},
            {'U', '2', 'E', 'E', 'E', '2', 'U'},
            {'U', '2', '2', '2', '2', '2', 'U'},
            {'U', 'U', 'U', 'U', 'U', 'U', 'U'}
        });

        // Level 20 - Final Challenge (Extreme)
        LEVEL_DESIGNS.put(20, new Character[][]{
            {'U', '2', 'U', '2', 'U', '2', 'U'},
            {'2', 'E', '2', 'E', '2', 'E', '2'},
            {'U', '2', 'U', '2', 'U', '2', 'U'},
            {'2', 'E', '2', 'U', '2', 'E', '2'},
            {'U', '2', 'U', '2', 'U', '2', 'U'},
            {'2', 'E', '2', 'E', '2', 'E', '2'},
            {'U', '2', 'U', '2', 'U', '2', 'U'}
        });
    }

    /**
     * Lấy layout cho level cụ thể
     */
    public static Character[][] getLevelDesign(int level) {
        return (LEVEL_DESIGNS.getOrDefault(level, LEVEL_DESIGNS.get(1)));
    }

    public static int getMaxLevel() {
        return LEVEL_DESIGNS.size();
    }
}
