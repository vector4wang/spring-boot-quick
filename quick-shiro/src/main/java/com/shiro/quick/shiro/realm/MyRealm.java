package com.shiro.quick.shiro.realm;

import com.shiro.quick.config.ShiroConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class MyRealm extends AuthorizingRealm {




    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Object principal = principalCollection.getPrimaryPrincipal();

        Set<String> roles = new HashSet<>();
        roles.add("user");
        if ("admin".equals(principal)) {
            roles.add("admin");
        }

        return new SimpleAuthorizationInfo(roles);
    }


    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("MyRealm doGetAuthenticationInfo");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String username = userToken.getUsername();
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在");
        }

        if ("monster".equals(username)) {
            throw new LockedAccountException("用户被锁定");
        }

        Object principal = username;
        Object credentials = "e10adc3949ba59abbe56e057f20f883e";
        String realmName = getName();


        log.info("doGetAuthenticationInfo username: {}", username);
        /**
         * 见 {@link ShiroConfig#myRealm} 的密码指定算法
         */
        return new SimpleAuthenticationInfo(principal, credentials, realmName);
    }


    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        Object salt = null;
        int hashIterations = 100;
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(simpleHash);
    }
}
