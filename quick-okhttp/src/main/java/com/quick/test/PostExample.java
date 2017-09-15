package com.quick.test;

import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostExample {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String get(String url, HashMap<String, String> paramsMap) throws IOException {
        StringBuilder tempParams = new StringBuilder();

        OkHttpClient client = new OkHttpClient();
        //处理参数
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            //对参数进行URLEncoder
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            pos++;
        }
        //补全请求地址
        String requestUrl = String.format("%s?%s", url, tempParams.toString());
        //创建一个请求
        Request request = new Request.Builder()
                .url(requestUrl).build();
        //创建一个Call
        Call call = client.newCall(request);
        //执行请求
        Response response = call.execute();
        return response.body().string();
    }

    public static void main(String[] args) throws IOException {

//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        String path = "D:\\school.txt";
//        List<String> list = IOUtils.readLines(new FileInputStream(new File(path)));
//        for (String item : list) {
//            executorService.execute(new MyThread2(item));
//        }

        ybSchool();
    }

    private static void ybSchool() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        String path = "D:\\ybschool.txt";
        List<String> list = IOUtils.readLines(new FileInputStream(new File(path)));
        for (String item : list) {
            JSONObject dataObj = JSONObject.parseObject(item);
            executorService.execute(new MyThread3(dataObj.getString("talentId"),dataObj.getString("school")));
//            System.out.println(dataObj.get("talentId") + "--->" + dataObj.get("school"));
        }
    }

}

class MyThread extends Thread {

    private String value;

    public MyThread(String value) {
        this.value = value;
    }

    @Override
    public void run() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        json.put("label", 0);
        PostExample example = new PostExample();
        String response = null;
        try {
            response = example.post("http://106.15.45.31:8002/value_check", json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println();
        String result = StringUtils.removePattern(response, "\n") + "--->" + value;
        try {
            File file = new File("D:\\companyResult.txt");
            OutputStream os = new FileOutputStream(file, true);
            List<String> lines = new ArrayList<>();
            lines.add(result);
            System.out.println(result);
            IOUtils.writeLines(lines, null, os, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class MyThread2 extends Thread {

    private String value;

    public MyThread2(String value) {
        this.value = value;
    }

    @Override
    public void run() {
        PostExample example = new PostExample();
        String response = null;
        String name = "";
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("text", value);
            response = example.get("http://school.bazhua.me:8841/school/normalize", map);
            JSONObject json = JSONObject.parseObject(response);
            if(json.containsKey("data")){
                String data = json.getString("data");
                if (!StringUtils.isEmpty(data)) {
                    JSONObject dataObj = JSONObject.parseObject(json.getString("data"));
                    name = dataObj.getString("name");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println();
        String result = value + "--->" + name;
        try {
            File file = new File("D:\\SchoolResult.txt");
            OutputStream os = new FileOutputStream(file, true);
            List<String> lines = new ArrayList<>();
            lines.add(result);
            System.out.println(result);
            IOUtils.writeLines(lines, null, os, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class MyThread3 extends Thread {

    private String talentId;
    private String value;


    public MyThread3(String talentId,String value) {

        this.talentId = talentId;
        this.value = value;
    }

    @Override
    public void run() {
        PostExample example = new PostExample();
        String response = null;
        String name = "";
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("text", value);
            response = example.get("http://school.bazhua.me:8841/school/normalize", map);
            JSONObject json = JSONObject.parseObject(response);
            if(json.containsKey("data")){
                String data = json.getString("data");
                if (!StringUtils.isEmpty(data)) {
                    JSONObject dataObj = JSONObject.parseObject(json.getString("data"));
                    name = dataObj.getString("name");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println();
        String result = talentId + "--->" + value + "--->" + name;
        try {
            File file = new File("D:\\SchoolResult.txt");
            OutputStream os = new FileOutputStream(file, true);
            List<String> lines = new ArrayList<>();
            lines.add(result);
            System.out.println(result);
            IOUtils.writeLines(lines, null, os, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}