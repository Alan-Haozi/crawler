package cn.edu.bistu.cs.crawler.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Crawler implements PageProcessor {
    private Site site = Site.me();

    @Override
    public void process(Page page) {
        page.putField("第一个爬虫",page.getHtml());
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new Crawler())
                .addUrl("https://www.jd.com/?cu=true&utm_source=ntp.msn.cn&utm_medium=jingfen&utm_campaign=t_2030767747_&utm_term=3992a8990286411b857b7e1caf6fcb6f")
                .run();
    }
}