package vibe.com.demo.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import vibe.com.demo.MainApp;

public class LobbyController {

    private MainApp mainApp;//dùng đối tượng mainApp để có thể chuyển khung hình 

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private MediaView backgroundVideo;
    @FXML
    private Button logoutButton;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        try {
            // video nằm trong resources/vibe/com/demo/assets/video/
            String videoPath = getClass()
                    .getResource("/vibe/com/demo/assets/video/ronaldo.mp4")
                    .toExternalForm();

            Media media = new Media(videoPath);
            mediaPlayer = new MediaPlayer(media);
            backgroundVideo.setMediaPlayer(mediaPlayer);

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // lặp lại
            mediaPlayer.setVolume(0.7);
            mediaPlayer.play();

            backgroundVideo.setSmooth(true);

        } catch (Exception e) {
            System.out.println("Không load được video nền: " + e.getMessage());
        }
    }

    @FXML
    public void switchToLoginView(ActionEvent event) {
        if (this.mainApp != null) {
            mediaPlayer.stop();
            PauseTransition delay = new PauseTransition(Duration.millis(150));//0.1s
            delay.setOnFinished(e -> this.mainApp.loadLoginView());
            delay.play();
        }
    }
}
