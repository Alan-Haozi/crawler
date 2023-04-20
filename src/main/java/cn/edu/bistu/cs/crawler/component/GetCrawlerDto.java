package cn.edu.bistu.cs.crawler.component;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.service.RedisAccessServiceImpl;
import cn.edu.bistu.cs.crawler.webmagic.DbPipeline;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// 定时器调用的方法
@Service
public class GetCrawlerDto {
    CrawlerDto crawlerDto;
    @Resource
    RedisAccessServiceImpl redisAccessServiceImpl;
    @Resource
    DbPipeline dbPipeline;

    // 获取CrawlerDto数据，传给Crawler启动爬虫
    public void getCrawlerDto() {
        crawlerDto = redisAccessServiceImpl.getredis();
        if (crawlerDto != null) {
            dbPipeline.url = crawlerDto.getUrl();
            dbPipeline.name = crawlerDto.getUsername();
            Crawler crawler = new Crawler(dbPipeline);
            crawler.executeCrawler();
        }
    }
}
