package com.quick.log.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.stereotype.Component;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Component
@Slf4j
public class LoggerService {


//    @PostConstruct
    public void showLog() {
//        for (int i = 0; i < 10; i++) {
            log.debug("我是DEBUG日志");
            log.info("我是INFO日志");
            log.warn("我是WARN日志");
            log.error("我是ERROR日志");
//        }
    }

}
