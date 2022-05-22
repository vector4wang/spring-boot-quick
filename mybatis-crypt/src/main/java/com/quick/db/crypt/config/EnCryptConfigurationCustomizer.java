package com.quick.db.crypt.config;

import com.quick.db.crypt.encrypt.Encrypt;
import com.quick.db.crypt.intercept.CryptReadInterceptor;
import com.quick.db.crypt.intercept.CryptWriteInterceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

public class EnCryptConfigurationCustomizer implements ConfigurationCustomizer {

    private Encrypt encrypt;

    public EnCryptConfigurationCustomizer(Encrypt encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public void customize(Configuration configuration) {
        configuration.addInterceptor(new CryptReadInterceptor(encrypt));
        configuration.addInterceptor(new CryptWriteInterceptor(encrypt));
    }
}
