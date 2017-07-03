package com.quick.mulit.mapper;

import com.quick.mulit.entity.City;

import java.util.List;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */

public interface CityMapper {
    // 注解 @TargetDataSource 不可以在这里使用
    List<City> likeName(String name);

    City getById(int id);

    String getNameById(int id);

}
