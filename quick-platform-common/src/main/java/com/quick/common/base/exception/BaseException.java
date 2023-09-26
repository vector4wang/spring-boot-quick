package com.quick.common.base.exception;

import com.quick.common.base.rest.SystemErrorType;

public class BaseException extends RuntimeException {
    /**
     * 异常对应的错误类型
     */
    private final SystemErrorType errorType;

    /**
     * 默认是系统异常
     */
    public BaseException() {
        this.errorType = SystemErrorType.SYSTEM_ERROR;
    }

    public BaseException(SystemErrorType errorType) {
        this.errorType = errorType;
    }

    public BaseException(SystemErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public BaseException(SystemErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
}