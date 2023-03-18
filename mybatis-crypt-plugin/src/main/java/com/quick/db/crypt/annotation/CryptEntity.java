package com.quick.db.crypt.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptEntity {
}
