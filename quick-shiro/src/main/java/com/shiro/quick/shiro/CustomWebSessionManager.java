package com.shiro.quick.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 自定义session管理，当前为了禁止在url上拼接sessionid
 */
@Slf4j
public class CustomWebSessionManager extends DefaultWebSessionManager {
    public static final String AUTH_HEAD_TOKEN = "auth_token";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            log.info("request url: {}", req.getRequestURL());
//            String sessionId = req.getHeader(AUTH_HEAD_TOKEN);
//            if (!StringUtils.isEmpty(sessionId)) {
//                // 禁止在url上拼接 JSessionId
//                request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, this.isSessionIdUrlRewritingEnabled());
//                return sessionId;
//            }
        }
        return super.getSessionId(request, response);
    }
}