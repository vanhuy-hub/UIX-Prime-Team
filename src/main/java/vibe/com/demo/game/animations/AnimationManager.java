package vibe.com.demo.game.animations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import vibe.com.demo.game.objects.entities.ball.Ball;

public class AnimationManager {

    private Map<AnimationType, List<SpriteAnimation>> activeSpriteAnimations;
    private BallTrailAnimation ballTrail;//hiệu ứng đuôi bóng (cần quản lý riêng vì chạy liên tục ~ do tồn tại suốt trò chơi , không giống các hiệu ứng nagwns hạn )
    // private Map<Brick, BrickShakeAnimation> brickShakeAnimations;//kho chuaws hieeuj ungws rung gạch ~ mỗi viên gạch có thể rung độc lập (cần quản lý riêng vì có thể rung cùng lúc ) ~ mỗi viên gạch rung độc lập 

    public BallTrailAnimation getBallTrail() {
        return this.ballTrail;
    }

    public AnimationManager() {
        this.activeSpriteAnimations = new HashMap<>();
        this.ballTrail = new BallTrailAnimation();

    }

    /**
     * Thêm sprite animation mới , sẽ được gọi ở GameEngine để thêm animation
     * sprite dựa vào tham số type, và vị trí
     */
    public void addSpriteAnimation(AnimationType type, double x, double y, Object... params) {
        SpriteAnimation animation = createSpriteAnimation(type, params);
        animation.start(x, y);

        activeSpriteAnimations
                .computeIfAbsent(type, k -> new ArrayList<>())
                .add(animation);//hàm này để lấy ra danh sách nếu đã có , hoạc tạo danh sách mới khi key chưa có và add animation item vào 
    }

    /**
     * phương thức tạo các anomation
     */
    private SpriteAnimation createSpriteAnimation(AnimationType type, Object... params) {
        switch (type) {
            case BRICK_DESTROY:
                return new BrickDestroyAnimation();

            case PADDLE_EXPLOSION:
                return new PaddleDestroyAnimation();

            // Thêm các sprite animation khác...
            default:
                return new BrickDestroyAnimation(); // Fallback
        }
    }

    public void update(Ball ball) {
        for (List<SpriteAnimation> animations : activeSpriteAnimations.values()) {
            animations.forEach(SpriteAnimation::update);//kĩ thuật lamda + forEach ~ duyệt không cần index  + kĩ thuật reference : Class :: method =>tham chiếu trực tiếp đến method , thay vì dùng anmItem -> anmItem.update()
        }

        // Dọn dẹp sprite animations đã kết thúc
        activeSpriteAnimations.values().forEach(list
                -> list.removeIf(anim -> !anim.isActive())//kĩ thuật removeIf với Predicate , tương đương với kĩ thuật dùng iterator 
        );//b1 duyệt qua tất cả danh sạch , mỗi danh sách duyệt qua tất cả item , item nào có isActive là false thì xóa item đó khỏi danh sách 
        activeSpriteAnimations.entrySet().removeIf(entry -> entry.getValue().isEmpty());//Kĩ thuật entrySet : truy cập vào cặp (key,value) cùng 1 lúc ~ b2 : duyệt qua tất cả các danh sách của key nếu danh sách nào rỗng thì xóa cặp (key,value )đó 

        ballTrail.updateBallPosition(ball);
    }

    /**
     * render sẽ luôn render đuôi bóng , và render các animation thuộc
     * gameAnimation , còn ShakeBrick được render ở brick
     *
     * @param gc
     */
    public void render(GraphicsContext gc) {
        // Render ball trail đầu tiên (nằm dưới cùng)
        if (ballTrail.isActive()) {
            ballTrail.render(gc);
        }
        // Render sprite animations
        for (List<SpriteAnimation> animations : activeSpriteAnimations.values()) {
            animations.forEach(anim -> anim.render(gc));
        }
    }

    public void stopAll() {
        // brickShakeAnimations.clear();
        ballTrail.stop();
        for (List<SpriteAnimation> animations : activeSpriteAnimations.values()) {
            animations.forEach(anim -> anim.stop());
        }
    }
}
