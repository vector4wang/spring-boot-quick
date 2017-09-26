package com.quick.redis.service;

import com.quick.redis.entity.Company;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/9/26
 * Time: 21:27
 * Description:
 */
public interface CompanyService {
    /**
     * 获取公司值
     *
     */
    Integer isCompany(String companyName);
}
