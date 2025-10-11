// package vibe.com.demo.game.animations;

// import java.util.HashMap;
// import java.util.Iterator;
// import java.util.Map;

// import javafx.scene.canvas.GraphicsContext;
// import vibe.com.demo.game.objects.entities.bricks.Brick;

// public class AnimationManager {

//     private Map<AnimationType, GameAnimation> activeAnimations;//kho chứa các hiệu ứng thông thường, mỗi loại chỉ có 1 instance đang chạy 
//     private BallTrailAnimation ballTrail;//hiệu ứng đuôi bóng (cần quản lý riêng vì chạy liên tục ~ do tồn tại suốt trò chơi , không giống các hiệu ứng nagwns hạn )
//     private Map<Brick, BrickShakeAnimation> brickShakeAnimations;//kho chuaws hieeuj ungws rung gạch ~ mỗi viên gạch có thể rung độc lập (cần quản lý riêng vì có thể rung cùng lúc ) ~ mỗi viên gạch rung độc lập 

//     public AnimationManager() {
//         this.activeAnimations = new HashMap<>();
//         this.ballTrail = new BallTrailAnimation();
//         this.brickShakeAnimations = new HashMap<>();
//     }

//     /**
//      * Them animation mới
//      */
//     public void addAnimation(AnimationType type, double x, double y, Object... params) {
//         GameAnimation animation = createAnimation(type);//tạo hiệu ứng 
//         animation.start(x, y, params);//khởi động hiệu ứng 
//         activeAnimations.put(type, animation);//lưu trữ 
//     }

//     private GameAnimation createAnimation(AnimationType type) {
//         switch (type) {
//             case BRICK_DESTROY:
//                 return new BrickDestroyAnimation();
//             case PADDLE_EXPLOSION:
//                 return new PaddleExplosionAnimation();
//             case BRICK_HIT_UNBREAKABLE:
//                 return new BrickShakeAnimation();
//             case BALL_TRAIL:
//                 return ballTrail;
//             default:
//                 return new BrickDestroyAnimation(); // Fallback
//         }
//     }

//     /**
//      * Cập nhật balltrail
//      */
//     public void updateBallTrail(double ballX, double ballY, boolean isActive) {
//         ballTrail.updateBallPosition(ballX, ballY, isActive);
//     }

//     /**
//      * Thêm hiệu ứng rung cho brick
//      */
//     public void addBrickShake(Brick brick, double x, double y) {
//         BrickShakeAnimation shakeAnim = new BrickShakeAnimation();
//         shakeAnim.start(x, y);
//         brickShakeAnimations.put(brick, shakeAnim);//mỗi viên gạch rung động lập, cần theo dõi hiệu ứng run của từng brick cụ thể 
//     }

//     /**
//      * Lấy vị trí rung của gạch
//      */
//     public double[] getBrickShakePosition(Brick brick) {
//         BrickShakeAnimation anim = brickShakeAnimations.get(brick);

//         if (anim != null && anim.isActive()) {
//             return new double[]{anim.getShakeX(), anim.getShakeY()};// Vị trí rung
//         }
//         return new double[]{brick.getX(), brick.getY()}; // Vị trí gốc
//     }

//     public void update() {
//         //cap nhat active animations 
//         Iterator<Map.Entry<AnimationType, GameAnimation>> iterator = activeAnimations.entrySet().iterator();

//         while (iterator.hasNext()) {
//             Map.Entry<AnimationType, GameAnimation> entry = iterator.next();
//             GameAnimation anim = entry.getValue();
//             anim.update();//cập nahatj hiệu ứng 
//             if (!anim.isActive()) {
//                 iterator.remove();//dọn dẹp hiệu ứng đã kết thúc 
//             }
//         }
//         //cập nhật brick shake 
//         brickShakeAnimations.values().forEach(GameAnimation::update);
//         brickShakeAnimations.entrySet().removeIf(entry -> !entry.getValue().isActive());

//         //cập nahatj vall trail 
//         ballTrail.update();
//     }

//     /**
//      * render sẽ luôn render đuôi bóng , và render các animation thuộc
//      * gameAnimation , còn ShakeBrick được render ở brick
//      *
//      * @param gc
//      */
//     public void render(GraphicsContext gc) {
//         // Render ball trail đầu tiên (nằm dưới cùng)
//         if (ballTrail.isActive()) {
//             ballTrail.render(gc);
//         }

//         // Render các animations khác
//         activeAnimations.values().forEach(anim -> anim.render(gc));

//     }

//     public void stopAll() {
//         activeAnimations.values().forEach(GameAnimation::stop);
//         activeAnimations.clear();
//         brickShakeAnimations.clear();
//         ballTrail.stop();
//     }
// }
