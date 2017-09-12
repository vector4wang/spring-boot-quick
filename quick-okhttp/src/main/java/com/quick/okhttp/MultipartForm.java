package com.quick.okhttp;

import com.squareup.okhttp.*;

import java.io.File;
import java.io.IOException;

public class MultipartForm {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"title\""),
                        RequestBody.create(null, "测试文档"))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"file\""),
                        RequestBody.create(MEDIA_TYPE_TEXT, new File("input.txt")))
                .build();

        Request request = new Request.Builder()
                .url("http://www.baidu.com/")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }

        System.out.println(response.body().string());
    }
}
