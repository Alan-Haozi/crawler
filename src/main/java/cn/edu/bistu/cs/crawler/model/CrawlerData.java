package cn.edu.bistu.cs.crawler.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CrawlerData {
    private int id;
    private String url;
    private String content;
    private LocalDateTime time;
    private String username;
    /**
     * 是否爬取成功，成功为1，失败为0
     */
    private int success;
    private String title;

    private String formattedDateTime() {
        // 创建一个 DateTimeFormatter 对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // time为一个LocalDateTime对象，此时使用 DateTimeFormatter 格式化 LocalDateTime 对象
        String formattedDateTime = this.time.format(formatter);
        return formattedDateTime;
    }


    //无参构造器
    public CrawlerData() {
    }

    //除id外构造器
    public CrawlerData(String url, String content, LocalDateTime time, String username, int success, String title) {
        this.url = url;
        this.content = content;
        this.time = time;
        this.username = username;
        this.success = success;
        this.title = title;
    }

    //全参构造器
    public CrawlerData(int id, String url, String content, LocalDateTime time, String username, int success, String title) {
        this.id = id;
        this.url = url;
        this.content = content;
        this.time = time;
        this.username = username;
        this.success = success;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSuccess() { return success; }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return "CrawlerData{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", username='" + username + '\'' +
                ", success=" + success +
                ", title='" + title + '\'' +
                '}';
    }
}
