package cn.edu.bistu.cs.crawler.component;

import cn.edu.bistu.cs.crawler.service.CrawlerServiceImpl;
import cn.edu.bistu.cs.crawler.webmagic.DbPipeline;
import cn.edu.bistu.cs.crawler.webmagic.CrawlerProcessor;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
//import java.util.Base64;

import javax.annotation.Resource;

@Service
public class Crawler {
    // 不用加 @Resource
    private final DbPipeline dbPipeline;
    @Resource
    CrawlerServiceImpl crawlerServiceImpl;


    public Crawler(DbPipeline dbPipeline) {
        this.dbPipeline = dbPipeline;
    }

    //  private final Base64.Decoder base64Decoder = Base64.getDecoder();

    // 执行爬虫函数,从redis中获取name和url，并执行爬虫
    public void executeCrawler() {
        // 解码同时把 byte[]类型的 base64Decoder.decode(base64Url)转换为String类型
        // String base64Url = crawlerDto.getUrl();
        // String url = new String(base64Decoder.decode(base64Url));
        try {
            Spider.create(new CrawlerProcessor())
                    .addUrl(dbPipeline.url)
                    .thread(5)      //开启5个线程
                    .addPipeline(dbPipeline).run();
            // 如果没有爬取结果，抛出异常
            if (!dbPipeline.res) {
                throw new Exception("没有爬取结果");
            }
        } catch (Exception e) {
            String content = "";
            String title = "";
            //写数据库，状态为 0，有问题：crawlerServiceImpl为空
            boolean res = crawlerServiceImpl.crawlerDataCreate(dbPipeline.name, content, dbPipeline.url, title, 0);
            System.out.println(res);
        }
    }
}
