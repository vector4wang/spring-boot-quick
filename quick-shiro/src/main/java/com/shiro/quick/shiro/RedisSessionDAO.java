//package com.shiro.quick.shiro;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.session.UnknownSessionException;
//import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.Resource;
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.Collections;
//
//@Slf4j
//public class RedisSessionDAO extends AbstractSessionDAO {
//    private static final String PREFIX_SESSION = "SHIRO:SESSION:";
//
//
//    @Resource
//    private StringRedisTemplate redisService;
//
//    public void setRedisService(StringRedisTemplate redisService){
//        this.redisService=redisService;
//    }
//
//    @Override
//    protected Serializable doCreate(Session session) {
//        Serializable sessionId = generateSessionId(session);
//        assignSessionId(session, sessionId);
//        update(session);
//        return sessionId;
//    }
//
//    @Override
//    protected Session doReadSession(Serializable sessionId) {
//        try{
//            return (Session) redisService.get(getSessionKey(sessionId));
//        }catch (Exception e){
//            log.error(this.getClass().getName()+" doReadSession 出现异常"+e.getMessage());
//            return null;
//        }
//    }
//
//    @Override
//    public void update(Session session) throws UnknownSessionException {
//        long timeout = session.getTimeout();
//        redisService.set(getSessionKey(session.getId()), session, timeout);
//    }
//
//    @Override
//    public void delete(Session session) {
//        redisService.del(getSessionKey(session.getId()));
//    }
//
//    @Override
//    public Collection<Session> getActiveSessions() {
//        return Collections.emptyList();
//    }
//
//    public static String getSessionKey(Serializable id) {
//        return PREFIX_SESSION + id;
//    }
//}
//
//
