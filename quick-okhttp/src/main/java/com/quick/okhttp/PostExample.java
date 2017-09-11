package com.quick.okhttp;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
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
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        String path = "D:\\company.txt";
        List<String> list = IOUtils.readLines(new FileInputStream(new File(path)));
        for (String item : list) {
            executorService.execute(new MyThread(item));
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
            response = example.post("http://106.15.45.31:8001/value_check", json.toString());
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
            IOUtils.writeLines(lines,null,os,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}