package cn.edu.bistu.cs.crawler.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ScheduledService {
    @Resource
    GetCrawlerDto getCrawlerDto;

    @Scheduled(fixedRate = 500)
    public void scheduled() {
        getCrawlerDto.getCrawlerDto();
    }
}
