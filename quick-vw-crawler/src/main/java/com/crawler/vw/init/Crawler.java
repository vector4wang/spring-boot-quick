package com.crawler.vw.init;

import com.crawler.vw.cache.DataCache;
import com.crawler.vw.entity.Blog;
import com.crawler.vw.utils.Md5Util;
import com.github.vector4wang.VWCrawler;
import com.github.vector4wang.service.CrawlerService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order
public class Crawler implements CommandLineRunner {

	@Autowired
	private DataCache dataCache;

	@Override
	public void run(String... strings) {
		new VWCrawler.Builder().setUrl("https://blog.csdn.net/qqHJQS").setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36")
				.setTargetUrlRex("https://blog.csdn.net/qqHJQS/article/details/[0-9]+","https://blog.csdn.net/qqhjqs/article/details/[0-9]+").setThreadCount(5)
				.setThreadCount(5)
				.setTimeOut(5000).setPageParser(new CrawlerService<Blog>() {

			@Override
			public void parsePage(Document doc, Blog pageObj) {
				pageObj.setReadCount(Integer.parseInt(pageObj.getReadCountStr().replace("阅读数：", "")));
				pageObj.setUrl(doc.baseUri());
				pageObj.setUrlMd5(Md5Util.getMD5(pageObj.getUrl().getBytes()));

				/**
				 * todo 评论列表还未处理
				 */
			}

			@Override
			public void save(Blog pageObj) {
				dataCache.save(pageObj);
			}
		}).build().start();
	}

}
