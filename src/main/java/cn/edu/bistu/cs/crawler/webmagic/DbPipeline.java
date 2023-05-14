package cn.edu.bistu.cs.crawler.webmagic;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.model.HtmlIndex;
import cn.edu.bistu.cs.crawler.service.CrawlerServiceImpl;
import cn.edu.bistu.cs.crawler.service.LuceneServiceImpl;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;
import us.codecraft.webmagic.selector.Html;

import javax.annotation.Resource;

// 处理爬虫结果
@Service
public class DbPipeline extends ResultItemsCollectorPipeline implements Pipeline {
    public String name;
    public String url;
    // 爬取结果对应id，大于0为正常
    public int id = -1;
    HtmlIndex htmlIndex = new HtmlIndex();

    // 无参构造
    public DbPipeline() {
    }

    public DbPipeline(CrawlerDto crawlerDto) {
        this.name = crawlerDto.getUsername();
    }

    @Resource
    CrawlerServiceImpl crawlerServiceImpl;
    @Resource
    LuceneServiceImpl luceneServiceImpl;

    /**
     * 页面被抓取成功后，会将该页面的数据传递给Process进行处理
     *
     * @param resultItems 存储抓取到的数据
     * @param task        当前的任务信息，可以使用Task对象获取任务的唯一标识符等
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();
        String html = resultItems.get("html");
        Html page = new Html(html);
        String content = page.xpath("/allText()").get();
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
        this.id = crawlerServiceImpl.crawlerDataCreate(this.name, html, url, title, 1);
        // 存成功索引
        htmlIndex.setId(id);
        htmlIndex.setUsername(this.name);
        htmlIndex.setTitle(title);
        htmlIndex.setContent(content);
        luceneServiceImpl.addLucene(htmlIndex);
    }
}
