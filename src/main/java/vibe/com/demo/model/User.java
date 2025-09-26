package vibe.com.demo.model;

public class User {

    private String username;
    private String password;
    private String playerName;

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
        return "User [username=" + username + ", password=" + password + ", playerName=" + playerName + "]";
    }
}
