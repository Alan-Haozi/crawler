package cn.edu.bistu.cs.crawler;

import cn.edu.bistu.cs.crawler.component.GetCrawlerDto;
import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.service.RedisAccessServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CrawlerTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedisAccessServiceImpl redisAccessServiceImpl;
    @Resource
    GetCrawlerDto getCrawlerDto;

    // 控制redis法一：
    @Test
    public void redisOrg() {
        RedisClient redisClient = RedisClient.create("redis://@localhost:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.set("redisorg", "Hello, Redis!");
        Object value = syncCommands.get("redisorg");
        System.out.println("value的类型：" + value.getClass());
        System.out.println("value的值：" + value);
        connection.close();
        redisClient.shutdown();
    }

    // 控制redis法二：
    @Test
    public void redisTemplate() {
        redisTemplate.opsForValue().set("redisTemplate", "Hello, Redis!");
        Object value = redisTemplate.opsForValue().get("redisTemplate");
        assert value != null;
        System.out.println("value的类型：" + value.getClass());
        System.out.println("value的值：" + value);
    }

    // 往redis存入对象，并取出
    @Test
    public void redisTemplateList() {
        CrawlerDto crawlerDto = new CrawlerDto();
        crawlerDto.setUsername("alan");
        crawlerDto.setUrl("hello,alan");
        try {
            //转换为json
            ObjectMapper mapper = new ObjectMapper();
            String jsonCrawlerDto = mapper.writeValueAsString(crawlerDto);
            // 入库
            redisTemplate.opsForList().leftPush("urlAccess", jsonCrawlerDto);
            //出库
            Object obj = redisTemplate.opsForList().rightPop("urlAccess");
            if (obj != null) {
                // 将JSON字符串转换为对象实例
                CrawlerDto crawlerDto2 = mapper.readValue(obj.toString(), CrawlerDto.class);
                assert crawlerDto2 != null;
                System.out.println("username：" + crawlerDto2.getUsername());
                System.out.println("url：" + crawlerDto2.getUrl());
            } else System.out.println("列表为空");
        } catch (Exception e) {
            System.out.println("出错");
        }
    }
    // 改进测试
    @Test
    public void redisTemplateList2() {
        CrawlerDto crawlerDto = new CrawlerDto();
        crawlerDto.setUsername("alan");
        crawlerDto.setUrl("hello,alan");
        try {
            //转换为json
            ObjectMapper mapper = new ObjectMapper();
            String jsonCrawlerDto = mapper.writeValueAsString(crawlerDto);
            // 入库
            redisTemplate.opsForList().leftPush("urlAccess", jsonCrawlerDto);
            //出库
            Object obj = redisTemplate.opsForList().rightPop("urlAccess");
            if (obj != null) {
                CrawlerDto crawlerDto2 = (CrawlerDto) obj;
/*            Object obj = redisTemplate.opsForList().rightPop("urlAccess");
            if (obj != null) {
                // 将JSON字符串转换为对象实例
                CrawlerDto crawlerDto2 = mapper.readValue(obj.toString(), CrawlerDto.class);*/
                System.out.println("username：" + crawlerDto2.getUsername());
                System.out.println("url：" + crawlerDto2.getUrl());
            } else System.out.println("列表为空");
        } catch (
                Exception e) {
            System.out.println("出错");
        }

    }

    // 添加对象到redis队列头中
    @Test
    public void addredis() {
        boolean result = redisAccessServiceImpl.addredis("alan", "12345");
    }

    // 从redis中获取队尾对象
    @Test
    public void getredis() {
        CrawlerDto crawlerDto;
        crawlerDto = redisAccessServiceImpl.getredis();
        if (crawlerDto != null) {
            System.out.println("username：" + crawlerDto.getUsername());
            System.out.println("url：" + crawlerDto.getUrl());
        } else {
            System.out.println("redis中已没有值");
        }
    }

    // 定时器任务单体
    @Test
    public void scheduled() {
        getCrawlerDto.getCrawlerDto();
    }
    // 对象和json相互转换
    @Test
    public void Converter() {
        CrawlerDto crawlerDto = new CrawlerDto();
        crawlerDto.setUsername("alan");
        crawlerDto.setUrl("12345");
        System.out.println("crawlerDto: " + crawlerDto);
        try {
            // 将对象转换为JSON字符串
            ObjectMapper mapper = new ObjectMapper();
            String jsonCrawlerDto = mapper.writeValueAsString(crawlerDto);
            System.out.println("jsonCrawlerDto: " + jsonCrawlerDto);
            // 将JSON字符串转换为对象实例
            CrawlerDto crawlerDto2 = mapper.readValue(jsonCrawlerDto, CrawlerDto.class);
            System.out.println("crawlerDto2: " + crawlerDto2);
        } catch (Exception e) {
            System.out.println("1");
        }
    }
    //        redisTemplate.opsForList().leftPush("urlAccess", crawlerDto);
    //       这个可以用： redisTemplate.opsForList().leftPush("name", "name");
}

