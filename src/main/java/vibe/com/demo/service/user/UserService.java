package vibe.com.demo.service.user;

import java.util.ArrayList;
import java.util.List;

import vibe.com.demo.model.user.User;

public class UserService {

    //user- data 
    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    //constructor
    public UserService() {
        //lấy dữ liệu từ cơ sở dữ liệu 
        users = new ArrayList<>();
        users.add(new User("vanhuy", "12345678", "pro1234"));
        users.get(0).getPlayerProgress().setTrophies(20);
        users.get(0).getPlayerProgress().setCoins(20000);
        users.add(new User("huybui", "12345678", "pro123"));
        users.get(1).getPlayerProgress().setCoins(100000);
    }

    //=====Tìm kiếm username  
    public int indexOfUsername(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public User getUserByUsername(String username) {
        int index = indexOfUsername(username);
        return index != -1 ? users.get(index) : null;
    }
    //====Kiem tra signup
    // kiem tra playername :

    public boolean isPlayerNameExists(String playerName) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getPlayerName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    //Kiem tra username: 
    public boolean isUsernameExists(String username) {
        //kiểm tra username đã tồn tại hay chưa
        if (indexOfUsername(username) != -1) {
            System.out.println("Tên đăng nhập đã tồn tại");
            return true;
        }
        return false;
    }

    //add them user 
    public void addUser(String username, String password, String playerName) {
        this.users.add(new User(username, password, playerName));
        //them vao co so du lieu ... 
    }

}
