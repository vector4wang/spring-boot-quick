package com.shiro.quick.filter;

import com.shiro.quick.shiro.realm.HeaderToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class HeaderFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.warn("isAccessAllowed 方法被调用");
        if (this.isLoginRequest(request, response)) {
            return true;
        }

        //这里先让它始终返回false来使用onAccessDenied()方法
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     *
     * @param servletRequest the incoming <code>ServletRequest</code>
     * @param response       the outgoing <code>ServletResponse</code>
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response) throws Exception {
        log.warn("onAccessDenied 方法被调用");

        //这个地方和前端约定，要求前端将jwtToken放在请求的Header部分

        //所以以后发起请求的时候就需要在Header中放一个Authorization，值就是对应的Token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String headerKey = request.getHeader("Authorization");
        if (StringUtils.isEmpty(headerKey)) {
            return false;
        }
        log.info("请求的 Header 中藏有 headerKey {}", headerKey);
        HeaderToken token = new HeaderToken(headerKey);
        /*
         * 下面就是固定写法
         * */
        try {
            // 委托 realm 进行登录认证
            //所以这个地方最终还是调用JwtRealm进行的认证
            getSubject(servletRequest, response).login(token);
            //也就是subject.login(token)
        } catch (Exception e) {
            e.printStackTrace();
            onLoginFail(response);
            //调用下面的方法向客户端返回错误信息
            return false;
        }

        return true;
        //执行方法中没有抛出异常就表示登录成功
    }

    //登录失败时默认返回 401 状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }

}
