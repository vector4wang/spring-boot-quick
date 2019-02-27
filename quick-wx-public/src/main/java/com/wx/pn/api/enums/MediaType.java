package com.wx.pn.api.enums;

public enum MediaType {

    /**
     * 图片
     */
    IMAGE("image"),

    /**
     * 语音
     */
    VOICE("voice"),

    /**
     * 视频
     */
    VIDEO("video"),

    /**
     * 缩略图
     */
    THUMB("thumb"),

    /**
     * 图文消息
     */
    NEWS("news");

    String value;

    MediaType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
