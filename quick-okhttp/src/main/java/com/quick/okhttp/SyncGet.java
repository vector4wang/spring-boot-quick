package com.quick.okhttp;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SyncGet {
	public static void main(String[] args) throws IOException {
		OkHttpClient client = new OkHttpClient();
		client.interceptors().add(new LoggingInterceptor());
		try {
			Request request = new Request.Builder()
					.url("http://localhost:54321/app/test")
					.header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwN2U5MTAyYzc5NTVlMTc3OTQ2NDcwZjcyNjY5MGZhNyIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQ0xJRU5UIn1dLCJpYXQiOjE1NTIxMjgzNjIsImV4cCI6MTU1MjEzMTk2Mn0.H4_KqWGgGc_YUSOezJ6c4fEmkiYp5NmXsQLbpWlgCv4")
					.header("whoami","07e9102c7955e177946470f726690fa7")
					.build();
			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				System.out.println("服务器端错误: " + response);
			}
			System.out.println(response.body().string());
			System.out.println("================================================");
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}
