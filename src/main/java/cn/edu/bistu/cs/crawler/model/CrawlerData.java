package cn.edu.bistu.cs.crawler.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CrawlerData {
    private int id;
    private String url;
    private String content;
    private LocalDateTime time;
    private String username;
    private int success;

    private String formattedDateTime(){
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
    public CrawlerData(String url, String content, LocalDateTime time, String username, int success) {
        this.url = url;
        this.content = content;
        this.time = time;
        this.username = username;
        this.success = success;
    }

    //全参构造器
    public CrawlerData(int id, String url, String content, LocalDateTime time, String username, int success) {
        this.id = id;
        this.url = url;
        this.content = content;
        this.time = time;
        this.username = username;
        this.success = success;
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

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public String toString() {

        return "CrawlerData{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                //输出格式化后字符串
                ", time=" + formattedDateTime() +
                ", username='" + username + '\'' +
                ", success=" + success +
                '}';
    }
}
