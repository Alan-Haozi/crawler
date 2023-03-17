package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.model.User;

import java.sql.SQLException;
import java.util.List;
public interface UserDao {
    public abstract List<User> selectAllUser();//查询整个用户表
    public abstract List<User> selectByName(String str)/* throws SQLException*/;//通过用户名查询
    public abstract boolean addUser(String name,String password,String role);//增操作
    public abstract boolean updateByName(String name,String password,String role);//改操作
    public abstract boolean deleteByName(String name);//删操作
    public abstract void resetIncrement();//重置id值，确保id不会跳过值，保持连续

}
