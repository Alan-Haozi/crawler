package cn.edu.bistu.cs.crawler.controller.dto;

import lombok.Data;
import lombok.ToString;
@Data
// 当程序输出一个对象或者把某个对象和字符串进行连接运算时，系统会自动调用该对象的 toString() 方法返回该对象的字符串表示
// 如：System.out.println("crawlerDto: " + crawlerDto.toString());中可以省略.toString()
@ToString
public class CrawlerDto{
    private String username;
    private String url;
/*    public CrawlerDto(){
    }
    public CrawlerDto(String username, String url) {
        this.username = username;
        this.url = url;
    }*/
}
