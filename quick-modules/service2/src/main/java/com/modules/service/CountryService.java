package com.modules.service;

import com.modules.entity.Country;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/1
 * Time: 8:38
 */
public interface CountryService {
    Country selectByCode(String code);
}
