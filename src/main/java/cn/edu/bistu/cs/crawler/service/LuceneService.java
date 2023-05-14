package cn.edu.bistu.cs.crawler.service;

import cn.edu.bistu.cs.crawler.model.CrawlerData;
import cn.edu.bistu.cs.crawler.model.HtmlIndex;

import java.util.List;

public interface LuceneService {
    public abstract boolean addLucene(HtmlIndex htmlIndex);

    public abstract List<CrawlerData> searchLucene(String username, String kw);

    public abstract boolean deleteLucene(int id);
}
