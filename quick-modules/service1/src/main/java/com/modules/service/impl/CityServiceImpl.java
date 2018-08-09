package com.modules.service.impl;

import com.modules.dao.CityMapper;
import com.modules.entity.City;
import com.modules.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/1
 * Time: 8:38
 */
@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;

	@Override
	public City selectById(int id) {
		return cityMapper.selectByPrimaryKey(id);
	}
}
