package cn.edu.bistu.cs.crawler.controller;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.model.CrawlerData;
import cn.edu.bistu.cs.crawler.service.CrawlerService;
import cn.edu.bistu.cs.crawler.service.RedisAccessServiceImpl;
import cn.edu.bistu.cs.crawler.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Crawler")
public class CrawlerController {
    @Resource
    CrawlerService crawlerService;
    @Resource
    RedisAccessServiceImpl redisAccessServiceimpl;

    //按照用户名返回数据库表
    @GetMapping("/View/{username}")
    public List<CrawlerData> showCrawlerData(@PathVariable String username) {
        return crawlerService.crawlerPage(username);
    }

    //根据id删除数据记录
    @DeleteMapping("/View/{id}")
    public boolean deleteCrawlerData(@PathVariable("id") int id) {
        boolean result = crawlerService.crawlerDataDelete(id);
        return result;
    }

    // 根据id返回页面的html
    @GetMapping("/Content/{id}")
    public String getCrawlerPage(@PathVariable int id) {
        String page = crawlerService.crawlerContent(id);
        return page;
    }

    // 从前端获取url和对应的username，保存redis
    @PostMapping("/View")
    public String geturl(@RequestBody CrawlerDto crawlerDto) {
        redisAccessServiceimpl.addredis(crawlerDto.getUsername(), crawlerDto.getUrl());
        return "存入成功";
    }
}
