package com.quick.druid.service.impl;

import com.quick.druid.entity.City;
import com.quick.druid.mapper.CityMapper;
import com.quick.druid.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public City getCityById(int id) {
        return cityMapper.selectByPrimaryKey(id);
    }
}
