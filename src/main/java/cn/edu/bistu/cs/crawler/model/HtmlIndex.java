package cn.edu.bistu.cs.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 页面建立索引的模型
 */
@Getter
@Setter
@NoArgsConstructor // 无参构造
@AllArgsConstructor // 全参构造
public class HtmlIndex {
    /**
     * 页面的唯一id,从mysql中获取
     */
    private int id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 页面标题
     */
    private String title;
    /**
     * 不带标签的html
     */
    private String content;
}
