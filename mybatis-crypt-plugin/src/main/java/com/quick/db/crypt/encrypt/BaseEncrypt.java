package com.quick.db.crypt.encrypt;

/**
 * 继承此类，必须实现实现无参数和有参数的构造器
 */
public abstract class BaseEncrypt implements Encrypt {

    private static final String DEFAULT_SEC = "FMjDV69Xkd6y9HVVK";

    public String password;

    public String getDefaultPassword() {
        return password;
    }

    public BaseEncrypt() {
        this.password = DEFAULT_SEC;
    }

    public BaseEncrypt(String password) {
        this.password = password;
    }

    @Override
    public abstract String encrypt(String src);

    @Override
    public abstract String decrypt(String encrypt);
}