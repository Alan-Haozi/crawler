package cn.edu.bistu.cs.crawler.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class Crawler implements PageProcessor {
    private final Site site = Site.me()
            .setCharset("utf8")     //设置编码
            .setTimeOut(10000)      //设置超时时间，单位是ms毫秒
            .setRetrySleepTime(3000)    //设置重置的时间间隔，单位是ms毫秒
            .setSleepTime(3)        //设置重试次数
            ;

    @Override
    public void process(Page page) {
        //没有使用选择器，通过toString()获取到一个元素
        page.putField("html", page.getHtml().toString());
    }

    @Override
    public Site getSite() {
        return this.site;
    }
}