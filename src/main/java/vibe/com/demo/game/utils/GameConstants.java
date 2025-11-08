package vibe.com.demo.game.utils;

public class GameConstants {

    public static final double GAME_WIDTH = 900;
    public static final double GAME_HEIGHT = 700;

    /**
     * Game Session.
     */
    public static final int LIVES = 5;
    public static final int REWARDS = 200; //hệ số nhân với rewards ra tiền thưởng mỗi level 
    /**
     * Level constants
     */
    public static final double BRICK_WIDTH = 80; // Giảm nhẹ để fit 20 levels
    public static final double BRICK_HEIGHT = 30;

    // Khoảng cách
    public static final double COL_GAP = 12;
    public static final double ROW_GAP = 15;

    // Vị trí bắt đầu
    public static final double START_Y = 40;

    // Số level tối đa
    public static final int MAX_LEVELS = 20;

    /**
     * Ball constants
     */
    public static final int BALL_BASE_SPEED = 4;
    public static final int BALL_DURATION = 4;//sau 4*60=240 khung hình sẽ tăng tốc 1 lần 
    public static final int MAX_BALLS_SIZE = 3;
    public static final double BALL_RADIUS = 10;
    /**
     * gia tốc.
     */
    public static final double ACCELERATION = 1.05;

    /**
     * Paddle constants
     */
    public static final int PADDLE_NORMAL_WIDTH = 140;
    public static final int PADDLE_NORMAL_HEIGHT = 40;
    public static final int PADDLE_EXPAND_WIDTH = 180;
    public static final int PADDLE_BASE_SPEED = 4;

    /**
     * Powerup constants
     */
    public static final double POWERUP_DURATION = 8;
    public static final double FIRE_BALL_DURATION = 5;
    public static final double EXPAND_PADDLE_DURATION = 5;
    public static final double POWERUP_DY = 5;
    public static final double POWERUP_DX = 0;
    public static final int BONUS = 20;

}
