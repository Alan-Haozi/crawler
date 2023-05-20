package cn.edu.bistu.cs.crawler.controller.dto;
//用来接受登录页面的模型

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDto {
    private String username;
    private String password;
}
