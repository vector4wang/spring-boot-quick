package com.quick.component.enables;

import com.quick.component.config.logAspect.WebLogAspectConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 接口请求日志
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WebLogAspectConfig.class)
public @interface QsEnableAroundLog {

    String value() default "execution(public * com.*..controller.*.*(..))";
}
