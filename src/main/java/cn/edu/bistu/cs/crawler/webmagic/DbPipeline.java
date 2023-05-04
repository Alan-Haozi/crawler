package cn.edu.bistu.cs.crawler.webmagic;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.service.CrawlerServiceImpl;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.selector.Html;

import javax.annotation.Resource;

@Service
public class DbPipeline extends ResultItemsCollectorPipeline implements Pipeline {
    public String name;
    public String url;
    // 爬取结果，true为正常
    public boolean res;

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
//        Html page = new Html(html);
//        page.xpath("/allText()").get();
        // 如果没有h1，String h1 = null值
        String title = resultItems.get("title");
        String h1 = resultItems.get("h1");
        // 如果title没有值，取h1的值，如果h1也没有值，则title取"暂无标题"
        if (title != null) {
            title = resultItems.get("title");
        } else {
            if (h1 != null) {
                title = resultItems.get("h1");
            } else {
                title = "暂无标题";
            }
        }
        //写数据库，状态为 1
        this.res = crawlerServiceImpl.crawlerDataCreate(this.name, html, url, title, 1);
    }
}
