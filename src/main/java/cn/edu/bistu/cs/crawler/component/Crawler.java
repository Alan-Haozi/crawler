package cn.edu.bistu.cs.crawler.component;

import cn.edu.bistu.cs.crawler.webmagic.DbPipeline;
import cn.edu.bistu.cs.crawler.webmagic.CrawlerProcessor;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
//import java.util.Base64;
public class Crawler {
    @Resource
    private final DbPipeline dbPipeline;

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
                        .addPipeline(dbPipeline)
                        .run();
            } catch (Exception e) {
                //TODO 修改数据库状态为失败
            }
    }
}
