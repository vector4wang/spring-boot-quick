package com.quick.innotree;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
public class InnotreeService {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // 创建WebClient
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

//        webClient.getOptions().setJavaScriptEnabled(false);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setRedirectEnabled(true);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);


//        webClient.addRequestHeader("Accept","application/json,text/javascript,*/*;q=0.01");
//        webClient.addRequestHeader("Accept-Encoding","gzip,deflate,br");
//        webClient.addRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
//        webClient.addRequestHeader("Connection","keep-alive");
//        webClient.addRequestHeader("Host","www.innotree.cn");
//        webClient.addRequestHeader("Referer","https//www.innotree.cn/inno/database/totalDatabase");
//        webClient.addRequestHeader("User-Agent","Mozilla/5.0(WindowsNT10.0;Win64;x64)AppleWebKit/537.36(KHTML,likeGecko)Chrome/60.0.3112.90Safari/537.36");
//        webClient.addRequestHeader("X-Requested-With","XMLHttpRequest");
//
//        webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
//
//        webClient.getCookieManager().addCookie(new Cookie("www.innotree.cn","Cookie","_user_identify_=bcc1c471-b2d8-3a36-bfa8-fb20b06f68e2; uID=450394; sID=2a48b3d753f6d08a1a4bf456440cc1f3; JSESSIONID=aaaMaWLicGHPz6AA-OX9v; Hm_lvt_37854ae85b75cf05012d4d71db2a355a=1509366682,1509440093; Hm_lpvt_37854ae85b75cf05012d4d71db2a355a=1509440146"));
//
//        Page page = webClient.getPage("https://www.innotree.cn/inno/search/ajax/getAllSearchResult?query=&st=1&ps=10&fchain=0");
//
//        System.out.println(page.getWebResponse().getContentAsString());


        CloseableHttpClient httpclient = HttpClients.createDefault();
//        URI uri = new URIBuilder()
//                .setScheme("https")
//                .setHost("www.innotree.cn")
//                .setPath("inno/search/ajax/getAllSearchResult")
//                .setParameter("query", "")
//                .setParameter("st", "1")
//                .setParameter("ps", "10")
//                .setParameter("fchain","0")
//                .build();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.innotree.cn/inno/search/ajax/getAllSearchResult?query=&st=1&ps=10&fchain=0");
        httpGet.setHeader("Accept","application/json,text/javascript,*/*;q=0.01");
        httpGet.setHeader("Accept-Encoding","gzip,deflate,br");
        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8");
        httpGet.setHeader("Connection","keep-alive");
        httpGet.setHeader("Cookie","_user_identify_=bcc1c471-b2d8-3a36-bfa8-fb20b06f68e2;uID=450394;sID=2a48b3d753f6d08a1a4bf456440cc1f3;JSESSIONID=aaaMaWLicGHPz6AA-OX9v;Hm_lvt_37854ae85b75cf05012d4d71db2a355a=1509366682,1509440093;Hm_lpvt_37854ae85b75cf05012d4d71db2a355a=1509440135");
        httpGet.setHeader("Host","www.innotree.cn");
        httpGet.setHeader("Referer","https//www.innotree.cn/inno/database/totalDatabase");
        httpGet.setHeader("User-Agent","Mozilla/5.0(WindowsNT10.0;Win64;x64)AppleWebKit/537.36(KHTML,likeGecko)Chrome/60.0.3112.90Safari/537.36");
        httpGet.setHeader("X-Requested-With","XMLHttpRequest");

        CloseableHttpResponse execute = httpclient.execute(httpGet);
        System.out.println(IOUtils.toString(execute.getEntity().getContent()));


    }
}
