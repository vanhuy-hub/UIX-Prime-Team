package vibe.com.demo.service.audio;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioService {

    private Map<String, MediaPlayer> backGroundMusic;//nhạc nền 
    private Map<String, AudioClip> soundEffects;//hiệu ứng âm thanh 
    private MediaPlayer currentMusic;//để get/set âm thanh nền hiện tại 

    public AudioService() {
        backGroundMusic = new HashMap<>();
        soundEffects = new HashMap<>();
        preLoadSounds();
    }

    public MediaPlayer getCurrentMusic() {
        return currentMusic;
    }

    public void setCurrentMusic(MediaPlayer currentMusic) {
        this.currentMusic = currentMusic;
    }

    // === PRELOAD ÂM THANH ===
    public void preLoadSounds() {
        //load music
        loadBackgoundMusic("background", "/vibe/com/demo/assets/sounds/background.mp3");

        // load audioclip
        loadSoundEffect("clicksound", "/vibe/com/demo/assets/sounds/clickSound.mp3");

        loadSoundEffect("collect", "/vibe/com/demo/assets/sounds/collect.mp3");
        loadSoundEffect("win", "/vibe/com/demo/assets/sounds/victory.mp3");
        loadSoundEffect("lose", "/vibe/com/demo/assets/sounds/lose.mp3");
    }

    // === LOAD BACKGROUND MUSIC (MediaPlayer) ===
    public void loadBackgoundMusic(String key, String path) {
        URL musicUrl = getClass().getResource(path);
        System.out.println(musicUrl);
        if (musicUrl != null) {
            Media media = new Media(musicUrl.toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);//lap vo tan 
            backGroundMusic.put(key, mediaPlayer);
        }
    }
    // === LOAD SOUND EFFECTS (AudioClip) ===

    public void loadSoundEffect(String key, String path) {
        URL soundEffectUrl = getClass().getResource(path);
        if (soundEffectUrl != null) {
            AudioClip audioClip = new AudioClip(soundEffectUrl.toExternalForm());
            soundEffects.put(key, audioClip);
        }
    }

    // === BACKGROUND MUSIC CONTROLS ===
    public void playBackgroundMusic(String key) {//chỉ cần truyền vào key là lấy được MediaPlayer tương ứng 
        if (currentMusic != null && key != null) {
            if (currentMusic != backGroundMusic.get(key)) {//nếu nhạc khác thì đổi 
                currentMusic.stop();
            } else {//nếu vẫn nhạc đó thì không đổi 
                return;
            }
        }

        if (key == null) {
            return;//chạy nhạc tiếp nếu không truyền vào gì 
        }
        MediaPlayer music = backGroundMusic.get(key);
        if (music != null) {
            currentMusic = music;
            currentMusic.play();
        }
    }

    public void stopBackgroundMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }

    // === SOUND EFFECT CONTROLS ===
    public void playSoundEffect(String key) {
        System.out.println(soundEffects.get(key));
        AudioClip clip = soundEffects.get(key);
        if (clip != null) {
            clip.play();
            System.out.println("🔊 Playing sound: " + key);
        }
    }

    public void stopSoundEffect(String key) {
        AudioClip clip = soundEffects.get(key);
        if (clip != null) {
            clip.stop();
        }
    }

    // === VOLUME CONTROLS ===
    public void setMusicVolume(double volume) {
        double validVolume = Math.max(0, Math.min(1, volume));//để đỡ bị sai khi truyền giá trị lỗi vào 
        //để xét tất cả nhạc nền cùng 
        if (this.currentMusic != null) {
            currentMusic.setVolume(validVolume);
        }
    }

    public void setAudioVolume(double volume) {
        double validVolume = Math.max(0, Math.min(1, volume));//để đỡ bị sai khi truyền giá trị lỗi vào 
        //để xét tất cả hiệu ứng ngắn có volumn nhỏ cùng lúc ta dùng forEach 
        soundEffects.values().forEach(clip -> clip.setVolume(validVolume));
    }
}
