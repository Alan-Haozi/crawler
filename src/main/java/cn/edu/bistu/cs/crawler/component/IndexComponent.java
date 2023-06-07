package cn.edu.bistu.cs.crawler.component;

import cn.edu.bistu.cs.crawler.model.HtmlIndex;
import org.apache.lucene.document.*;
import org.springframework.stereotype.Component;

@Component
public class IndexComponent {
    /**
     * 把索引对象转化成文档对象
     *
     * @param htmlIndex 索引类对象
     * @return 一个Document(一行内容)
     */
    public Document toDoc(HtmlIndex htmlIndex) {
        // 1 创建文档对象
        Document document = new Document();
        // 创建并添加字段信息
        // StringField, TextField都是Field子类
        // StringField, 一定会被创建索引,不会被分词
        // TextField, 既创建索引又会被分词
        // 参数：字段的名称、字段的值、是否存储，Store.YES代表存储到文档列表。Store.NO代表不存储
        document.add(new StringField("id", String.valueOf(htmlIndex.getId()), Field.Store.YES));
        document.add(new StringField("username", htmlIndex.getUsername(), Field.Store.YES));
        document.add(new TextField("title", htmlIndex.getTitle(), Field.Store.YES));
        document.add(new TextField("content", htmlIndex.getContent(), Field.Store.NO)); // 不带标签的html
        return document;
    }

    /**
     * @param doc 一个Document(一行内容)
     * @return htmlIndex 索引类对象
     */
    public HtmlIndex toHtmlIndex(Document doc) {
        int id = Integer.parseInt(doc.get("id"));
        String username = doc.get("username");
        String title = doc.get("title");
        String content = doc.get("content");
        return new HtmlIndex(id, username, title, content);
    }

}
