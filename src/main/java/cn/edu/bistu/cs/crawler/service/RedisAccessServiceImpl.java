package cn.edu.bistu.cs.crawler.service;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.dao.RedisAccessDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisAccessServiceImpl implements RedisAccessService {
    @Resource
    RedisAccessDaoImpl redisAccessDaoImpl;

    @Override
    public boolean addredis(String username, String url) {
        boolean res = redisAccessDaoImpl.LpushData(username, url);
        return res;
    }

    @Override
    public CrawlerDto getredis() {
        CrawlerDto crawlerDto;
        crawlerDto = redisAccessDaoImpl.RpopData();
        return crawlerDto;
    }
}
