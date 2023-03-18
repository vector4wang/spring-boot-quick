package com.quick.feign;

import com.alibaba.fastjson.JSON;
import com.quick.feign.entity.BaseResp;

/**
 * @author 01390942
 * @Description
 * @create 2022/1/24
 * @since 1.0.0
 */
public class TestMain {
    public static void main(String[] args) {
        BaseResp baseResp = JSON.parseObject(
                "{\"data\":{\"id\":\"11000002000016\",\"sitename\":\"新浪网\",\"sitedomain\":\"sina.com.cn\",\"sitetype\":\"交互式\",\"cdate\":\"2016-01-21\",\"comtype\":\"企业单位\",\"comname\":\"北京新浪互联信息服务有限公司\",\"comaddress\":\"北京市网安总队\",\"updateTime\":\"2017-09-09\"},\"status\":200}",
                BaseResp.class);
        System.out.println(baseResp);
    }
}