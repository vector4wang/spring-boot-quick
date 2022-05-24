package com.quick.db.crypt.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptField {

}