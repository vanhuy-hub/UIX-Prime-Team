package vibe.com.demo.game.animations;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpriteAnimation {

    protected Image spriteSheet;
    protected int frameWidth;
    protected int frameHeight;
    protected int totalFrames;
    protected int currentFrame;
    protected double x, y;
    protected boolean isActive;
    protected double frameDuration;//thoi gian moi frame
    protected double elapsedTime;
    protected AnimationType type;

    public SpriteAnimation(AnimationType type, String spritePath, int frameWidth, int frameHeight, int totalFrames, double frameDuration) {
        this.type = type;
        this.spriteSheet = new Image(getClass().getResourceAsStream(spritePath));
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.frameDuration = frameDuration;
        this.currentFrame = 0;
        this.isActive = false;
        this.elapsedTime = 0;

    }

    public void start(double x, double y) {
        this.x = x;
        this.y = y;
        this.currentFrame = 0;
        this.elapsedTime = 0;
        this.isActive = true;

    }

    public void update() {
        if (!isActive) {
            return;
        }
        elapsedTime += 1.0 / 60.0;//60 FPS 
        if (elapsedTime >= frameDuration) {
            currentFrame++;
            elapsedTime = 0;
        }
        if (currentFrame >= totalFrames) {
            stop();
        }
    }

    public void stop() {
        this.isActive = false;
    }

    public void render(GraphicsContext gc) {
        if (!isActive) {
            return;
        }
        //Tinh vi tri  frame hien tai trong sprite sheet
        int spriteX = (currentFrame % getSpritesPerRow()) * frameWidth;
        int spriteY = (currentFrame / getSpritesPerRow()) * frameHeight;

        //render frame hien tai 
        gc.drawImage(spriteSheet,
                spriteX, spriteY, frameWidth, frameHeight, // Source
                x, y, frameWidth * 1.5, frameHeight * 1.5 // Destination ~ địa điểm muốn vẽ trên màn 
        );

    }

    protected int getSpritesPerRow() {
        return (int) (spriteSheet.getWidth() / frameWidth);
    }

    public boolean isActive() {
        return isActive;
    }

    public AnimationType getType() {
        return type;
    }
}
