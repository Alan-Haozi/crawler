package cn.edu.bistu.cs.crawler.service;

import cn.edu.bistu.cs.crawler.model.CrawlerData;

import java.util.List;


public interface CrawlerService {
    //返回爬虫数据表
    public abstract List<CrawlerData> crawlerPage(String name);

    //新增爬虫数据库内容
    public abstract int crawlerDataCreate(String name, String content, String url, String title, int success);

    //删除爬虫数据项
    public abstract boolean crawlerDataDelete(int id);

    /**
     * 根据id返回爬虫数据页面(带标签)
     *
     * @param id 爬虫数据库唯一id
     * @return 爬虫数据页面(带标签)
     */
    public abstract String crawlerContent(int id);

    /**
     * 根据id数组返回爬虫数据表
     *
     * @param a id数组
     * @return 爬虫数据表
     */
    public abstract List<CrawlerData> crawlerPageArray(int[] a);

    /**
     * 用id判断对应数据是否爬取成功
     * @param id 爬虫数据唯一id
     * @return success值
     */
    public abstract int isSuccess(int id);
}
