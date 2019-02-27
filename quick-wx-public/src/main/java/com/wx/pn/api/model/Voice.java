package com.wx.pn.api.model;

/**
 * @author vector
 * @date: 2018/11/5 0005 14:19
 */
public class Voice {
    private String MediaId;

    public Voice() {
    }

    public Voice(String mediaId) {
        this.MediaId = mediaId;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
