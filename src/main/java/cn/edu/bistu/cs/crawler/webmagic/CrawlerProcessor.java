package cn.edu.bistu.cs.crawler.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class CrawlerProcessor implements PageProcessor {
    private final Site site = Site.me()
            .setCharset("utf8")     //设置编码
            .setTimeOut(10000)      //设置超时时间，单位是ms毫秒
            .setRetrySleepTime(3000)    //设置重置的时间间隔，单位是ms毫秒
            .setSleepTime(3)        //设置重试次数
            ;

    @Override
    public void process(Page page) {
        //没有使用选择器，通过toString()获取整个页面
        page.putField("html", page.getHtml().toString());
        // text()可以去除标签选择内容
        //当有多条数据的时候，使用 get()和 toString()都是获取第一个 url 地址，all() 返回所有抽取结果
        page.putField("title", page.getHtml().xpath("//title/text()").toString());
        page.putField("h1", page.getHtml().xpath("//h1/text()").toString());
    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
