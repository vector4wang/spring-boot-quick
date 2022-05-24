package com.quick.db.crypt.intercept;

import com.quick.db.crypt.encrypt.AesDesDefaultEncrypt;
import com.quick.db.crypt.encrypt.Encrypt;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;

@Slf4j
public abstract class CryptInterceptor {

    protected Encrypt encrypt;

    public CryptInterceptor() throws NoSuchAlgorithmException {
        encrypt = new AesDesDefaultEncrypt();
    }

    public CryptInterceptor(Encrypt encrypt) {
        this.encrypt = encrypt;
    }
}
