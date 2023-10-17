package com.quick.common.base.controller;

/**
 * 后续可在此提供公共方法
 */
public interface BaseController {

    default String toJSON(ArgusResponse responseBody) {
        String result = JsonUtil.serialize(responseBody);
        return result;
    }


}
