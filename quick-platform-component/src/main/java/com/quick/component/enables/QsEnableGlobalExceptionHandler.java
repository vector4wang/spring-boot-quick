package com.quick.component.enables;

import com.quick.component.config.GlobalHandlerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GlobalHandlerConfig.class)
public @interface QsEnableGlobalExceptionHandler {
}
