package vibe.com.demo.service;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static AuthService instance;//tạo 1 static AuthService để dùng chung cho login và signup để chúng cùng trỏ đến 1 dữ liệu 
    private Map<String, String> users;

    public AuthService() {
        users = new HashMap<>();
        users.put("vanhuytnt", "12345678");
    }

    public static AuthService getInstance() {//get trả về instance 
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        System.out.println(users);
        System.out.println(users.containsKey(username) + " : " + users.get(username));
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public boolean signup(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty()) {
            return false;
        }
        //kiểm tra username đã tồn tại hay chưa
        if (users.containsKey(username)) {
            System.out.println("Tên đăng nhập đã tồn tại");
            return false;
        }
        //Thêm user mới 

        users.put(username, password);
        System.out.println(users);
        System.out.println("Đã thêm 1 user");

        return true;
    }
}
