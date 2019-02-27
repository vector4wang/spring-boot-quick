package com.wx.pn.config;

import com.wx.pn.utils.BaseResp;
import com.wx.pn.utils.LogExceptionUtil;
import com.wx.pn.utils.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = {Controller.class})
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 系统异常处理，比如：404
     * 服务器异常自动捕捉为 500
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResp<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        logger.error("捕捉到异常：{}", LogExceptionUtil.LogExceptionStack(e));
        BaseResp baseResp = new BaseResp();
        baseResp.setMessage(LogExceptionUtil.LogExceptionStack(e));
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            baseResp.setCode(ResultStatus.http_status_not_found.getErrorCode());
        } else {
            baseResp.setCode(ResultStatus.http_status_internal_server_error.getErrorCode());
        }
        return baseResp;
    }
}