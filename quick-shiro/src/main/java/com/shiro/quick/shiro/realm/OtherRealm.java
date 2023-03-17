package com.shiro.quick.shiro.realm;

import com.shiro.quick.config.ShiroConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;

@Slf4j
public class OtherRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("OtherRealm doGetAuthenticationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String username = userToken.getUsername();
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在");
        }

        if ("monster".equals(username)) {
            throw new LockedAccountException("用户被锁定");
        }

        Object principal = username;
        Object credentials = "7c4a8d09ca3762af61e59520943dc26494f8941b";
        String realmName = getName();

        log.info("doGetAuthenticationInfo username: {}", username);
        /**
         * 见 {@link ShiroConfig#myRealm} 的密码指定算法
         */
        return new SimpleAuthenticationInfo(principal, credentials, realmName);
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "SHA1";
        String credentials = "123456";
        Object salt = null;
        int hashIterations = 1;
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(simpleHash);
    }
}
