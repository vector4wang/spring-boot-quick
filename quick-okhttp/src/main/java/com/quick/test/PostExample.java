package com.quick.test;

import com.alibaba.fastjson.JSONArray;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//        ybSchool();
        company();
    }

    private static void company() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String path = "D:\\datafilter\\v0.2\\temp.txt";
        List<String> list = IOUtils.readLines(new FileInputStream(new File(path)));
        for (String item : list) {
            executorService.execute(new MyThread(replaceBlank(item)));
//            System.out.println(dataObj.get("talentId") + "--->" + dataObj.get("school"));
        }
    }

    private static void ybSchool() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        String path = "D:\\datafilter\\v0.2\\total_school.txt";
        List<String> list = IOUtils.readLines(new FileInputStream(new File(path)));
        for (String item : list) {
            executorService.execute(new MyThread3(replaceBlank(item)));
        }
    }
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
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
        double processTime = 0;

        try {
            long start = System.currentTimeMillis();
            response = example.post("http://192.168.80.10:8001/company/clean", json.toString());
            long end = System.currentTimeMillis();
            processTime  = (double)(end-start)/1000;
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
            {
              "current_time": "2017-10-16 16:14:34",
              "message": "",
              "cost_time": 0.002658843994140625,
              "data": {
                "raw_value": "娃哈哈(北京)有限公司(1000人)(杭州总部(5个月))",
                "clean_value": "娃哈哈(北京)有限公司",
                "isLabel": 1
              },
              "success": true
            }
         */
        JSONObject resJson = JSONObject.parseObject(response);
        JSONObject data = resJson.getJSONObject("data");
//        if (data.getInteger("isLabel") == 1) {
            String result =  "[" + value + "--->" + data.getInteger("isLabel") + "--->{" + data.getString("clean_value") + "} 耗时: " + processTime + "]";
            try {
                File file = new File("D:\\datafilter\\v0.2\\temp_result.txt");
                OutputStream os = new FileOutputStream(file, true);
                List<String> lines = new ArrayList<>();
                lines.add(result);
                System.out.println(result);
                IOUtils.writeLines(lines, null, os, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
//            }
        }
    }
}

class MyThread3 extends Thread {

    private String value;


    public MyThread3(String value) {

        this.value = value;
    }

    @Override
    public void run() {
        PostExample example = new PostExample();
        String response = null;
        String name = "";
        double processTime = 0;
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jo = new JSONObject();
            jo.put("school", value);
            jsonArray.add(jo);
            long start = System.currentTimeMillis();
            response = example.post("http://192.168.1.31:9992/school/batch_normalize_v2", jsonArray.toString());
            long end = System.currentTimeMillis();
            processTime  = (double)(end-start)/1000;
            JSONObject json = JSONObject.parseObject(response);
            if(json.containsKey("data")){
                JSONObject data = json.getJSONArray("data").getJSONObject(0);
                String enName = data.getString("en_name");
                String oldName = data.getString("old_name");
                String zhName = data.getString("zh_name");

                if (StringUtils.isEmpty(enName) && StringUtils.isEmpty(zhName)) {
                    name = oldName;
                }else {
                    name = StringUtils.isEmpty(zhName) ? enName : zhName;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = "[" + value + "--->{" + name + "} 耗时: " + processTime + "]";
        try {
            File file = new File("D:\\datafilter\\v0.2\\total_school_result.txt");
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