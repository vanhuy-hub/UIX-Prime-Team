package vibe.com.demo.game.animations;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.factories.AnimationFactory;

public class AnimationManager {

    private List<SpriteAnimation> activeSpriteAnimations;

    public AnimationManager() {
        this.activeSpriteAnimations = new ArrayList<>();
    }

    /**
     * Thêm sprite animation mới , sẽ được gọi ở GameEngine để thêm animation
     * sprite dựa vào tham số type, và vị trí
     */
    public void addSpriteAnimation(AnimationType type, double x, double y) {
        SpriteAnimation animation = AnimationFactory.createSpriteAnimation(type);
        animation.start(x, y);

        activeSpriteAnimations
                .add(animation);//hàm này để lấy ra danh sách nếu đã có , hoạc tạo danh sách mới khi key chưa có và add animation item vào 
    }

    public void update() {
        for (SpriteAnimation anim : this.activeSpriteAnimations) {
            anim.update();
        }

    }

    /**
     * gameAnimation
     *
     * @param gc
     */
    public void render(GraphicsContext gc) {
        for (SpriteAnimation anim : this.activeSpriteAnimations) {
            anim.render(gc);
        }
    }

    public void stopAll() {
        for (SpriteAnimation anim : this.activeSpriteAnimations) {
            anim.stop();
        }

    }

    public void clear() {

        activeSpriteAnimations.clear();
    }
}
