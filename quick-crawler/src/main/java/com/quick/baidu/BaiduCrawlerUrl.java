package com.quick.baidu;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/4 0004.
 */
public class BaiduCrawlerUrl {

    final static int pageSize = 10;
    static String search = "";
    final static WebClient client = new WebClient(BrowserVersion.CHROME);
    final static Pattern r = Pattern.compile("in/[\\u4e00-\\u9fa5-0-9-a-z-A-z%]*");

    public static void crawler() throws IOException {
        String datakeys = "wang,";
        List<String> strings = Arrays.asList(datakeys.split(","));
        for (String item : strings) {
            search = item;
            try {
                searchResult(item, item + " site:cn.linkedin.com", 1);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static void searchResult(String search, String keyword, int page) throws IOException {
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setThrowExceptionOnScriptError(false);

        while (true) {
            // 限制，如果数据采集错误，或者无限的时候，就限制100页
            if (page == 100) {
                break;
            }
            String url = "http://www.baidu.com/s?pn=" + (page - 1) * pageSize + "&wd=" + keyword;
            HtmlPage result = client.getPage(url);
            String contentAsString = result.getWebResponse().getContentAsString();
            try {
                parseContent(contentAsString);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            page++;
        }
    }

    public static void parseContent(String content) throws Exception {
        LinkedinBaiDuSearch linkedinBaiDuSearch = new LinkedinBaiDuSearch();
        Document parse = Jsoup.parse(content);
        Elements select = parse.select("div[tpl]");
        for (Element ele : select) {
            try {
                String text = ele.select("div.c-abstract").get(0).text();
                String summary = "";
                if (text.contains("(")) {
                    int start = text.indexOf("(");
                    int end = text.lastIndexOf(")");
                    if (end > 0) {
                        summary = text.substring(start + 1, end);
                    } else {
                        summary = text.substring(start + 1, text.length());
                    }

                }

                linkedinBaiDuSearch.setSummary(summary);
                String tools = ele.select("div.c-tools").get(0).attr("data-tools");
                String title = JsonPath.parse(tools).read("$.title").toString();
                linkedinBaiDuSearch.setName(title.substring(0, title.lastIndexOf("|")));
                String tmpUrl = JsonPath.parse(tools).read("$.url").toString();
                linkedinBaiDuSearch.setUrl(tmpUrl);
                linkedinBaiDuSearch.setCrawled(LinkedinBaiDuSearch.NO);
                linkedinBaiDuSearch.setSearch(search);

                // 此做法是通过强制访问地址，在异常信息中抽取对应种子链接
                try {
                    client.getPage(tmpUrl);
                } catch (FailingHttpStatusCodeException e) {
                    e.printStackTrace();
                    Matcher m = r.matcher(e.getMessage());
                    String seed = "";
                    if (m.find()) {
                        seed = m.group();
                        seed = seed.substring(3, seed.length());
                    }
                    linkedinBaiDuSearch.setUrl(seed);
                    System.out.println(linkedinBaiDuSearch);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }

        }
        Elements select1 = parse.select("div#page a");
        Element last = select1.last();

        // 如果没有下一页，就结束爬取
        if (StringUtils.isEmpty(last.className())) {
            throw new Exception("no reslt");
        }
    }

//    public static void main(String[] args) throws IOException {
//        crawler();
//    }
}
