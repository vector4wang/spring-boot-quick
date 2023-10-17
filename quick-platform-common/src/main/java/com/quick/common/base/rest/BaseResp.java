package com.quick.common.base.rest;

import java.util.Date;

/**
 * @param <T>
 */
public class BaseResp<T> {
    /**
     * 返回码
     */
    private ResultStatus code;

    /**
     * 返回信息描述
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private long currentTime;


    public ResultStatus getCode() {
        return code;
    }

    public void setCode(ResultStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public BaseResp() {
    }

    /**
     * @param code    错误码
     * @param message 信息
     * @param data    数据
     */
    public BaseResp(ResultStatus code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.currentTime = new Date().getTime();
    }

    /**
     * 不带数据的返回结果
     *
     * @param resultStatus
     */
    public BaseResp(ResultStatus resultStatus) {
        this.code = resultStatus;
        this.message = resultStatus.getErrorMsg();
        this.currentTime = new Date().getTime();
    }

    /**
     * 带数据的返回结果
     *
     * @param resultStatus
     * @param data
     */
    public BaseResp(ResultStatus resultStatus, T data) {
        this.code = resultStatus;
        this.message = resultStatus.getErrorMsg();
        this.data = data;
        this.currentTime = new Date().getTime();
    }

    public static BaseResp<Boolean> success() {
        BaseResp<Boolean> response = new BaseResp<Boolean>();
        response.setCode(ResultStatus.SUCCESS);
        return response;
    }

    public static <T> BaseResp<T> success(T data, String msg) {
        BaseResp<T> response = new BaseResp<T>();
        response.setCode(ResultStatus.SUCCESS);
        response.setData(data);
        response.setMessage(msg);
        return response;
    }

    public static BaseResp<String> fail(String msg) {
        BaseResp<String> response = new BaseResp<String>();
        response.setCode(ResultStatus.FAIL);
        response.setMessage(msg);
        return response;
    }

    public static BaseResp<String> fail(ResultStatus code, String msg) {
        BaseResp<String> response = new BaseResp<String>();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }


}

