package com.quick.flowable.common;

import java.io.Serializable;

public class ResponseData<T> implements Serializable {

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private T data;

    private ResponseData() {
    }

    private ResponseData(T data) {
        this.data = data;
    }

    private ResponseData(String message) {
        this.message = message;
    }

    private ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResponseData success() {
        return new ResponseData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    public static <E> ResponseData<E> success(E object) {
        return new ResponseData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), object);
    }

    public static <E> ResponseData<E> success(Integer code, String message, E object) {
        return new ResponseData(code, message, object);
    }

    public static ResponseData error() {
        return new ResponseData(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), null);
    }

    public static ResponseData error(ResponseCode code) {
        return new ResponseData(code.getCode(), code.getMessage(), null);
    }

    public static ResponseData error(String message) {
        return new ResponseData(ResponseCode.FAILED.getCode(), message, null);
    }

    public static ResponseData error(Integer code, String message) {
        return new ResponseData(code, message, null);
    }

    public static <E> ResponseData<E> error(Integer code, String message, E object) {
        return new ResponseData(code, message, object);
    }
}
