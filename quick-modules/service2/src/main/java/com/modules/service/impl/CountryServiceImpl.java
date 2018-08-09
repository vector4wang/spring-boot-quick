package com.modules.service.impl;

import com.modules.dao.CountryMapper;
import com.modules.entity.Country;
import com.modules.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vector
 * @Data 2018/8/9 0009
 * @Description TODO
 */
@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryMapper countryMapper;

	@Override
	public Country selectByCode(String code) {
		return countryMapper.selectByPrimaryKey(code);
	}
}
