package cn.edu.bistu.cs.crawler.webmagic;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.service.CrawlerServiceImpl;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

@Service
public class DbPipeline implements Pipeline {
    public String name;
    boolean res;

    // 无参构造
    public DbPipeline() {

    }

    public DbPipeline(CrawlerDto crawlerDto) {
        this.name = crawlerDto.getUsername();
    }

    @Resource
    CrawlerServiceImpl crawlerServiceImpl;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();
        String html = resultItems.get("html");
        // 如果没有h1，String h1 = null值
        String title = resultItems.get("title");
        // 如果title没有值，取h1的值
        if (title==null) title = resultItems.get("h1");
        //写数据库
        this.res = crawlerServiceImpl.crawlerDataCreate(this.name, html, url, title);
    }
}
