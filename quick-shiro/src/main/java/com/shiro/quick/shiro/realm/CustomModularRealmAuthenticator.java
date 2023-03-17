package com.shiro.quick.shiro.realm;

import com.shiro.quick.config.SpringBeanFactory;
import com.shiro.quick.shiro.token.HeaderToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

@Slf4j
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 重写多realm认证
     *
     * @param realms 领域
     * @param token  令牌
     * @return {@link AuthenticationInfo}
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        Realm finalRealm;
        // 匹配Realm名称
        if (token instanceof HeaderToken) {
            finalRealm = SpringBeanFactory.getBean(HeaderRealm.class);
        } else {
            finalRealm = SpringBeanFactory.getBean(MyRealm.class);
        }
        return super.doSingleRealmAuthentication(finalRealm, token);
    }

}