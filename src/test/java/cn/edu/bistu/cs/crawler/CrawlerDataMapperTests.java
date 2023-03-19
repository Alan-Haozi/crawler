package cn.edu.bistu.cs.crawler;

import cn.edu.bistu.cs.crawler.dao.CrawlerDataDaoImpl;
import cn.edu.bistu.cs.crawler.model.CrawlerData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerDataMapperTests {
    @Autowired
    CrawlerDataDaoImpl crawlerDataDaoImpl;
    //将一个String Datetime转换成LocalDateTime对象
    private LocalDateTime toLocalDateTime(String strDateTime) {
        // 创建一个 DateTimeFormatter 对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 使用 DateTimeFormatter 解析字符串并将其转换为 LocalDateTime 对象
        LocalDateTime localDateTime = LocalDateTime.parse(strDateTime, formatter);
        return localDateTime;
    }

    @Test
    public void selectAll() {
        List<CrawlerData> list = crawlerDataDaoImpl.selectAll();
        for (CrawlerData a : list) {
            System.out.println(a.toString());
        }
    }

    @Test
    public void selectById() {
        int id = 2;
        List<CrawlerData> list = crawlerDataDaoImpl.selectById(id);
        for (CrawlerData b : list) {
            System.out.println(b.toString());
        }
    }

    @Test
    public void addCrawlerData() {
//        String datetime = "2019-04-08 15:07:30";
        boolean result = crawlerDataDaoImpl.addCrawlerData
                ("aw", "qwe", toLocalDateTime("2019-04-08 15:07:30"),
                        "alan", 1);
        if (result) System.out.println("添加数据成功");
    }
    @Test
    public void updateById(){
        boolean result=crawlerDataDaoImpl.updateById(2,"dinwdq","291dh",
                toLocalDateTime("2023-12-11 13:24:56"),"blan",1);
        if (result) System.out.println("更新数据成功");
    }
}
