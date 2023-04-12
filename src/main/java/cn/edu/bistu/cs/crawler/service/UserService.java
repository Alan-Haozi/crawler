package cn.edu.bistu.cs.crawler.service;

import org.springframework.stereotype.Service;


public interface UserService {
    //创建用户
    public abstract boolean userCreate(String name, String password);

    //登录
    public abstract boolean userLogin(String name, String password);

}
