package com.quick.db.crypt.annotation;

import com.quick.db.crypt.config.EncryptConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author vector4wang
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EncryptConfig.class)
public @interface EnableEncrypt {
    /**
     * 加密key，一般使用默认
     * @return
     */
    String value() default "12315456asdf123asd456f41231vc53ads";
}
