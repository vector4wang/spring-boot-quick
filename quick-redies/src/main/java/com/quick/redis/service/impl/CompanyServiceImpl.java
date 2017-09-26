package com.quick.redis.service.impl;

import com.quick.redis.entity.Company;
import com.quick.redis.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/9/26
 * Time: 21:29
 * Description:
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private Map<String, Company> companyMap = new HashMap<>();

    @Override
    public Company getCompanyByName(String companyName) {
        return null;
    }

    @Override
    public void saveCompany(Company city) {

    }

    @Override
    public void updateCompany(String companyName, Integer label) {

    }
}
