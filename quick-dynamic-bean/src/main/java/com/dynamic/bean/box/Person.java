package com.dynamic.bean.box;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author wangxc
 * @date: 2019/3/12 下午10:41
 *
 */
@Getter
@Setter
public class Person {
	private String id;
	private String name;
	private String address;
	private int age;
	private Date birthday;
}
