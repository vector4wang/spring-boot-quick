package com.quick.druid.service.impl;

import com.quick.druid.entity.City;
import com.quick.druid.entity.Country;
import com.quick.druid.mapper.CityMapper;
import com.quick.druid.mapper.CountryMapper;
import com.quick.druid.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public City getCityById(int id) {
        return cityMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class) // 需要回滚的地方加入注解
    @Override
    public void saveTransaction() {
        City city = new City();
        city.setCountrycode("HRV");
        city.setDistrict("20");
        city.setName("this is test");
        cityMapper.insertSelective(city);

        Country country = new Country();
        country.setName("this is test");
        country.setCode(UUID.fromString("vector").toString());

        countryMapper.insertSelective(country);
    }
}
