package com.quick.component.config;

import com.quick.common.base.rest.BaseResp;
import com.quick.common.base.rest.ResultStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerConfig {

    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResp exceptionHandler(Exception e) {
        return new BaseResp<>(ResultStatus.error_invalid_argument);
    }
}
