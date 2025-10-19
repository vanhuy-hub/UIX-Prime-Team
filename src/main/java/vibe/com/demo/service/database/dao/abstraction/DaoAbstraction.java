package vibe.com.demo.service.database.dao.abstraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import vibe.com.demo.service.database.utils.DatabaseConnection;

public abstract class DaoAbstraction<T> {

    protected abstract Function<ResultSet, T> getMapper();

    /**
     * Hàm thực hiện chức năng update/ delete/ add
     *
     * @throws SQLException
     */
    protected boolean executeUpdate(String query, Object... params) {
        Connection c;
        try {
            c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                System.out.println(params[i] + ":" + params[i].getClass());
                ps.setObject(i + 1, params[i]);
            }
            int res = ps.executeUpdate();
            if (res > 0) {
                System.out.println("Update DB thanh cong");
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Update DB that bai");
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection();
        }

        return false;
    }

    /**
     * Hàm thực hiên truy vấn
     */
    protected List<T> executeQuery(String query, Object... params) {
        List<T> list = new ArrayList<>();
        try {
            Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                System.out.println(params[i] + ":" + params[i].getClass());
                ps.setObject(i + 1, params[i]);
            }
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                T item = getMapper().apply(res);
                if (item != null) {
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Query DB that bai");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }
        return list;
    }

}
