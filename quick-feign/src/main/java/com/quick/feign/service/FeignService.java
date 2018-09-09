package com.quick.feign.service;

import feign.RequestLine;

public interface FeignService {

	@RequestLine("GET /data/sk/101190408.html")
	String getWeather();
}
