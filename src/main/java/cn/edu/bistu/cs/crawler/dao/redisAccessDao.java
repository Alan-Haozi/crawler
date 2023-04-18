package cn.edu.bistu.cs.crawler.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface redisAccessDao {
    // 存储CrawlerDto到redis队头
    public abstract boolean LpushData(String username, String url);

    // 从redis队尾取元素并移除
    public abstract void RpopData();
}
