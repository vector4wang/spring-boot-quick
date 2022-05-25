package com.quick.db.crypt.encrypt;

/**
 * @author 01390942
 * @Description
 * @create 2022/5/25
 * @since 1.0.0
 */
public abstract class BaseEncrypt implements Encrypt {

    public String password;

    public BaseEncrypt() {
        this.password = "FMjDV69Xkd6y9HVVK";
    }

    public BaseEncrypt(String password) {
        this.password = password;
    }

    @Override
    public abstract String encrypt(String src);

    @Override
    public abstract String decrypt(String encrypt);
}