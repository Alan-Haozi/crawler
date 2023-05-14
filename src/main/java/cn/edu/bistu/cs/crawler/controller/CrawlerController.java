package cn.edu.bistu.cs.crawler.controller;

import cn.edu.bistu.cs.crawler.controller.dto.CrawlerDto;
import cn.edu.bistu.cs.crawler.model.CrawlerData;
import cn.edu.bistu.cs.crawler.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Crawler")
public class CrawlerController {
    @Resource
    CrawlerServiceImpl crawlerServiceImpl;
    @Resource
    RedisAccessServiceImpl redisAccessServiceimpl;
    @Resource
    LuceneServiceImpl luceneServiceImpl;

    //按照用户名返回数据库表
    @GetMapping("/View/{username}")
    public List<CrawlerData> showCrawlerData(@PathVariable String username) {
        return crawlerServiceImpl.crawlerPage(username);
    }

    //根据id删除数据记录
    @DeleteMapping("/View/{id}")
    public boolean deleteCrawlerData(@PathVariable("id") int id) {
        // 获取success状态，如果为1，则删除索引，0则不用
        if (crawlerServiceImpl.isSuccess(id) == 1)
            // 删除索引
            luceneServiceImpl.deleteLucene(id);
        //删除爬虫数据库对应项内容
        boolean result = crawlerServiceImpl.crawlerDataDelete(id);
        return result;
    }

    // 根据id返回页面的html
    @GetMapping("/Content/{id}")
    public String getCrawlerPage(@PathVariable int id) {
        String content = crawlerServiceImpl.crawlerContent(id);
        return content;
    }

    // 从前端获取url和对应的username，保存redis
    @PostMapping("/View")
    public String geturl(@RequestBody CrawlerDto crawlerDto) {
        // 往redis存入用户名和url
        redisAccessServiceimpl.addredis(crawlerDto.getUsername(), crawlerDto.getUrl());
        return "存入成功";
    }

    // 前端获取搜索内容
    @GetMapping("/View/SearchContent")
    public List<CrawlerData> searchCrawlerData(@RequestParam("username") String username,
                                               @RequestParam("searchContent") String searchContent) {
        List<CrawlerData> list = luceneServiceImpl.searchLucene(username, searchContent);
        return list;
    }
}
