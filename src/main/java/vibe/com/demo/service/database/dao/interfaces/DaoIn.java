package vibe.com.demo.service.database.dao.interfaces;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

public interface DaoIn<T> {

    public boolean insert(T t);

    public boolean delete(T t);

    public boolean update(T t);

    public List<T> selectAll();

    public T selectByConditions(String conditions);

}
