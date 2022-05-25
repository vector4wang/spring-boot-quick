package com.quick.db.crypt.annotation;

import com.quick.db.crypt.config.EncryptConfig;
import com.quick.db.crypt.encrypt.AesDesDefaultEncrypt;
import com.quick.db.crypt.encrypt.Encrypt;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author vector4wang
 * https://www.jianshu.com/p/c92a6a9d8c4f
 * https://www.jianshu.com/p/3a2aac411edf
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EncryptConfig.class)
public @interface EnableEncrypt {
    /**
     * 加密key，一般使用默认
     *
     * @return
     */
    String value() default "";

    Class<? extends Encrypt> encryptIml() default AesDesDefaultEncrypt.class;

}
