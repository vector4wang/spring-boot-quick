package com.multi.test.service.impl;

import com.modules.dao.CityMapper;
import com.modules.entity.City;
import com.multi.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vector
 * @Data 2018/8/10 0010
 * @Description TODO
 */
@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;

	@Override
	public City getCity(int id) {
		return cityMapper.selectByPrimaryKey(id
		);
	}
}
