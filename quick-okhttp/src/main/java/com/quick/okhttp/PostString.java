package com.quick.okhttp;

import com.squareup.okhttp.*;

import java.io.IOException;

public class PostString {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/json");
        String postBody = "{\"username\":\"3\",\"password\":\"string\",\"email\":\"string\"}";

        Request request = new Request.Builder()
                .url("http://localhost:54321/users/signup")
//				.header("Content-Type","application/json")
                .post(RequestBody.create(MEDIA_TYPE_TEXT, postBody))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }

        System.out.println(response.body().string());
    }
}
