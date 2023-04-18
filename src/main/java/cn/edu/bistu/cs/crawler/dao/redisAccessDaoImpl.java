package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class redisAccessDaoImpl implements redisAccessDao {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private CrawlerDto crawlerDto;

    @Override
    public boolean LpushData(String username, String url) {
        try {
            crawlerDto.setUsername(username);
            crawlerDto.setUrl(url);
            redisTemplate.opsForList().leftPush("urlAccess", crawlerDto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void RpopData() {
        Object obj = redisTemplate.opsForList().rightPop("urlAccess");
        crawlerDto = (CrawlerDto) obj;
    }
}
