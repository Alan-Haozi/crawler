package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisAccessDaoImpl implements RedisAccessDao {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
/*    不能这样下面这样装载，因为CrawlerDto中没有 @service，也不会用@service，应该用下面的planA或者B创建对象实例
    @Resource
    private CrawlerDto crawlerDto;*/

    @Override
    public boolean LpushData(String username, String url) {
        // plan A:对象实例自己注入
        CrawlerDto crawlerDto = new CrawlerDto();
        try {
            crawlerDto.setUsername(username);
            crawlerDto.setUrl(url);
            //转换为json
            ObjectMapper mapper = new ObjectMapper();
            String jsonCrawlerDto = mapper.writeValueAsString(crawlerDto);
            // 入库
            redisTemplate.opsForList().leftPush("urlAccess", jsonCrawlerDto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CrawlerDto RpopData() {
        // plan B:对象实例直接赋值
        CrawlerDto crawlerDto = null;
        Object obj = redisTemplate.opsForList().rightPop("urlAccess");
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (obj != null)
                crawlerDto = mapper.readValue(obj.toString(), CrawlerDto.class);
            // 后续看是否需要判断依据
            return crawlerDto;
        } catch (Exception e) {
            System.out.println("RedisAccessDaoImpl中RpopData方法报错");
            return null;
        }
    }
}
