package com.quick.starter.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author vector
 * @date: 2019/8/6 0006 17:00
 */
@ConfigurationProperties(prefix = "quick")
public class QuickProperties {

    private String name = "quick starter demo!!!";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
