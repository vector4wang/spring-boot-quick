package com.wx.pn.api.utils;


/**
 * @author vector
 * @date: 2018/11/6 0006 11:28
 * 异常提示
 */
public class WXTip {
    public static String errTip(String fromUserName, String toUserName) {
        return MessageUtil.reversalMessage(fromUserName, toUserName, "神马情况~");
    }

    public static String warnTip(String fromUserName, String toUserName) {
        return MessageUtil.reversalMessage(fromUserName, toUserName, "警告信息▄█▀█●给跪了");
    }
}
