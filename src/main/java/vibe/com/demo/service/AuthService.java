package vibe.com.demo.service;

import java.util.ArrayList;
import java.util.List;

import vibe.com.demo.model.User;

public class AuthService {

    private static AuthService instance;//tạo 1 static AuthService để dùng chung cho login và signup để chúng cùng trỏ đến 1 dữ liệu 
    private List<User> users;
    private User currentUser;//lưu đối tượng hiện tại sau khi signUp/login thành công
    private int completedLevels;//lưu số level đã hoàn thành 

    public User getCurrentUser() {//trả về currentUser khi ở lobby
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {//set currentUser khi ở signUp thành công / login thành công 
        this.currentUser = currentUser;
    }

    public List<User> getUserList() {
        return users;
    }

    public AuthService() {
        users = new ArrayList<>();
    }

    public static AuthService getInstance() {//get trả về instance 
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }
//trả về index của User, rồi sẽ truyền index vào thằng loadLobbyView ,để sẽ lấy được userCurrent thông qua index vì Lobby cũng có trường AuthService 

    public boolean login(String username, String password) {
        int index = indexOfUsername(username);
        System.out.println(index);
        if (index == -1 || !users.get(index).getPassword().equals(password)) {
            return false;
        }
        currentUser = users.get(index);//luu userCurrent 
        return true;
    }

    public boolean isUsernameExists(String username) {
        //kiểm tra username đã tồn tại hay chưa
        if (indexOfUsername(username) != -1) {
            System.out.println("Tên đăng nhập đã tồn tại");
            return true;
        }
        return false;
    }

    public boolean isPlayerNameExists(String playerName) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getPlayerName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    //=====Tìm kiếm username và playerName 
    public int indexOfUsername(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    //add them user 
    public void addUser(String username, String password, String playerName) {
        setCurrentUser(new User(username, password, playerName));//lưu luôn currentUser khi đăng kí 
        this.users.add(new User(username, password, playerName));
    }

    public int getCompletedLevels() {
        return completedLevels;
    }

    public void getCompletedLevels(int completedLevels) {
        this.completedLevels = completedLevels;
    }
}
