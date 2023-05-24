package cn.edu.bistu.cs.crawler.service;

import cn.edu.bistu.cs.crawler.component.IndexService;
import cn.edu.bistu.cs.crawler.dao.CrawlerDataDao;
import cn.edu.bistu.cs.crawler.model.CrawlerData;
import cn.edu.bistu.cs.crawler.model.HtmlIndex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneServiceImpl implements LuceneService {
    @Resource
    IndexService indexService;
    @Resource
    CrawlerServiceImpl crawlerServiceimpl;

    // 还有问题
    @Override
    public boolean addLucene(HtmlIndex htmlIndex) {
        indexService.addDocument(htmlIndex);
        return true;
    }

    @Override
    public List<CrawlerData> searchLucene(String username, String kw) {
        try {
            List<HtmlIndex> indexList = new ArrayList<>();
            indexList = indexService.queryByKw(username, kw);
            // 使用了 stream() 方法将 indexList 转换为一个流，
            // 然后使用 mapToInt() 方法将 HtmlIndex 对象映射为它们的 id 值，
            // 最后使用 toArray() 方法将结果收集到一个 int 数组 a 中
            int[] a = indexList.stream()
                    .mapToInt(HtmlIndex::getId)
                    .toArray();
/*
            上面的代码等于：
            int size = indexList.size();
            int[] a = new int[size];
            int i;
            for (i = 0; i < size; i++) {
                    a[i] = indexList.get(i).getId();
            }
*/
            List<CrawlerData> searchList = crawlerServiceimpl.crawlerPageArray(a);
            return searchList;
        } catch (Exception e) {
            System.out.println("LuceneServiceImpl: Lucene搜索无结果或出错");
            return null;
        }
    }

    @Override
    public boolean deleteLucene(int id) {
        try {
            indexService.deleteDocumentById(id);
        } catch (Exception e) {
            System.out.println("LuceneServiceImpl类deleteLucene方法：删除索引出错");
            return false;
        }
        return true;
    }
}
