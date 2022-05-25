package com.quick.crypt.test.config;

import com.quick.db.crypt.encrypt.BaseEncrypt;

/**
 * @author 01390942
 * @Description
 * @create 2022/5/25
 * @since 1.0.0
 */
public class CustomerCryptor extends BaseEncrypt {


    @Override
    public String encrypt(String src) {
        return password + src;
    }

    @Override
    public String decrypt(String encrypt) {
        return encrypt + " de " + encrypt;
    }
}