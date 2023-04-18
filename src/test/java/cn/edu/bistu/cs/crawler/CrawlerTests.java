package cn.edu.bistu.cs.crawler;


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

    // 控制redis法一：
    @Test
    public void redis() {
        RedisClient redisClient = RedisClient.create("redis://@localhost:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("hellokey", "Hello, Redis!");
        Object value = syncCommands.get("hellokey");
        System.out.println("value的类型：" + value.getClass());
        System.out.println("value的值：" + value);
        connection.close();
        redisClient.shutdown();
    }

    // 控制redis法二：
    @Test
    public void RedisTemplate() {
        redisTemplate.opsForValue().set("redisTemplate", "Hello, Redis!");
        Object value = redisTemplate.opsForValue().get("redisTemplate");
        assert value != null;
        System.out.println("value的类型：" + value.getClass());
        System.out.println("value的值：" + value);
    }
}
