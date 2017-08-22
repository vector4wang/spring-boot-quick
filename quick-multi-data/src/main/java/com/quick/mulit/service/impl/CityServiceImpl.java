package com.quick.mulit.service.impl;

import com.quick.mulit.entity.primary.City;
import com.quick.mulit.mapper.primary.CityMapper;
import com.quick.mulit.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/8/22
 * Time: 19:14
 * Description:
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public City getCity(short i) {
        return cityMapper.selectByPrimaryKey(i);
    }
}
