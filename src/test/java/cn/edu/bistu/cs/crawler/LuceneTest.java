package cn.edu.bistu.cs.crawler;

import cn.edu.bistu.cs.crawler.dao.CrawlerDataDaoImpl;
import cn.edu.bistu.cs.crawler.model.CrawlerData;
import cn.edu.bistu.cs.crawler.model.HtmlIndex;
import cn.edu.bistu.cs.crawler.service.LuceneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.selector.Html;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LuceneTest {
    @Resource
    CrawlerDataDaoImpl crawlerDataDaoImpl;
    @Resource
    LuceneService luceneServiceImpl;

    /**
     * 把数据库全部内容存索引
     */
    @Test
    public void indexAll() {
        List<CrawlerData> dataList = crawlerDataDaoImpl.selectAll();
        int i;
        int size = dataList.size();
        List<HtmlIndex> indexLists = new ArrayList<>();
        // 把List<CrawlerData>转化为List<HtmlIndex>
        for (i = 0; i < size; i++) {
            // 排除爬取失败的页面
            if (dataList.get(i).getSuccess() == 0) {
                size--;
            } else {
                HtmlIndex indexList = new HtmlIndex();
                indexList.setId(dataList.get(i).getId());
                indexList.setUsername(dataList.get(i).getUsername());
                indexList.setTitle(dataList.get(i).getTitle());
                Html page = new Html(dataList.get(i).getContent());
                String content = page.xpath("/allText()").get();
                indexList.setContent(content);
                indexLists.add(indexList);
            }
        }
        // 写索引
        for (HtmlIndex index : indexLists) {
            luceneServiceImpl.addLucene(index);
        }
    }
}
