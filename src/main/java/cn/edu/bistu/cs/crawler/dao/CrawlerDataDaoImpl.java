package cn.edu.bistu.cs.crawler.dao;

import cn.edu.bistu.cs.crawler.model.CrawlerData;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

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
    public List<CrawlerData> selectByIdArray(int[] a) {
        // 创建一个用逗号分隔的字符串拼接器，起始字符串为 "("，结束字符串为 ")"
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        for (int id : a) {
            joiner.add(String.valueOf(id));
        }
        String sql = "SELECT * FROM crawlerdata WHERE id IN " + joiner;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CrawlerData.class));
    }

    @Override
    public List<CrawlerData> selectByName(String name) {
        String sql = "select * from crawlerdata where username ='" + name + "'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CrawlerData.class));
    }

    @Override
    @Transactional
    public int addCrawlerData(String url, String content, LocalDateTime time, String username, int success, String title) {
        CrawlerData crawlerData = new CrawlerData(url, content, time, username, success, title);
        String sql = "insert into crawlerdata(url,content,time,username,success,title)values(?,?,?,?,?,?)";
        Object[] params = {crawlerData.getUrl(), crawlerData.getContent(), crawlerData.getTime(),
                crawlerData.getUsername(), crawlerData.getSuccess(), crawlerData.getTitle()};
        resetIncrement();
        if (jdbcTemplate.update(sql, params) <= 0) {
            System.out.println("添加数据失败");
            return -1;
        }
        // 获取自增id值
        Integer generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        if (generatedId == null) {
            System.out.println("无法检索到生成的ID。");
            return -1;
        }
        System.out.println("获取的id值：" + generatedId);
        return generatedId;
    }

    @Override
    @Transactional
    public boolean updateById(int id, String url, String content, LocalDateTime time, String username, int success, String title) {
        try {
            // CrawlerData crawlerData = new CrawlerData(id, url, content, time, username, success);
            String sql = "update crawlerdata set url = ?, content = ?,time=?,"
                    + "username=?, success=?, title=? where id = ?";
            Object[] params = {url, content, time, username, success, title, id};
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
