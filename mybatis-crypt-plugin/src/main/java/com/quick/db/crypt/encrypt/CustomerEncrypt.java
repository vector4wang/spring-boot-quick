package com.quick.db.crypt.encrypt;

/**
 * @author 01390942
 * @Description
 * @create 2022/5/25
 * @since 1.0.0
 */
public class CustomerEncrypt extends BaseEncrypt {
    @Override
    public String encrypt(String src) {
        return src + " 使用 " + password + "已加密";
    }

    @Override
    public String decrypt(String encrypt) {
        return encrypt + " 使用" + password + "已解密";
    }
}