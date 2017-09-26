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
    Company getCompanyByName(String companyName);

    /**
     * 新增公司信息
     *
     */
    void saveCompany(Company city);

    /**
     * 更新公司信息
     *
     */
    void updateCompany(String companyName, Integer label);

}
