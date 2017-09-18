package com.quick.csdn2md;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Service
public class CSDN2mdService {

//    public final static String username = "qqhjqs";
    public final static String HOST_URL = "http://blog.csdn.net/";
    public final static String TOP_XPATH = "#article_toplist .list_item,.article_item";
    public final static String NOMAIL_QUERY = "#article_list .list_item,.article_item";
    public final static String TARGET_DIR = "D:\\data\\csdn_blogs";

    public static void main(String[] args) throws IOException, XpathSyntaxErrorException {
//        String convert = convert(new URL("http://blog.csdn.net/qqhjqs/article/details/66474364"));
//        System.out.println(convert);
    }

    public String convert(URL url) throws IOException {
        Document doc = Jsoup.parse(url,5000);
        doc.getElementsByTag("script").remove();
        String content = doc.select("#article_content").toString();
        String result = HTML2Md.convertHtml4csdn(content, "utf-8");
        return result;
    }

    public String convert(String html){
        Document doc = Jsoup.parse(html,"utf-8");
        doc.getElementsByTag("script").remove();
        Elements select = doc.select("#article_content");
        if(select.isEmpty()){
            String convert = HTML2Md.convert(html, "utf-8");
            return convert;
        }else{
            String content = select.toString();
            String result = HTML2Md.convertHtml4csdn(content, "utf-8");
            return result;
        }

    }

    private static void convertAllBlogByUserName(String username) throws IOException {
        String mdString = "";
        String url = HOST_URL + username + "/article/list/" + 1;
        Document parse = Jsoup.parse(new URL(url), 5000);
        Element element = parse.select("div#papelist span").get(0);
        String papelistInfo = element.text();
        String totalPage = papelistInfo.split("共")[1].split("页")[0]; // 有点暴力，需注意
        int total = Integer.valueOf(totalPage);
        System.out.println(total);
        Map<String,String> map = new HashMap<>();
        for (int i = 1; i <= total; i++) {
            String listUrl = HOST_URL + username + "/article/list/" + i;
            Document doc = Jsoup.parse(new URL(listUrl), 5000);
            Elements topSelect = doc.select(TOP_XPATH);
            Elements normalSelect = doc.select(NOMAIL_QUERY);
            for (Element item : topSelect) {
                map.put(item.select(".article_title  h1  span  a").get(0).attr("href"),item.select(".article_description").get(0).text());
            }
            for (Element item : normalSelect) {
                map.put(item.select(".article_title  h1  span  a").get(0).attr("href"),item.select(".article_description").get(0).text());
            }
        }
        Set<String> strings = map.keySet();
        for(String item:strings){
            Document doc = Jsoup.parse(new URL(HOST_URL + item), 5000);
            BlogModel bm = new BlogModel();
            bm.setTitle(doc.select("#article_details > div.article_title > h1 > span > a").text());
            bm.setDesc(map.get(item));
            bm.setPublishDate(doc.select("#article_details > div.article_manage.clearfix > div.article_r > span.link_postdate").text());
            Elements select = doc.select("#article_details > div.category.clearfix > div.category_r");
            List<String> cat = new ArrayList<>();
            for(Element ele : select){
                String text = ele.select("label span").get(0).text();
                int i = text.lastIndexOf("（");
                if(i>=0){
                    String substring = text.substring(0, i);
                    cat.add(substring);
                }else{
                    cat.add(text);
                }
            }
            bm.setCategories(cat);
            Elements tagEles = doc.select("#article_details > div.article_manage.clearfix > div.article_l > span > a");
            List<String> tags = new ArrayList<>();
            for(Element ele : tagEles){
                String text = ele.text();
                tags.add(text);
            }
            bm.setTags(tags);
            doc.getElementsByTag("script").remove();
            bm.setContent(doc.select("#article_content").toString());
            try{
                buildHexo(bm);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private static void buildHexo(BlogModel bm) throws IOException {
        System.out.println("保存博文--->[" + bm.getTitle() + "]");
        StringBuffer sb = new StringBuffer();
        sb.append("---");
        sb.append("\r\n");
        sb.append("title: " + bm.getTitle());
        sb.append("\r\n");
        sb.append("date: " + bm.getPublishDate() + ":00");
        sb.append("\r\n");
        sb.append("categories:");
        sb.append("\r\n");
        for(String cat:bm.getCategories()){
            sb.append("- " + cat);
            sb.append("\r\n");
        }
        sb.append("tags:");
        sb.append("\r\n");
        for(String tag:bm.getTags()){
            sb.append("- " + tag);
            sb.append("\r\n");
        }
        sb.append("\r\n");
        sb.append("---");
        sb.append("\r\n");
        sb.append(bm.getDesc()==null?"":bm.getDesc());
        sb.append("\r\n");
        sb.append("<!--more-->");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append(HTML2Md.convertHtml4csdn(bm.getContent(),"UTF-8"));
        IOUtils.write(sb.toString(),new FileOutputStream(new File(TARGET_DIR + File.separator + bm.getTitle() + ".md")));
    }

}
