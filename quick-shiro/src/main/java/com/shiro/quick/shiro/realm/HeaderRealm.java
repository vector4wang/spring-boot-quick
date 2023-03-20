package com.shiro.quick.shiro.realm;

import com.shiro.quick.shiro.token.HeaderToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class HeaderRealm extends AuthorizingRealm {
    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof HeaderToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //认证
    //这个token就是从过滤器中传入的jwtToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String headerKey = (String) token.getPrincipal();
        if (headerKey == null) {
            throw new NullPointerException("headerKey 不允许为空");
        }
        if (!"123".equals(headerKey)) {
            throw new AuthenticationException("认证失败");
        }
        // 校验逻辑
        //...
        //下面是验证这个user是否是真实存在的
        log.info("使用header认证： " + headerKey);

        return new SimpleAuthenticationInfo(headerKey, headerKey, "HeaderRealm");
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名

    }

}