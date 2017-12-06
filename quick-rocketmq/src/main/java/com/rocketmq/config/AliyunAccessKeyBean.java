package com.rocketmq.config;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/5
 * Time: 19:07
 * Description: 阿里云相关配置
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="aliyun.mq")
public class AliyunAccessKeyBean {

    private String accessKeyID;
    private String accessKeySecret;

    public String getAccessKeyID() {
        return accessKeyID;
    }

    public void setAccessKeyID(String accessKeyID) {
        this.accessKeyID = accessKeyID;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    @Override
    public String toString() {
        return "AliyunAccessKeyBean{" +
                "accessKeyID='" + accessKeyID + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                '}';
    }
}
