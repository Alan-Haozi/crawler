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

    @GetMapping("/View/{username}")
    public List<CrawlerData> showCrawlerData(@PathVariable String username) {
        return crawlerService.crawlerPage(username);
    }
}
