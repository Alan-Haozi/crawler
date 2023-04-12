package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.model.CrawlerData;
import org.apache.ibatis.annotations.Mapper;


import java.time.LocalDateTime;
import java.util.List;
@Mapper
public interface CrawlerDataDao {
    /*public abstract*/ List<CrawlerData> selectAll();//查询整个爬虫数据库

    /*public abstract*/ List<CrawlerData> selectById(int id)/* throws SQLException*/;//通过id查询
    /*public abstract*/ List<CrawlerData> selectByName(String name)/* throws SQLException*/;//通过name查询

    /*public abstract*/ boolean addCrawlerData(String url, String content, LocalDateTime time, String username, int success);//增操作

    /*public abstract*/ boolean updateById(int id, String url, String content, LocalDateTime time, String username, int success);//改操作

    /*public abstract*/ boolean deleteById(int id);//删操作

    /*public abstract*/ void resetIncrement();//重置id值，确保id不会跳过值，保持连续
}
