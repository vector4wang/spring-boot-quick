package com.wx.pn.api.config;

import com.wx.pn.api.enums.ChangeType;
import com.wx.pn.api.model.BaseModel;

import java.util.Date;

public final class ConfigChangeNotice extends BaseModel {

    private Date noticeTime;

    private String appid;

    private ChangeType type;

    private String value;

    public ConfigChangeNotice() {
        this.noticeTime = new Date();
    }

    public ConfigChangeNotice(String appid, ChangeType type, String value) {
        this();
        this.appid = appid;
        this.type = type;
        this.value = value;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public ChangeType getType() {
        return type;
    }

    public void setType(ChangeType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    @Override
    public String toString() {
        return "ConfigChangeNotice{" +
                "noticeTime=" + noticeTime +
                ", appid='" + appid + '\'' +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
