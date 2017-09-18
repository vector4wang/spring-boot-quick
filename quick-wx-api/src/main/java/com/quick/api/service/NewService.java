package com.quick.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quick.api.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Service
public class NewService {

    private final static String host = "http://toutiao-ali.juheapi.com";
    private final static String path = "/toutiao/index";
    private final static String method = "GET";
    private final static String appcode = "8852ff1c52e84e7595d1425bc99813c2";

//    public static void main(String[] args) {
//        try {
//            String content = getContent("http://mini.eastday.com/mobile/170711205356140.html");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }



    public static JSONArray getNews() {
        JSONArray jsonArray = new JSONArray();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("type", "type");


        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//            System.out.println(response.toString());
            //获取response的body
            String result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSON.parseObject(result);
            jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static String getContent(String url) throws IOException {
        Document parse = Jsoup.parse(new URL(url), 5000);
        Elements select = parse.select("div#content");
        String content = select.get(0).html();
        return content;
    }

    public static void main(String[] args) throws IOException {
        Document parse = Jsoup.parse(new URL("http://mini.eastday.com/mobile/170725205647104.html"), 5000);
        Elements select = parse.select("div#content");
        String content = select.get(0).html();

        System.out.println(content);
    }
}
