package cn.edu.bistu.cs.crawler.component;

import cn.edu.bistu.cs.crawler.model.HtmlIndex;
import cn.edu.bistu.cs.crawler.service.CrawlerServiceImpl;
import cn.edu.bistu.cs.crawler.webmagic.DbPipeline;
import cn.edu.bistu.cs.crawler.webmagic.CrawlerProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;
//import java.util.Base64;

import javax.annotation.Resource;

//爬虫类，启动爬虫，异常处理
@Component
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
        } catch (Exception e) {
            System.out.println("异常");
        }
        // 如果没有爬取结果，标记异常
        if (dbPipeline.id < 0) {
            String content = "";
            String title = "";
            // 写数据库，状态为 0，此时并没有运行process
            int id = crawlerServiceImpl.crawlerDataCreate(dbPipeline.name, content, dbPipeline.url, title, 0);
            System.out.println(id);
        }
    }
}
