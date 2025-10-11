package vibe.com.demo.game.animations;

public enum AnimationType {
    //Brick animations 
    BRICK_DESTROY,//Nổ gạch
    BRICK_HIT_UNBREAKABLE, // Rung gạch không phá vỡ
    BRICK_EXPLOSION_CHAIN, // Nổ chuỗi gạch nổ

    //Ball animations : 
    BALL_TRAIL, //duoi gio theo bong 
    BALL_SPEED_TRAIL, // Đuôi tốc độ cao
    BALL_FIRE_TRAIL, // Đuôi lửa

    // Paddle animations
    PADDLE_EXPLOSION, // Nổ paddle
    PADDLE_HIT, // Hiệu ứng va chạm paddle
    PADDLE_POWERUP_GLOW, // Hiệu ứng power-up trên paddle

    // Power-up animations
    POWERUP_SPAWN, // Spawn power-up (sinh power up )
    POWERUP_COLLECT, // Thu thập power-up

    // Game effect animations
    EXPLOSION, // Nổ tổng quát
    PARTICLE_BURST, // Bùng nổ particle
    SCORE_POPUP, // Hiệu ứng điểm số
    COMBO_EFFECT, // Hiệu ứng combo
    LEVEL_COMPLETE, // Hoàn thành level
    GAME_OVER        // Game over
}
