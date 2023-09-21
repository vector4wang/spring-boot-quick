package com.shiro.quick.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

import static com.shiro.quick.shiro.filter.HeaderFilter.HEADER_KEY;

/**
 * https://william-zzw.github.io/2018/07/13/Shiro%E6%98%AF%E5%A6%82%E4%BD%95%E6%8B%A6%E6%88%AA%E6%9C%AA%E7%99%BB%E5%BD%95%E8%AF%B7%E6%B1%82%E7%9A%84(%E4%BA%8C)/
 * 自定义的sessionManager
 */
@Slf4j
public class CustomSessionManager extends DefaultWebSessionManager {
//    private static final String AUTH_TOKEN = "Token";
//    private static final String DEVICE = "device";
    private static final String LOGIN_STATE_ID = "JSESSIONID";
    private static final String MOBILE = "mobile";

    public CustomSessionManager() {
        super();
    }

    /**
     * 重写父类获取sessionID的方法,若请求为APP或者H5则从请求头中取出token
     *
     * @param request  请求参数
     * @param response 响应参数
     * @return id
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        if (!(request instanceof HttpServletRequest)) {
            log.debug("Current request is not an HttpServletRequest - cannot get session ID.  Returning null.");
            return null;
        }
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        if (StringUtils.hasText(httpRequest.getHeader(HEADER_KEY))) {
            String token = httpRequest.getHeader(HEADER_KEY);
//            if (!StringUtils.hasText(httpRequest.getHeader(DEVICE)) || !MOBILE.equals(httpRequest.getHeader(DEVICE))) {
//                token = getLoginStateId(token);
//            }
            // 每次读取之后都把当前的token放入response中
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            if (StringUtils.hasText(token)) {
                httpResponse.setHeader(HEADER_KEY, token);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            }
            //sessionIdUrlRewritingEnabled的配置为false,不会在url的后面带上sessionID
            request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());
            return token;
        }
        return getReferencedSessionId(request, response);
    }

//    private String getLoginStateId(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            return jwt.getClaim(LOGIN_STATE_ID).asString();
//        } catch (JWTDecodeException e) {
//            return null;
//        }
//    }

    /**
     * shiro默认从cookie中获取sessionId
     *
     * @param request  请求参数
     * @param response 响应参数
     * @return
     */
    private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {
        String id = getSessionIdCookieValue(request, response);
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
        } else {
            //not in a cookie, or cookie is disabled - try the request URI as a fallback (i.e. due to URL rewriting):
            //try the URI path segment parameters first:
            id = getUriPathSegmentParamValue(request, ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
            if (id == null) {
                //not a URI path segment parameter, try the query parameters:
                String name = getSessionIdName();
                id = request.getParameter(name);
                if (id == null) {
                    //try lowercase:
                    id = request.getParameter(name.toLowerCase());
                }
            }
            if (id != null) {
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                        ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
            }
        }
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            //automatically mark it valid here.  If it is invalid, the
            //onUnknownSession method below will be invoked and we'll remove the attribute at that time.
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }
        // always set rewrite flag - SHIRO-361
        request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());
        return id;
    }

    /**
     * copy from DefaultWebSessionManager
     *
     * @param request  请求参数
     * @param response 响应参数
     * @return
     */
    private String getSessionIdCookieValue(ServletRequest request, ServletResponse response) {
        if (!isSessionIdCookieEnabled()) {
            log.debug("Session ID cookie is disabled - session id will not be acquired from a request cookie.");
            return null;
        }
        if (!(request instanceof HttpServletRequest)) {
            log.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));
    }

    private String getUriPathSegmentParamValue(ServletRequest servletRequest, String paramName) {
        if (!(servletRequest instanceof HttpServletRequest)) {
            return null;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if (uri == null) {
            return null;
        }
        int queryStartIndex = uri.indexOf('?');
        if (queryStartIndex >= 0) {
            uri = uri.substring(0, queryStartIndex);
        }
        int index = uri.indexOf(';');
        if (index < 0) {
            //no path segment params - return:
            return null;
        }
        //there are path segment params, let's get the last one that may exist:
        final String TOKEN = paramName + "=";
        uri = uri.substring(index + 1);
        //we only care about the last JSESSIONID param:
        index = uri.lastIndexOf(TOKEN);
        if (index < 0) {
            //no segment param:
            return null;
        }
        uri = uri.substring(index + TOKEN.length());
        index = uri.indexOf(';');
        if (index >= 0) {
            uri = uri.substring(0, index);
        }
        return uri;
    }

    private String getSessionIdName() {
        String name = this.getSessionIdCookie() != null ? this.getSessionIdCookie().getName() : null;
        if (name == null) {
            name = ShiroHttpSession.DEFAULT_SESSION_ID_NAME;
        }
        return name;
    }


    /**
     * 存储会话id到response header中
     *
     * @param currentId 会话ID
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     */
    private void storeSessionId(Serializable currentId, HttpServletRequest request, HttpServletResponse response) {
        if (currentId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        }
        String idString = currentId.toString();
        //增加判断，如果请求头中包含DEVICE=MOBILE，则将sessionId放在header中返回
        if (StringUtils.hasText(request.getHeader(HEADER_KEY))) {
            response.setHeader(HEADER_KEY, idString);
        } else {
            Cookie template = getSessionIdCookie();
            Cookie cookie = new SimpleCookie(template);
            cookie.setValue(idString);
            cookie.saveTo(request, response);
        }
        log.trace("Set session ID cookie for session with id {}", idString);
    }

    /**
     * 设置deleteMe到response header中
     *
     * @param request  request
     * @param response HttpServletResponse
     */
    private void removeSessionIdCookie(HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.hasText(request.getHeader(HEADER_KEY))) {
            response.setHeader(HEADER_KEY, Cookie.DELETED_COOKIE_VALUE);
        } else {
            getSessionIdCookie().removeFrom(request, response);
        }
    }

    /**
     * 会话创建
     * Stores the Session's ID, usually as a Cookie, to associate with future requests.
     *
     * @param session the session that was just {@link #createSession created}.
     */
    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
        if (!WebUtils.isHttp(context)) {
            log.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response " +
                    "pair. No session ID cookie will be set.");
            return;
        }
        HttpServletRequest request = WebUtils.getHttpRequest(context);
        HttpServletResponse response = WebUtils.getHttpResponse(context);
        if (isSessionIdCookieEnabled()) {
            Serializable sessionId = session.getId();
            storeSessionId(sessionId, request, response);
        } else {
            log.debug("Session ID cookie is disabled.  No cookie has been set for new session with id {}", session.getId());
        }
        request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
    }

    /**
     * 会话失效
     *
     * @param s   Session
     * @param ese ExpiredSessionException
     * @param key SessionKey
     */
    @Override
    protected void onExpiration(Session s, ExpiredSessionException ese, SessionKey key) {
        super.onExpiration(s, ese, key);
        onInvalidation(key);
    }

    @Override
    protected void onInvalidation(Session session, InvalidSessionException ise, SessionKey key) {
        super.onInvalidation(session, ise, key);
        onInvalidation(key);
    }

    private void onInvalidation(SessionKey key) {
        ServletRequest request = WebUtils.getRequest(key);
        if (request != null) {
            request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID);
        }
        if (WebUtils.isHttp(key)) {
            log.debug("Referenced session was invalid.  Removing session ID cookie.");
            removeSessionIdCookie(WebUtils.getHttpRequest(key), WebUtils.getHttpResponse(key));
        } else {
            log.debug("SessionKey argument is not HTTP compatible or does not have an HTTP request/response " +
                    "pair. Session ID cookie will not be removed due to invalidated session.");
        }
    }

    /**
     * 会话销毁
     *
     * @param session Session
     * @param key     SessionKey
     */
    @Override
    protected void onStop(Session session, SessionKey key) {
        super.onStop(session, key);
        if (WebUtils.isHttp(key)) {
            HttpServletRequest request = WebUtils.getHttpRequest(key);
            HttpServletResponse response = WebUtils.getHttpResponse(key);
            log.debug("Session has been stopped (subject logout or explicit stop).  Removing session ID cookie.");
            removeSessionIdCookie(request, response);
        } else {
            log.debug("SessionKey argument is not HTTP compatible or does not have an HTTP request/response " +
                    "pair. Session ID cookie will not be removed due to stopped session.");
        }
    }
}

