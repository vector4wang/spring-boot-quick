package com.quick.okhttp;

import com.squareup.okhttp.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CacheResponse {
    public static void main(String[] args) throws IOException {
        int cacheSize = 100 * 1024 * 1024;
        File cacheDirectory = Files.createTempDirectory("cache").toFile();
        Cache cache = new Cache(cacheDirectory, cacheSize);
        OkHttpClient client = new OkHttpClient();
        client.setCache(cache);

        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }

        System.out.println(response.cacheResponse());
        System.out.println(response.networkResponse());
    }
}
