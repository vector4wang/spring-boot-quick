package com.modules.api;

import com.modules.entity.Country;
import com.modules.service.CountryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/1
 * Time: 8:36
 */
@RestController
@RequestMapping("/service2")
public class APIController {

	@Resource
	private CountryService countryService;

	@RequestMapping("/hello/{code}")
	public Country hello(@PathVariable("code") String code) {
		return countryService.selectByCode(code);
	}

}
