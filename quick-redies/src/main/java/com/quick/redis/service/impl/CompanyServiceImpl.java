package com.quick.redis.service.impl;

import com.quick.redis.service.CompanyService;
import com.quick.redis.util.KeyUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

    private Logger logger = LogManager.getLogger(CompanyServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Integer isCompany(String companyName) {
        try {
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
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            return 1;
        }

    }

    @Override
    public Integer add() {

        for(int i=0;i<1000;i++) {
            String key = "Test:" + i;
            redisTemplate.opsForValue().set(key, i);
        }

        return 1000;
    }
}
