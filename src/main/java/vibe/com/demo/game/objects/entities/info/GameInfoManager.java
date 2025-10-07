package vibe.com.demo.game.objects.entities.info;

import javafx.scene.canvas.GraphicsContext;

public class GameInfoManager {

    private CoinInfo coinInfo;
    private TimeInfo timeInfo;
    private LivesInfo livesInfo;
    double gameWidth;

    public GameInfoManager(double gameWidth) {
        this.gameWidth = gameWidth;
        livesInfo = new LivesInfo(10, 10);
        coinInfo = new CoinInfo(10, 30);
        timeInfo = new TimeInfo(10, 50);
    }

    public void render(GraphicsContext gc) {
        livesInfo.render(gc);
        coinInfo.render(gc);
        timeInfo.render(gc);
    }

    public void update() {
        timeInfo.update();
    }

    public void reset() {
        livesInfo.reset();
        timeInfo.reset();
        coinInfo.reset();
    }

    public void stop() {
        timeInfo.pauseTime();
    }

    public void play() {
        timeInfo.play();
    }

    public CoinInfo getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(CoinInfo coinInfo) {
        this.coinInfo = coinInfo;
    }

    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public LivesInfo getLivesInfo() {
        return livesInfo;
    }

    public void setLivesInfo(LivesInfo livesInfo) {
        this.livesInfo = livesInfo;
    }

    public double getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(double gameWidth) {
        this.gameWidth = gameWidth;
    }

}
