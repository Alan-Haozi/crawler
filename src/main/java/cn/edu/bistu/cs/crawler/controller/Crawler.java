package cn.edu.bistu.cs.crawler.controller;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.pipeline.DbPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import javax.annotation.Resource;
import java.util.Base64;


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

    private final Base64.Decoder base64Decoder = Base64.getDecoder();


    // 执行爬虫函数,传递 username和 url
    @PostMapping("/View")
    public String geturl(@RequestBody CrawlerDto crawlerDto) {
        String base64Url = crawlerDto.getUrl();
        dbPipeline.name = crawlerDto.getUsername();
        //解码同时把 byte[]类型的 base64Decoder.decode(base64Url)转换为String类型
//        String url = new String(base64Decoder.decode(base64Url));
//        String url = Arrays.toString(base64Decoder.decode(base64Url));
        Spider.create(new cn.edu.bistu.cs.crawler.service.Crawler())
                .addUrl(crawlerDto.getUrl())
                .thread(5)      //开启5个线程
                .addPipeline(dbPipeline)
                .run();
        return base64Url;
    }
}
