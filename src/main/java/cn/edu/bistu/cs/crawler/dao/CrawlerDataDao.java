package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.model.CrawlerData;
import org.apache.ibatis.annotations.Mapper;


import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CrawlerDataDao {
    /**
     * 查询整个爬虫数据库
     *
     * @return 爬虫数据对象数组
     */
    /*public abstract*/ List<CrawlerData> selectAll();

    /**
     * 通过id查询
     *
     * @param id 爬虫数据项唯一id
     * @return 爬虫数据对象数组
     */
    /*public abstract*/ List<CrawlerData> selectById(int id)/* throws SQLException*/;

    /**
     * 通过整形id数组查询对应内容
     * @param a 整形数组
     * @return 爬虫数据对象数组
     */
    /*public abstract*/ List<CrawlerData> selectByIdArray(int[] a)/* throws SQLException*/;

    /**
     * 通过name查询
     *
     * @param name 用户名
     * @return 爬虫数据对象数组
     */
    /*public abstract*/ List<CrawlerData> selectByName(String name)/* throws SQLException*/;

    /**
     * 增操作
     *
     * @param url      url
     * @param content  内容(带标签)
     * @param time     time
     * @param username 用户名
     * @param success
     * @param title
     * @return 添加数据是否成功
     */
    /*public abstract*/ int addCrawlerData(String url, String content, LocalDateTime time, String username, int success, String title);

    /**
     * 改操作
     *
     * @param id       爬虫数据项唯一id
     * @param url      url
     * @param content 页面内容(带标签)
     * @param time     time
     * @param username 用户名
     * @param success 是否爬取成功
     * @param title 标题
     * @return 新增的id值，产生错误时值为-1
     */
    /*public abstract*/ boolean updateById(int id, String url, String content, LocalDateTime time, String username, int success, String title);//

    /**
     * 删操作
     *
     * @param id 爬虫数据项唯一id
     * @return 是否删除成功
     */
    /*public abstract*/ boolean deleteById(int id);

    /**
     * 重置id值，确保id不会跳过值，保持连续
     */
    /*public abstract*/ void resetIncrement();
}
