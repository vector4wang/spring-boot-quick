package com.modules.dao;

import com.modules.entity.Country;

public interface CountryMapper {
    int deleteByPrimaryKey(String code);

    int insert(Country record);

    int insertSelective(Country record);

    Country selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(Country record);

    int updateByPrimaryKey(Country record);
}