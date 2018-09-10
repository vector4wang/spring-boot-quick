package com.quick.feign.controller;

import com.quick.feign.entity.BaseResp;
import com.quick.feign.entity.DomainDetail;
import com.quick.feign.service.FeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author vector
 * @Data 2018/9/10 0010
 * @Description
 */
@RestController
public class Api {

	@Resource
	private FeignService feignService;

	@GetMapping("/domain/{domain}/detail")
	public Object getDomainDetail(@PathVariable("domain") String domain) {
		BaseResp<DomainDetail> domain1 = feignService.getDomain(domain);
		return domain1;
	}
}
