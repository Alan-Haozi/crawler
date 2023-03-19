package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.model.User;
import org.apache.catalina.mbeans.UserMBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    //查询整个用户表
    @Override
    public List<User> selectAllUser() {
        String sql = "select * from user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    //通过用户名查询
    @Override
    public List<User> selectByName(String str) /*throws SQLException*/ {
        String sql = "select * from user where name ='" + str + "'";
//        String sql = "select * from user where name=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
//        return jdbcTemplate.query(sql, new Object[]{str}, new BeanPropertyRowMapper<>(User.class));
    }

    //增操作
    @Override
    @Transactional
    public boolean addUser(String name, String password, String role) {
        try {
            User user = new User(name, password, role);
            String sql = "insert into user(name,password,role)values(?,?,?)";
            Object[] params = {user.getName(), user.getPassword(), user.getRole()};
            resetIncrement();
            if (jdbcTemplate.update(sql, params) <= 0) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            System.out.println("添加用户失败");
            return false;
        }
        return true;
    }

    //改操作
    @Override
    @Transactional
    public boolean updateByName(String name, String password, String role) {
        try {
            String sql = "update user SET password = ?, role = ? where name = ?";
            Object[] params = {password, role, name};
            resetIncrement();
            int result = jdbcTemplate.update(sql, params);
            if (result <= 0) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            System.out.println("更改用户数据失败");
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        try {
            String sql = "delete from user where name=?";
            Object[] params = {name};
            resetIncrement();
            if (jdbcTemplate.update(sql, params) <= 0) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            System.out.println("删除用户失败");
            return false;
        }
        return true;
    }
    @Override
    public void resetIncrement() {
        String sql = "alter table user AUTO_INCREMENT = 1";
        jdbcTemplate.execute(sql);
    }
}
