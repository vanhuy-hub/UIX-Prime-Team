package vibe.com.demo.game.objects.factories;

import vibe.com.demo.game.animations.AnimationType;
import vibe.com.demo.game.animations.BrickDestroyAnimation;
import vibe.com.demo.game.animations.SpriteAnimation;

public class AnimationFactory {

    /**
     * phương thức tạo các animation
     */
    public static SpriteAnimation createSpriteAnimation(AnimationType type) {
        switch (type) {
            case BRICK_DESTROY:
                return new BrickDestroyAnimation();
            default:
                return null;
        }
    }
}
