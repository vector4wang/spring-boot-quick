package com.wx.pn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author vector
 * @date: 2018/11/8 0008 11:02
 */
@Component
public class SessionCache {

    private static final int EXPIRE_TIME = 30; // 30s

    //    public static final String V1 = "V1";
    public static final String V2 = "V2";
    public static final String V3 = "V3";

    private static final String VOICE_OPENID_V1_SESSION = "openid:voice:session:v1:";
    private static final String TEXT_OPENID_V1_SESSION = "openid:text:session:v1:";

    private static final String VOICE_OPENID_V2_SESSION = "openid:voice:session:v2:";
    private static final String TEXT_OPENID_V2_SESSION = "openid:text:session:v2:";

    private static final String VOICE_OPENID_V3_SESSION = "openid:voice:session:v3:";
    private static final String TEXT_OPENID_V3_SESSION = "openid:text:session:v3:";

    public static final String DIALOG_VOICE_TYPE = "voice";
    public static final String DIALOG_TEXT_TYPE = "text";


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *
     * @param version 版本号
     * @param openId  用户唯一识别，可位微信端的openid，也可以是通话过程的callid
     * @param session 通过模块与交互模块的session，通话系统中，callid就是session
     * @param type
     */
    public void refreshSession(String version, String openId, String session, String type) {
        set(generateKey(version, openId, type), session);
    }

    public Boolean isExist(String version, String openId, String type) {
        return stringRedisTemplate.hasKey(generateKey(version, openId, type));
    }

    public String getSession(String version, String openId, String type) {
        return get(generateKey(version, openId, type));
    }

    public void delSession(String version, String openId, String type) {
        del(version, openId, type);
    }

    private String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    private void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    private void del(String version, String openId, String type) {
        stringRedisTemplate.delete(generateKey(version, openId, type));
    }

    private String generateKey(String version, String openId, String type) {
//        if (V1.equals(version)) {
//            if (DIALOG_VOICE_TYPE.equals(type)) {
//                return VOICE_OPENID_V1_SESSION + openId;
//            } else {
//                return TEXT_OPENID_V1_SESSION + openId;
//            }
//        } else
        if (V2.equals(version)) {
            if (DIALOG_VOICE_TYPE.equals(type)) {
                return VOICE_OPENID_V2_SESSION + openId;
            } else {
                return TEXT_OPENID_V2_SESSION + openId;
            }
        } else {
            if (DIALOG_VOICE_TYPE.equals(type)) {
                return VOICE_OPENID_V3_SESSION + openId;
            } else {
                return TEXT_OPENID_V3_SESSION + openId;
            }
        }

    }
}
