package vibe.com.demo.game.animations;

public class BrickDestroyAnimation extends SpriteAnimation {

    public BrickDestroyAnimation() {
        super(AnimationType.BRICK_DESTROY, "/vibe/com/demo/assets/img/exp2.png",
                150, 108, // Kích thước mỗi frame
                8, // Tổng số frames
                0.08);    // 0.05s mỗi frame => 16 frames × 0.05 = 0.8s toàn bộ);
        //TODO Auto-generated constructor stub
    }

}
