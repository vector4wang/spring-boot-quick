package com.crawler.vw;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vector
 * @Data 2018/8/15 0015
 * @Description TODO
 */
public class TestMain {
	public static void main(String[] args) throws IOException {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36");
		headers.put("Cookie",
				"yunsuo_session_verify=1a3375389647fba22b7902b9fb4ace23; UM_distinctid=1653ca373b49dd-08590abd5d5966-e323462-1fa400-1653ca373b5d45; CNZZDATA1261546263=791873602-1534320347-%7C1534320347; JSESSIONID=BBAEDA8A8DED1B76BF8B5F576B52FFDD; PD=9ebee55477eac1bcc49b735f29d4f4134928223a2c77d03ba86aebe3044aa69c698511426b80c7d7");
		headers.put("Referer",
				"https://www.patexplorer.com/results/s.html?sc=&q=%E6%AD%A6%E6%B1%89%E7%91%9E%E5%8D%9A%E7%A7%91%E5%88%9B%E7%94%9F%E7%89%A9%E6%8A%80%E6%9C%AF%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&fq=&type=s&sort=&sortField=");
		headers.put("Host", "www.patexplorer.com");
		headers.put("Origin", "https://www.patexplorer.com");
		headers.put("X-Requested-With", "XMLHttpRequest");
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Connection", "keep-alive");
		headers.put("Content-Length", "199");

		Map<String, String> data = new HashMap<>();
		data.put("sc", "");
		data.put("q", "武汉瑞博科创生物技术有限公司");
		data.put("sort", "");
		data.put("sortField", "");
		data.put("fq", "");
		data.put("pageSize", "");
		data.put("pageIndex", "");
		data.put("type", "s");
		data.put("merge", "no-merge");


		Document post = Jsoup
				.connect("https://www.patexplorer.com/results/list/YzhkYWIwYzRlZWIwZmJmZDRiMGMyZDRjOTdjZmM4MmU=")
				.headers(headers).data(data).post();
		System.out.println(post.body());
	}
}
