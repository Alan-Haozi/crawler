package cn.edu.bistu.cs.crawler.controller;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.webmagic.DbPipeline;
import cn.edu.bistu.cs.crawler.webmagic.CrawlerProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import io.lettuce.core.*;

import javax.annotation.Resource;
//import java.util.Base64;


@RestController
@RequestMapping("/Crawler")
public class Crawler {

    @Resource
    private final DbPipeline dbPipeline;

    //有参构造器。Spring中，如果只有一个有参构造函数，Spring会自动将该构造函数中的参数进行自动注入，
    //无需显式地使用@Autowired注解
    public Crawler(@Autowired DbPipeline dbPipeline) {
        this.dbPipeline = dbPipeline;
    }

//    private final Base64.Decoder base64Decoder = Base64.getDecoder();


    // 执行爬虫函数,传递 username和 url
    @PostMapping("/View")
    public String geturl(@RequestBody CrawlerDto crawlerDto) {
        // 解码同时把 byte[]类型的 base64Decoder.decode(base64Url)转换为String类型
        // String base64Url = crawlerDto.getUrl();
        // String url = new String(base64Decoder.decode(base64Url));
        dbPipeline.name = crawlerDto.getUsername();
        try {
            Spider.create(new CrawlerProcessor())
                    .addUrl(crawlerDto.getUrl())
                    .thread(5)      //开启5个线程
                    .addPipeline(dbPipeline)
                    .run();
            return crawlerDto.getUrl();
        } catch (Exception e) {
            //TODO 修改数据库状态为失败
            return null;
        }
    }
}
