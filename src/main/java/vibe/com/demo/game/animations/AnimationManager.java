package vibe.com.demo.game.animations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
        // this.brickShakeAnimations = new HashMap<>();
    }

    /**
     * Thêm sprite animation mới
     */
    public void addSpriteAnimation(AnimationType type, double x, double y, Object... params) {
        SpriteAnimation animation = createSpriteAnimation(type, params);
        animation.start(x, y);

        activeSpriteAnimations
                .computeIfAbsent(type, k -> new ArrayList<>())
                .add(animation);
    }

    private SpriteAnimation createSpriteAnimation(AnimationType type, Object... params) {
        switch (type) {
            case BRICK_DESTROY:
                if (params.length > 0 && params[0] instanceof Color) {
                    return new BrickDestroyAnimation();
                }
                return new BrickDestroyAnimation();
            // Thêm các sprite animation khác...
            default:
                return new BrickDestroyAnimation(); // Fallback
        }
    }

    /**
     * Cập nhật balltrail
     */
    // public void updateBallTrail(double ballX, double ballY, boolean isActive) {
    //     ballTrail.updateBallPosition(ballX, ballY, isActive);
    // }
    /**
     * Thêm hiệu ứng rung cho brick
     */
    // public void addBrickShake(Brick brick, double x, double y) {
    //     BrickShakeAnimation shakeAnim = new BrickShakeAnimation();
    //     shakeAnim.start(x, y);
    //     brickShakeAnimations.put(brick, shakeAnim);//mỗi viên gạch rung động lập, cần theo dõi hiệu ứng run của từng brick cụ thể 
    // }
    /**
     * Lấy vị trí rung của gạch
     */
    // public double[] getBrickShakePosition(Brick brick) {
    //     BrickShakeAnimation anim = brickShakeAnimations.get(brick);
    //     if (anim != null && anim.isActive()) {
    //         return new double[]{anim.getShakeX(), anim.getShakeY()};// Vị trí rung
    //     }
    //     return new double[]{brick.getX(), brick.getY()}; // Vị trí gốc
    // }
    public void update(Ball ball) {
        for (List<SpriteAnimation> animations : activeSpriteAnimations.values()) {
            animations.forEach(SpriteAnimation::update);
        }

        // Dọn dẹp sprite animations đã kết thúc
        activeSpriteAnimations.values().forEach(list
                -> list.removeIf(anim -> !anim.isActive())
        );
        activeSpriteAnimations.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        // //cập nhật brick shake 
        // brickShakeAnimations.values().forEach(GameAnimation::update);
        // brickShakeAnimations.entrySet().removeIf(entry -> !entry.getValue().isActive());
        //cập nahatj vall trail 
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
