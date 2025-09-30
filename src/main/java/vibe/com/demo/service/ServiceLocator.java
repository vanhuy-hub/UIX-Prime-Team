package vibe.com.demo.service;

//đây là lớp quản lí các service trong game 
import vibe.com.demo.service.audio.AudioService;
import vibe.com.demo.service.auth.AuthService;
import vibe.com.demo.service.game.GameProgressService;
import vibe.com.demo.service.user.UserService;

public class ServiceLocator {

    //........instance~ static 
    private static ServiceLocator instance;

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }
    //.........audioService~ quan li am thanh 
    private AudioService audioService = new AudioService();

    public AudioService getAudioService() {
        return audioService;
    }

    public AuthService getAuthService() {
        return authService;
    }
    // ......UserService : 
    private UserService userService;

    //....... authService ~ chịu trách nghiệm xác thực tài khoản 
    private AuthService authService;

    // constructor
    private ServiceLocator() {
        this.userService = new UserService();
        this.authService = new AuthService(userService); // ← TRUYỀN userService VÀO
    }

    public UserService getUserService() {
        return userService;
    }
    //gamProgressService : chịu trách nghiệm load level , load coin ~ đồng nhất dữ liệu ở mọi nơi ~ những dữ liệu sẽ được cập nhật cả ở model 
    private GameProgressService gameProgressService = new GameProgressService();

    public GameProgressService getGameProgressService() {
        return gameProgressService;
    }

}
