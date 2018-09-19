package com.quick.feign.service;

import com.quick.feign.entity.BaseResp;
import com.quick.feign.entity.DomainDetail;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface FeignService {

	@RequestLine("GET /api/gongan/{domain}")
	@Headers({
			"user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36",
			"content-type: application/json;charset=UTF-8"})
	BaseResp<DomainDetail> getDomain(@Param("domain") String domain);




}
