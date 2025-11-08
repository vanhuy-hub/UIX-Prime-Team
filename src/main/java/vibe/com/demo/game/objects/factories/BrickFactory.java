package vibe.com.demo.game.objects.factories;

import vibe.com.demo.game.objects.entities.bricks.Brick;
import vibe.com.demo.game.objects.entities.bricks.ExplosiveBrick;
import vibe.com.demo.game.objects.entities.bricks.NormalBrick;
import vibe.com.demo.game.objects.entities.bricks.StrongBrick;
import vibe.com.demo.game.objects.entities.bricks.UnbreakableBrick;

public class BrickFactory {

    /**
     * Hàm tạo loại gạch từ kí tự char (kí tự số )
     */
    public static Brick createBrickFromChar(char c, double x, double y, double width, double height) {
        switch (c) {
            case '1':
                return new NormalBrick(x, y, width, height);
            case '2': // Strong brick  
                return new StrongBrick(x, y, width, height);
            case 'U': // Unbreakable
                return new UnbreakableBrick(x, y, width, height);
            case 'E'://ExplosiveBrick
                return new ExplosiveBrick(x, y, width, height);
            default:
                return new NormalBrick(x, y, width, height);
        }
    }
}
