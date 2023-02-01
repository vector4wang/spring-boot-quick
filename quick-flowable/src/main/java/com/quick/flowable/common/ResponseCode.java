package com.quick.flowable.common;

public enum ResponseCode {

    /* 公共状态码 */
    // 成功
    SUCCESS(200, "成功"),
    //失败
    FAILED(400, "失败"),
    //未认证（签名错误）
    UNAUTHORIZED(401, "签名错误"),
    //接口不存在
    NOT_FOUND(404, "此接口不存在"),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500, "系统繁忙,请稍后再试"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    LOGIN_CREDENTIAL_EXISTED(20006, "凭证已存在");

    /* 业务错误：50001-59999 */


    private Integer code;

    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}