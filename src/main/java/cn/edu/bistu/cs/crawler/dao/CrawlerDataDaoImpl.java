package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.model.CrawlerData;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CrawlerDataDaoImpl implements CrawlerDataDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CrawlerData> selectAll() {
        String sql = "select * from crawlerdata";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CrawlerData.class));
    }

    @Override
    public List<CrawlerData> selectById(int id) {
        String sql = "select * from crawlerdata where id =" + id;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CrawlerData.class));
    }

    @Override
    @Transactional
    public boolean addCrawlerData(String url, String content, LocalDateTime time, String username, int success) {
        try {
            CrawlerData crawlerData = new CrawlerData(url, content, time, username, success);
            String sql = "insert into crawlerdata(url,content,time,username,success)values(?,?,?,?,?)";
            Object[] params = {crawlerData.getUrl(), crawlerData.getContent(),
                    crawlerData.getTime(), crawlerData.getUsername(), crawlerData.getSuccess()};
            resetIncrement();
            if (jdbcTemplate.update(sql, params) <= 0) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            System.out.println("添加数据失败");
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(int id, String url, String content, LocalDateTime time, String username, int success) {
        try {
//            CrawlerData crawlerData = new CrawlerData(id, url, content, time, username, success);
            String sql = "update crawlerdata set url = ?, content = ?,time=?,"
                    + "username=?, success=? where id = ?";
            Object[] params = {url, content, time, username, success, id};
            resetIncrement();
            int result = jdbcTemplate.update(sql, params);
            if (result <= 0) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            System.out.println("更改爬虫数据失败");
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        try {
            String sql = "delete from crawlerdata where id=?";
            Object[] params = {id};
            resetIncrement();
            if (jdbcTemplate.update(sql, params) <= 0) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            System.out.println("删除爬虫数据失败");
            return false;
        }
        return true;
    }

    @Override
    public void resetIncrement() {
        String sql = "alter table crawlerdata AUTO_INCREMENT = 1";
        jdbcTemplate.execute(sql);
    }
}
