package cn.edu.bistu.cs.crawler.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// 定时器任务
@Component
public class ScheduledService {
    @Resource
    GetCrawlerDto getCrawlerDto;
    // 定时器调用的GetCrawlerDto类中有定时器要调用的方法，
    // getCrawlerDto方法会从redis获取CrawlerDto数据，传给Crawler启动爬虫
    @Scheduled(fixedRate = 500)
    public void scheduled() {
        getCrawlerDto.getCrawlerDto();
    }
}
