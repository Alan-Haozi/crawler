package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.model.User;
import org.apache.catalina.mbeans.UserMBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> selectAllUser() {
        String sql = "select * from user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> selectByName(String str) /*throws SQLException*/ {
        String sql = "select * from user where name = '"+str+"'";
//        String sql = "select * from user where name=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
//        return jdbcTemplate.query(sql, new Object[]{str}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public boolean addUser(User user) {

        return true;
    }
}
