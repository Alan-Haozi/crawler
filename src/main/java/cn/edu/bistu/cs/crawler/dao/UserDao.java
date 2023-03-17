package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.model.User;

import java.sql.SQLException;
import java.util.List;
public interface UserDao {
    public abstract List<User> selectAllUser();//查询整个用户表
    public abstract List<User> selectByName(String str)/* throws SQLException*/;//通过用户名查询
    public abstract boolean addUser(User user);//增

}
