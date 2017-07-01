package com.quick.mulit.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static List<String> dataSourceIds = new ArrayList<>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * @Author: wangxc
     * @Description: 判断指定DataSrouce当前是否存在
     * @param dataSourceId
     * @return: boolean
     * @Date: 下午 10:33 2017/6/21 0021
     */
    public static boolean containsDataSource(String dataSourceId){

        return dataSourceIds.contains(dataSourceId);
    }

}
