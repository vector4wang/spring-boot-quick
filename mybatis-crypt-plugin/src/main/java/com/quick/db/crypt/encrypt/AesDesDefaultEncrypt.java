package com.quick.db.crypt.encrypt;

import lombok.extern.slf4j.Slf4j;
import com.quick.db.crypt.util.Hex;
import org.springframework.util.StringUtils;

import javax.crypto.*;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
public class AesDesDefaultEncrypt extends BaseEncrypt {

    private static final String DEFAULT_SEC = "FMjDV69Xkd6y9HVVK";
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private String password;
    private SecretKeySpec secretKeySpec;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AesDesDefaultEncrypt() throws NoSuchAlgorithmException {
        log.info("init encrypt by default passwd");
        this.password = DEFAULT_SEC;
        this.secretKeySpec = getSecretKey(this.password);
    }

    public AesDesDefaultEncrypt(String password) throws NoSuchAlgorithmException {

        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("password should not be null!");
        }
        log.info("init encrypt by custom passwd");
        this.password = password;
        this.secretKeySpec = getSecretKey(password);
    }

    @Override
    public String encrypt(String value) {

        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] content = value.getBytes("UTF-8");
            byte[] encryptData = cipher.doFinal(content);

            return Hex.bytesToHexString(encryptData);
        } catch (Exception e) {
            log.error("AES加密时出现问题，密钥为：{}", password);
            throw new IllegalStateException("AES加密时出现问题" + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        try {
            byte[] encryptData = Hex.hexStringToBytes(value);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] content = cipher.doFinal(encryptData);
            return new String(content, "UTF-8");
        } catch (Exception e) {
            log.error("AES解密时出现问题，密钥为:{},密文为：{}", password, value);
            throw new IllegalStateException("AES解密时出现问题" + e.getMessage(), e);
        }

    }


    private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES 要求密钥长度为 128
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        kg.init(128, random);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        // 转换为AES专用密钥
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
    }
}