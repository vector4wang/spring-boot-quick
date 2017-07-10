package com.quick.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;


/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 * 参考:http://neverflyaway.iteye.com/blog/2301571  http://blog.csdn.net/u010935920/article/details/71024018
 */
//@ControllerAdvice(annotations = {RestController.class})
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = { IOException.class , RuntimeException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(Exception exception, WebRequest request) {
        logger.info("Catch an exception", exception);
        return  new ModelAndView("500");
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView noMapping(Exception exception, WebRequest request) {
        logger.info("No mapping exception", exception);
        return  new ModelAndView("404");
    }

    @ExceptionHandler(value = {MultipartException.class})
    public ModelAndView fileError(Exception exception, WebRequest request){
        logger.error("上传文件异常，信息:{}",exception.getMessage());
        System.out.println(exception.getMessage());
        return  new ModelAndView("/error");
    }
}

