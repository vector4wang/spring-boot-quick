package com.quick.service.base;

import com.quick.dto.BaseRequestBody;
import com.quick.dto.BaseRequestParam;
import com.quick.dto.BaseResponseBody;
import com.quick.enums.ApiInvokerType;

public interface ApiInvoker {

    /**
     * 需指定调用者类型，区分不同厂商或者同一厂商不同的api
     *
     * @return
     */
    ApiInvokerType getInvokerType();


    /**
     * 构建请求体
     * @return
     * @param <T>
     */
    <T extends BaseRequestBody,E extends BaseRequestParam> T buildRequestBody(E e);


    /**
     * 获取响应体，基本上直接获取目标内容
     * @return
     * @param <T>
     */
    <T extends BaseResponseBody> T buildResponseBody(String response);

    /**
     * 获取当前的调用url
     * @return
     */
    String getApiUrl();




}
