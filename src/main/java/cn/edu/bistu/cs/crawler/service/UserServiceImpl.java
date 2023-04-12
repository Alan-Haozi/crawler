package cn.edu.bistu.cs.crawler.service;


import cn.edu.bistu.cs.crawler.dao.UserDaoImpl;
import cn.edu.bistu.cs.crawler.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDaoImpl userDaoImpl;

    @Override
    public boolean userCreate(String name, String password) {
        boolean result = userDaoImpl.addUser(name, password, "user");
        if (result)
            return true;
        return false;
    }
    //用户登录，根据name和password判断数据库中是否匹配
    @Override
    public boolean userLogin(String name, String password) {
        List<User> list = userDaoImpl.selectByName(name);
        //List的get方法能获取特定元素
        if (Objects.equals(list.get(0).getPassword(), password))
            return true;
        return false;
    }
}
