package com.shiro.controller;

import com.shiro.constant.Codes;
import com.shiro.vo.Json;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public Json handleShiroException(ShiroException e) {
		String eName = e.getClass().getSimpleName();
		log.error("shiro执行出错：{}", eName);
		return new Json(eName, false, Codes.SHIRO_ERR, "鉴权或授权过程出错", null);
	}

	@ExceptionHandler(UnauthenticatedException.class)
	@ResponseBody
	public Json page401() {
		return new Json("401", false, Codes.UNAUTHEN, "用户未登录", null);
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public Json page403() {
		return new Json("403", false, Codes.UNAUTHZ, "用户没有访问权限", null);
	}

}