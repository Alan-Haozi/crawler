package cn.edu.bistu.cs.crawler;

import cn.edu.bistu.cs.crawler.dao.UserDaoImpl;
import cn.edu.bistu.cs.crawler.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserDaoImpl userDaoImpl;

    @Test
    public void getAllUsers() {
        List<User> list = userDaoImpl.selectAllUser();
        for (User a : list) {
//        System.out.println("id:"+a.getId()+"   Name:"+a.getName());
            System.out.println(a.toString());
        }
    }

    @Test
    public void findByName() {
        String findname = "blan";
        List<User> list1 = userDaoImpl.selectByName(findname);
        for (User b : list1) {
            System.out.println(b.toString());
        }
    }
}


