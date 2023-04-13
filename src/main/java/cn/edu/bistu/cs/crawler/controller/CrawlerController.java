package cn.edu.bistu.cs.crawler.controller;

import cn.edu.bistu.cs.crawler.model.CrawlerData;
import cn.edu.bistu.cs.crawler.service.CrawlerService;
import cn.edu.bistu.cs.crawler.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Crawler")
public class CrawlerController {
    @Resource
    UserService userService;
    @Resource
    CrawlerService crawlerService;

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
}
