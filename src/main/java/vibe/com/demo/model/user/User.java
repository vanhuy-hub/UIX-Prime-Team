package vibe.com.demo.model.user;

import java.util.Objects;

import vibe.com.demo.model.game.PlayerProgress;

public class User {

    private String username;
    private String password;
    private String playerName;
    private PlayerProgress playerProgress = new PlayerProgress();//đối tượng chưa coins và trophies 

    public User(String username, String password, String playerName) {
        this.username = username;
        this.password = password;
        this.playerName = playerName;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", playerName=" + playerName + ", " + playerProgress.toString() + "]";
    }

    public PlayerProgress getPlayerProgress() {
        return playerProgress;
    }

    public void setPlayerProgress(PlayerProgress playerProgress) {
        this.playerProgress = playerProgress;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        System.out.println(password.hashCode());
        return result;
    }

    public static void main(String[] args) {
        User user = new User("huy", "12345", "Pro");
        System.out.println(user.hashCode());
    }

}
