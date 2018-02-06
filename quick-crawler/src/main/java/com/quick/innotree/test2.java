package com.quick.innotree;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.xpath.XPath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
public class test2 {
    public static void main(String[] args) throws IOException {
        final WebClient webClient = new WebClient();// 创建WebClient
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        Document parse = null;
        Page page = null;
        for(int i=100;i<1000;i++){
            try{
                page = webClient.getPage("http://www.innotree.cn/company/"+i+".html");
                parse = Jsoup.parse(page.getWebResponse().getContentAsString());
                Elements select = parse.select("body > div.details_1221_con > div.details_1221_d01 > div.details_1221_d01_01 > table > tbody > tr > td:nth-child(2) > h3");
                System.out.println(select.get(0).text());
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
