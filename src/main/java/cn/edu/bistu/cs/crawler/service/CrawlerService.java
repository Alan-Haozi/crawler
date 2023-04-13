package cn.edu.bistu.cs.crawler.service;

import cn.edu.bistu.cs.crawler.model.CrawlerData;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CrawlerService {
    //返回爬虫数据表
    public abstract List<CrawlerData> crawlerPage(String name);

    //新增爬虫数据库内容
    public abstract boolean crawlerDataCreate(String name, String content, String url, String title);

    //删除爬虫数据项
    public abstract boolean crawlerDataDelete(int id);
}
