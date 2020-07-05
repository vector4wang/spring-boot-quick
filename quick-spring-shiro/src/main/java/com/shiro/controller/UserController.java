package com.shiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiro.entity.User;
import com.shiro.vo.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wangxc
 * @date: 2019-12-11 22:40
 *
 */
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

	@PostMapping("/login")
	public Json login(@RequestBody String body) {
		String oper = "user login";
		log.info("{}, body: {}", oper, body);
		JSONObject json = JSON.parseObject(body);
		String uname = json.getString("uname");
		String pwd = json.getString("pwd");
		if (StringUtils.isEmpty(uname)) {
			return Json.fail(oper, "用户名不能为空");
		}
		if (StringUtils.isEmpty(pwd)) {
			return Json.fail(oper, "密码不能为空");
		}
		Subject currentUser = SecurityUtils.getSubject();
		try {
			//登录
			currentUser.login(new UsernamePasswordToken(uname, pwd));
			//从session取出用户信息
			User user = (User) currentUser.getPrincipal();
			if (user == null)
				throw new AuthenticationException();
			//返回登录用户的信息给前台，含用户的所有角色和权限
			return Json.succ(oper).data("uid", user.getUid()).data("nick", user.getNick())
					.data("roles", user.getRoles()).data("perms", user.getPerms());

		} catch (UnknownAccountException uae) {
			log.warn("用户帐号不正确");
			return Json.fail(oper, "用户帐号或密码不正确");

		} catch (IncorrectCredentialsException ice) {
			log.warn("用户密码不正确");
			return Json.fail(oper, "用户帐号或密码不正确");

		} catch (LockedAccountException lae) {
			log.warn("用户帐号被锁定");
			return Json.fail(oper, "用户帐号被锁定不可用");

		} catch (AuthenticationException ae) {
			log.warn("登录出错");
			return Json.fail(oper, "登录失败：" + ae.getMessage());
		}
	}
}
