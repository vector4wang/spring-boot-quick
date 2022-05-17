package com.quick.db.crypt.intercept.anno;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptEntity {
}