package cn.edu.bistu.cs.crawler.service;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;

import java.util.List;

public interface RedisAccessService {
    // 存储CrawlerDto到redis
    public abstract boolean addredis(String username, String url);

    // 从redis获取数据
    public abstract CrawlerDto getredis();
}

