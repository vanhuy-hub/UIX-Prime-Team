package vibe.com.demo.service.auth;

import vibe.com.demo.model.user.User;
import vibe.com.demo.service.user.UserService;

public class AuthService {

    //khởi tạo userService bằng new UserService  sẽ bị sai vì như vậy sẽ tạo mới userService mỗi lần tạo AuthService 
    private UserService userService;

    //ham khoi  tao, lấy giá trị từ ServiceLocator truyền vào  
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    private User currentUser;//lưu đối tượng hiện tại sau khi signUp/login thành công

    public User getCurrentUser() {//trả về currentUser khi ở lobby
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {//set currentUser khi ở signUp thành công / login thành công 
        this.currentUser = currentUser;
    }

//trả về index của User, rồi sẽ truyền index vào thằng loadLobbyView ,để sẽ lấy được userCurrent thông qua index vì Lobby cũng có trường AuthService 
    public boolean login(String username, String password) {
        int index = userService.indexOfUsername(username);
        System.out.println(userService.getUsers().size());
        System.out.println(index);
        if (index == -1 || !userService.getUserByUsername(username).getPassword().equals(password)) {
            return false;
        }

        currentUser = userService.getUserByUsername(username);//luu userCurrent 
        System.out.println(currentUser);
        return true;
    }
//

    public boolean signUp(String username, String password, String playerName) {
        if (userService.isUsernameExists(username) || userService.isPlayerNameExists(playerName)) {
            return false;
        }
        currentUser = new User(username, password, playerName);//luu userCurrent 
        System.out.println(currentUser);
        userService.addUser(username, password, playerName);
        System.out.println(userService.getUsers().size());
        return true;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
