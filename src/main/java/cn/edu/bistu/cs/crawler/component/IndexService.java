package cn.edu.bistu.cs.crawler.component;

import cn.edu.bistu.cs.crawler.model.HtmlIndex;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class IndexService implements DisposableBean {
    @Resource
    IndexComponent indexComponent;
    private static final String DIR = "D:\\IdeaProjects\\crawler\\src\\main\\resources\\indexdir";
    // private static final Class<? extends Analyzer> DEFAULT_ANALYZER = StandardAnalyzer.class;
    private static final Class<? extends Analyzer> DEFAULT_ANALYZER = SmartChineseAnalyzer.class;
    private final IndexWriter indexWriter;

    /**
     * 索引初始化
     *
     * @throws Exception 索引初始化失败
     */
    public IndexService() throws Exception {
        // 2 索引目录类,指定索引在硬盘中的位置
        Directory directory = FSDirectory.open(Paths.get(DIR));
        // 3 创建分词器对象
        // 通过 DEFAULT_ANALYZER.getConstructor() 方法获取 StandardAnalyzer 的默认构造函数，
        // 然后使用 newInstance() 方法调用该构造函数创建 StandardAnalyzer 的实例，
        // 赋值给 analyzer 变量。这样可以动态地创建一个 Analyzer 的实例，而不需要显式地通过 new 关键字来创建。
        // 下面语句等于Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = DEFAULT_ANALYZER.getConstructor().newInstance();
//        Analyzer analyzer = new SmartChineseAnalyzer();
        // 4 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 5 创建索引的写出工具类。参数：索引的目录和配置信息
        indexWriter = new IndexWriter(directory, conf);
    }

    /**
     * 添加索引
     *
     * @param htmlIndex 索引模型
     * @return 是否成功
     */
    public boolean addDocument(HtmlIndex htmlIndex) {
        Document document = indexComponent.toDoc(htmlIndex);
        if (htmlIndex.getContent() == null) {
            return false;
        }
        try {
            // 6 把文档交给IndexWriter
            indexWriter.addDocument(document);
            // 7 提交
            indexWriter.commit();
        } catch (IOException e) {
            System.out.println("提交文档失败");
        }
        return true;
    }

    /**
     * 根据关键词对索引内容进行检索，并将检索结果返回
     *
     * @param kw 待检索的关键词
     * @return 检索得到的文档列表对应的HtmlIndex列表
     */
    public List<HtmlIndex> queryByKw(String username, String kw) throws Exception {
        //打开准实时索引Reader,使用IndexWriter打开DirectoryReader
        DirectoryReader reader = DirectoryReader.open(indexWriter);
        //创建IndexSearcher对象
        IndexSearcher searcher = new IndexSearcher(reader);
        //创建指定类型的分析器对象
        Analyzer analyzer = DEFAULT_ANALYZER.getConstructor().newInstance();
        //指定检索的字段和分析器对象创建QueryParser对象
        QueryParser parser1 = new QueryParser("title", analyzer);
        QueryParser parser2 = new QueryParser("content", analyzer);
        QueryParser parser3 = new QueryParser("username", analyzer);
        //根据待检索的关键词创建Query对象
        Query query1 = parser1.parse(kw);
        Query query2 = parser2.parse(kw);
        Query query3 = parser3.parse(username);
        // query1与query2的并集 与query3取交集
        BooleanQuery queryA = new BooleanQuery.Builder()
                .add(query1, BooleanClause.Occur.SHOULD)
                .add(query2, BooleanClause.Occur.SHOULD)
                .build();
        BooleanQuery query = new BooleanQuery.Builder()
                .add(queryA, BooleanClause.Occur.MUST)
                .add(query3, BooleanClause.Occur.MUST)
                .build();
        // 返回的结果是 按照匹配度排名得分前n名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
        TopDocs topDocs = searcher.search(query, 10);
        // 获取总条数
        System.out.println("queryByKw：本次搜索共找到" + topDocs.totalHits + "条数据");
        // 获取得分文档对象（ScoreDoc）数组, SocreDoc对象中包含的属性：文档的编号(doc)、文档的得分(score)
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //创建文档列表对象
        List<Document> doc = new ArrayList<>();
        //遍历得分文档对象数组
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 将得分文档对象添加到文档列表中
            // searcher.doc方法语法：Document doc = searcher.doc(docId);
            // 其中，docId 参数是一个文档的内部编号（即文档ID），
            // 该方法返回一个包含索引文档字段的 Document 对象，
            // 其中存储了文档的各个字段及其对应的值
            doc.add(searcher.doc(scoreDoc.doc));
        }
        List<HtmlIndex> results = new ArrayList<>();
        for (Document document : doc) {
            results.add(indexComponent.toHtmlIndex(document));
        }
        return results;
    }

    public boolean deleteDocumentById(int id) throws Exception {
        // 根据词条进行删除
        indexWriter.deleteDocuments(new Term("id", String.valueOf(id)));
        // 根据query对象删除,如果ID是数值类型，那么我们可以用数值范围查询锁定一个具体的ID
        // Query query = ...... ;
        //	writer.deleteDocuments(query);
        // 删除所有
        // indexWriter.deleteAll();
        // 提交
        indexWriter.commit();
        return true;
    }

    /**
     * 实现DisposableBean接口，该接口中只有一个方法destroy()，
     * 当Spring容器关闭时，会自动调用实现该接口的Bean的destroy()方法进行资源清理
     */
    @Override
    public void destroy() {
        if (this.indexWriter == null) {
            return;
        }
        try {
            // 进行优化操作，从而立即删除已标记为删除的文档
            indexWriter.forceMergeDeletes();
            System.out.println("destroy: 索引关闭");
            // 8 关闭
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("destroy: 尝试关闭索引失败");
        }
    }
}
