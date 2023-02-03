package com.shiro.service;

import com.shiro.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author wangxc
 * @date: 2019-12-11 22:06
 *
 */
@Service
public class UserService {
	/**
	 * 模拟查询返回用户信息
	 * @param uname
	 * @return
	 */
	public User findUserByName(String uname) {
		User user = new User();
		user.setUname(uname);
		user.setNick(uname + "NICK");
		user.setPwd("e90d70cde308af541eee5a8ef3c7f08e");//密码明文是123456 md5 打散100次
//		user.setPwd("J/ms7qTJtqmysekuY8/v1TAS+VKqXdH5sB7ulXZOWho=");//密码明文是123456
		user.setSalt("wxKYXuTPST5SG0jMQzVPsg==");//加密密码的盐值
		user.setUid(new Random().nextLong());//随机分配一个id
		user.setCreated(new Date());
		return user;
	}
}
