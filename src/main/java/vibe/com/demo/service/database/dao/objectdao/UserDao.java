package vibe.com.demo.service.database.dao.objectdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import vibe.com.demo.game.objects.entities.paddle.Paddle;
import vibe.com.demo.model.game.PlayerProgress;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.database.dao.abstraction.DaoAbstraction;
import vibe.com.demo.service.database.dao.interfaces.DaoIn;
import vibe.com.demo.service.database.utils.DatabaseConnection;

public class UserDao extends DaoAbstraction<User> implements DaoIn<User> {

    private static UserDao instance;

    @Override
    public boolean insert(User t) {
        // TODO Auto-generated method stub
        Object[] params = {t.getUsername(), t.getPassword(), t.getPlayerName()};
        return executeUpdate("Insert into player (username,password,player_name) values (?,?,?)", params) && addPaddleIntoInventory(t, "item1");
    }

    @Override
    public boolean delete(User t) {
        // TODO Auto-generated method stub
        Object[] params = {t.getUsername(), t.getPassword(), t.getPlayerName()};
        return executeUpdate("Delete from player where username = ? AND password = ? AND player_name = ?", params);
    }

    @Override
    public boolean update(User t) {
        // TODO Auto-generated method stub
        System.out.println(t.getPlayerProgress().getIdCurrentPaddle());
        Object[] params = {t.getPlayerProgress().getCoins(), t.getPlayerProgress().getTrophies(), t.getPlayerProgress().getIdCurrentPaddle(), t.getUsername()};
        return executeUpdate("Update player set coins=? , trophies = ? , current_paddle_id = ? where username = ?", params);
    }

    @Override
    public List<User> selectAll() {
        // TODO Auto-generated method stub
        return executeQuery("Select * from player");
    }

    @Override

    public User selectByConditions(String username) {
        // TODO Auto-generated method stub
        Object[] params = {username};
        return executeQuery("Select * from player where username = ?", params).get(0);
    }

    @Override
    protected Function<ResultSet, User> getMapper() {
        // TODO Auto-generated method stub
        Function<ResultSet, User> mapper = (ResultSet res) -> {
            //Chuyen doi ResultSet - > User object
            User user = new User();
            try {
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                user.setPlayerName(res.getString("player_name"));
                user.setPlayerProgress(new PlayerProgress(res.getInt("coins"), res.getInt("trophies"), res.getString("current_paddle_id")));
                user.getPlayerProgress().setIdPurchasedPaddles(selectAllPaddleInInventory(user));
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return user;
        };
        return mapper;
    }

    /*
     * ==========Player Inventory 
     */
    /**
     * Thêm mới vật phẩm vào tủ đồ
     *
     * @param t
     * @param idNewPaddle
     * @return
     */
    public boolean addPaddleIntoInventory(User t, String idNewPaddle) {
        Object[] params = {t.getPlayerName(), idNewPaddle};
        return executeUpdate("Insert into  playerinventory (player_name, paddle_id) values(?,?) ", params);
    }

    /**
     * Select all items of inventory
     */
    public List<String> selectAllPaddleInInventory(User t) {
        List<String> list = new ArrayList<>();
        try {
            Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("Select paddle_id from playerinventory where player_name=? ");
            ps.setObject(1, t.getPlayerName());
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                list.add(res.getString("paddle_id"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Query DB that bai");
            DatabaseConnection.closeConnection();
            e.printStackTrace();
        }
        return list;
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public void setInstance(UserDao instance) {
        this.instance = instance;
    }
}
