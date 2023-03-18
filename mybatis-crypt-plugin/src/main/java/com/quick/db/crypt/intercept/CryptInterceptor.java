package com.quick.db.crypt.intercept;

import com.quick.db.crypt.encrypt.Encrypt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CryptInterceptor {

    protected Encrypt encrypt;

    public CryptInterceptor(Encrypt encrypt) {
        this.encrypt = encrypt;
    }
}
