package cn.edu.bistu.cs.crawler.service;

import cn.edu.bistu.cs.crawler.dao.CrawlerDataDaoImpl;
import cn.edu.bistu.cs.crawler.model.CrawlerData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CrawlerServiceImpl implements CrawlerService {
    @Resource
    CrawlerDataDaoImpl crawlerDataDaoImpl;

    @Override
    public List<CrawlerData> crawlerPage(String name) {
        List<CrawlerData> list = crawlerDataDaoImpl.selectByName(name);
        return list;
    }

    @Override
    public boolean crawlerDataCreate(String name, String content, String url, String title) {
        LocalDateTime nowTime = LocalDateTime.now();
        // 暂时先设 success= 1
        int success = 1;
        boolean result = crawlerDataDaoImpl
                .addCrawlerData(url, content, nowTime, name, success, title);
        if (result)
            return true;
        return false;
    }

    @Override
    public boolean crawlerDataDelete(int id) {
        boolean result = crawlerDataDaoImpl.deleteById(id);
        if (result)
            return true;
        return false;
    }
}
