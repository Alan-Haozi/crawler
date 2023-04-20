package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedisAccessDao {
    // 存储CrawlerDto到redis队头
    public abstract boolean LpushData(String username, String url);

    // 从redis队尾取元素并移除
    public abstract CrawlerDto RpopData();
}
