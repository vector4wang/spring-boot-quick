package com.quick.innotree;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
public class InnotreeService {
    public static void main(String[] args) throws IOException {
        final WebClient webClient = new WebClient();// 创建WebClient

        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().set
            webClient.getOptions().setTimeout(5000);
//
//        // 获取页面
//        HtmlPage page = webClient.getPage("http://www.innotree.cn/indexprocoo/search.html?keyword=%E5%85%AB%E7%88%AA");
//        webClient.waitForBackgroundJavaScript(10000);
//        // 获得name为"session_key"的html元素
////        HtmlElement usernameEle = page.getElementByName("session_key");
////        // 获得id为"session_password"的html元素
////        HtmlElement passwordEle = (HtmlElement) page.getElementById("session_password-login");
////        usernameEle.focus(); // 设置输入焦点
////        usernameEle.type(name);
////        passwordEle.focus(); // 设置输入焦点
////
////        passwordEle.type(password);
//        System.out.println(page.getWebResponse().getContentAsString());

        String keyEncode = URLEncoder.encode("八爪","UTF-8");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");



        WebRequest webRequest = new WebRequest(new URL("http://www.innotree.cn/collectlog/info.ajax"));
        webRequest.setHttpMethod(HttpMethod.POST);
        JSONObject params = new JSONObject();
        params.put("source_type","pc");
        params.put("collect_type","click");
        params.put("collect_key","search");
        params.put("collect_type","八爪");
        params.put("collect_id","");
        params.put("u_request_url","http://www.innotree.cn/indexprocoo/search.html?keyword="+ keyEncode);
        params.put("u_referer_url","http://www.innotree.cn/indexprocoo/search.html?keyword="+ keyEncode);
        params.put("u_agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
        params.put("gen_time",format.format(new Date()));
        params.put("collect_page","SearchList");

        JSONObject nj = new JSONObject();
        nj.put("json",params);

        webRequest.setAdditionalHeader("Accept","application/json, text/javascript, */*; q=0.01");
        webRequest.setAdditionalHeader("Accept-Encoding","gzip, deflate");
        webRequest.setAdditionalHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        webRequest.setAdditionalHeader("Connection","keep-alive");
        webRequest.setAdditionalHeader("Host","www.innotree.cn");
        webRequest.setAdditionalHeader("Cookie","_user_identify_=38e78465-ca98-39a1-a411-d22034ad31af; JSESSIONID=aaaJR4Oz2351xh_LAPKZv; Hm_lvt_37854ae85b75cf05012d4d71db2a355a=1498484735,1498485012; Hm_lpvt_37854ae85b75cf05012d4d71db2a355a=1498486706");
        webRequest.setAdditionalHeader("Origin","http://www.innotree.cn");
        webRequest.setAdditionalHeader("Referer","http://www.innotree.cn/indexprocoo/search.html?keyword="+keyEncode);
        webRequest.setAdditionalHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
        webRequest.setAdditionalHeader("X-Requested-With","XMLHttpRequest");
//        webRequest.setAdditionalHeader("Content-Length",""+params.toString().length());
        System.out.println(nj.toString());
//        webRequest.setRequestBody("REQUESTBODY");
        webRequest.setRequestParameters(new ArrayList());
        webRequest.getRequestParameters().add(new NameValuePair("json", params.toString()));
        Page page = webClient.getPage(webRequest);
//        webRequest.getre
        page = webClient.getPage("http://www.innotree.cn/indexprocoo/search.html?keyword=" + keyEncode);
        System.out.println(page.getWebResponse().getContentAsString());
    }
}
