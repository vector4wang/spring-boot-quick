package com.quick.linkedin.order;


import com.quick.linkedin.service.CrawlLinkedService;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by bd2 on 2017/3/13.
 */
@Component
@Order(value = 1)
public class InitLinkedInCrawlOrder implements CommandLineRunner {

    private Logger logger = Logger.getLogger(InitLinkedInCrawlOrder.class);

    @Resource
    private CrawlLinkedService service;

    @Override
    public void run(String... strings) throws Exception {
        if(strings.length!=3){
            logger.error("请以此填写用户名、密码和爬取切入url");
        }

        System.out.println("用户名、密码和爬取切入url分别为: " + StringUtils.arrayToDelimitedString(strings,","));

        service.crawlLinkedIn(strings[0],strings[1],strings[2]);
    }
}
