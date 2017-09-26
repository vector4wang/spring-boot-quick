package com.quick.redis.service.impl;

import com.quick.redis.entity.Company;
import com.quick.redis.service.CompanyService;
import com.quick.redis.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/9/26
 * Time: 21:29
 * Description:
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Integer isCompany(String companyName) {
        Integer result = 0;
        String key = KeyUtil.COMPANY_KEY + companyName;
        String sk = KeyUtil.SCHOOL_KEY + companyName;
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            Random random = new Random();
            result = random.nextInt(1000);
            redisTemplate.opsForValue().set(key, result);
            redisTemplate.opsForValue().set(sk, result);
        } else {
            result = Integer.parseInt(o.toString());
        }
        return result;
    }
}
