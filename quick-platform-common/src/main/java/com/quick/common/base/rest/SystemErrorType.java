package com.quick.common.base.rest;


public enum SystemErrorType {

    SYSTEM_ERROR("-1", "系统错误，请联系管理员"),
    SYSTEM_BUSY("000001", "请求频繁,请稍候再试"),

    GATEWAY_NOT_FOUND_SERVICE("010404", "服务未找到"),
    GATEWAY_ERROR("010500", "网关异常"),
    GATEWAY_CONNECT_TIME_OUT("010002", "网关超时"),
    GATEWAY_NOT_LOGIN("010003", "未登录"),
    GATEWAY_NO_PERMISSION("010004", "无权限"),
    USER_NOT_FOUND("010005", "用户不存在"),
    PAASSWORD_ERROR("010006", "密码错误"),

    ARGUMENT_NOT_VALID("020000", "请求参数校验不通过"),
    INVALID_TOKEN("020001", "无效token"),
    /** 令牌无效 */
    TOKEN_INVALID("020002", "token failure!"),
    /** 缺少相应参数 */
    MISSING_PARAMETER("020003", "参数绑定失败:缺少参数"),
    /** 刷新令牌无效 */
    REFRESH_TOKEN_INVALID("020005", "认证过期,请重新登录"),
    /** 验证码无效 */
    CODE_EXPIRE("020006", "secretKey无效"),
    /** 用户名不存在 */
    USERNAME_NOT_EXIST("020008", "appKey不存在"),

    UPLOAD_FILE_SIZE_LIMIT("020010", "上传文件大小超过限制"),
    DUPLICATE_PRIMARY_KEY("030000","唯一键冲突");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    SystemErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return msg;
    }
}