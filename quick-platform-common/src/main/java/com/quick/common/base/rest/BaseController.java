package com.quick.common.base.rest;

/**
 * 后续可在此提供公共方法
 */
public interface BaseController {

    default BaseResp<Boolean> success() {
        return BaseResp.success();
    }

    default <T> BaseResp<T> success(T data) {
        return success(data,"");
    }

    default <T> BaseResp<T> success(T data, String msg) {
        return BaseResp.success(data, msg);
    }

    default <T> BaseResp<String> fail(String msg) {
        return BaseResp.fail(msg);
    }

    default <T> BaseResp<String> fail(ResultStatus code, String msg) {
        return BaseResp.fail(code, msg);
    }

}
