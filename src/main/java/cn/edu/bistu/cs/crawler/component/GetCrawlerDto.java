package cn.edu.bistu.cs.crawler.component;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.service.RedisAccessServiceImpl;
import cn.edu.bistu.cs.crawler.webmagic.DbPipeline;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// 定时器调用的方法
@Component
public class GetCrawlerDto {
    CrawlerDto crawlerDto;
    @Resource
    RedisAccessServiceImpl redisAccessServiceImpl;
    @Resource
    DbPipeline dbPipeline;
    // 带参数的自动注入
    @Resource
    Crawler crawler = new Crawler(dbPipeline);

    // 从redis获取CrawlerDto数据，传给Crawler启动爬虫
    public void getCrawlerDto() {
        crawlerDto = redisAccessServiceImpl.getredis();
        if (crawlerDto != null) {
            dbPipeline.url = crawlerDto.getUrl();
            dbPipeline.name = crawlerDto.getUsername();
//           不要用new: Crawler crawler = new Crawler(dbPipeline)，否则Crawler类中引用的其它对象不会实例化
            crawler.executeCrawler();
        }
    }
}
