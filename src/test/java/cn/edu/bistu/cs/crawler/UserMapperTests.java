package cn.edu.bistu.cs.crawler;

import cn.edu.bistu.cs.crawler.dao.UserDaoImpl;
import cn.edu.bistu.cs.crawler.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserDaoImpl userDaoImpl;

    //查询整个用户表
    @Test
    public void selectAllUsers() {
        List<User> list = userDaoImpl.selectAllUser();
        for (User a : list) {
//        System.out.println("id:"+a.getId()+"   Name:"+a.getName());
            System.out.println(a.toString());
        }
    }

    //通过用户名查询
    @Test
    public void selectByName() {
        String findname = "alan";
        List<User> list1 = userDaoImpl.selectByName(findname);
        for (User b : list1) {
            System.out.println(b.toString());
        }
    }

    //增操作
    @Test
    public void addUser() {
        boolean result = userDaoImpl.addUser("glan", "123456", "user");
        if (result) System.out.println("添加用户成功");
    }

    //改操作
    @Test
    public void updateByName() {
        boolean result = userDaoImpl.updateByName("hlan", "12345", "admin");
        if (result) System.out.println("更改用户数据成功");
    }

    //删操作
    @Test
    public void deleteByName() {
        boolean result = userDaoImpl.deleteByName("flan");
        if (result) System.out.println("删除用户成功");
    }

}


