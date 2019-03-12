package com.dynamic.bean.box;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

/**
 *
 * @author wangxc
 * @date: 2019/3/13 上午12:04
 *
 */
public class AnonymousComponent {

	@Description(value = "${dynamic.annotate.unpredictable.key}")
	@Bean(name = "${dynamic.annotate.unpredictable.key}")
	public Person createBean() {
		return new Person();
	}
}
