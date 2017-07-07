package com.quick.log.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Component
public class LoggerService {

    private Logger logger = LogManager.getLogger(LoggerService.class);

//    @PostConstruct
    public void showLog() {
//        for (int i = 0; i < 10; i++) {
            logger.debug("我是DEBUG日志");
            logger.info("我是INFO日志");
            logger.warn("我是WARN日志");
            logger.error("我是ERROR日志");
            Marker marker = MarkerManager.getMarker("test");
            logger.error(marker,"我是ERROR日志");

            logger.fatal("我是FATAL日志");
//        }
    }

}
