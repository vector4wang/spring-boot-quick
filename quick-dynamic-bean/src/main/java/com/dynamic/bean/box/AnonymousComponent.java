package com.dynamic.bean.box;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

/**
 *
 * @author wangxc
 * @date: 2019/3/13 上午12:04
 *
 */
@Getter
@Setter
@Description(value = "${dynamic.annotate.unpredictable.key}")
public class AnonymousComponent {
	private String other;
}
