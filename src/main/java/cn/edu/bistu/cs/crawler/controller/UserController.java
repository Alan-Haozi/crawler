package cn.edu.bistu.cs.crawler.controller;

import cn.edu.bistu.cs.crawler.controller.dto.LoginDto;

import cn.edu.bistu.cs.crawler.service.CrawlerService;
import cn.edu.bistu.cs.crawler.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    CrawlerService crawlerService;

    @PostMapping("/login")
    public boolean userLogin(@RequestBody LoginDto loginDto) {
        String name = loginDto.getUsername();
        String password = loginDto.getPassword();
        if (userService.userLogin(name, password))
            return true;
        else {
            return false;
        }
    }
}
