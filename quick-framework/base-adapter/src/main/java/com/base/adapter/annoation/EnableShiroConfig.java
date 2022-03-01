package com.base.adapter.annoation;

import com.base.adapter.ShiroConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @author wangxc
 * @date: 2022/1/26 9:52 AM
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ShiroConfig.class)
public @interface EnableShiroConfig {
}
