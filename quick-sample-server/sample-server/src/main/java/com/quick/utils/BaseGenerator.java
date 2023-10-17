package com.quick.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import java.io.IOException;

public class BaseGenerator {
    /**
     * 数据库配置
     *
     * @return 数据源
     */
    public static DataSourceConfig.Builder dataSourceGenerate() throws IOException {

        Props properties = new Props("application-jdbc.properties", CharsetUtil.CHARSET_UTF_8);
        //获取properties里的参数
        String url = properties.getStr("spring.datasource.url");
        String driver = properties.getStr("spring.datasource.driver-class-name");
        String username = properties.getStr("spring.datasource.username");
        String password = properties.getStr("spring.datasource.password");
        // 数据库配置
        return new DataSourceConfig.Builder(url, username, password);
    }
}
