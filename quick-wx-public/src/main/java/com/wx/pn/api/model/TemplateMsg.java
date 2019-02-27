package com.wx.pn.api.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

public class TemplateMsg extends BaseModel {
    private String touser;
    @JSONField(name = "template_id")
    private String templateId;
    private String url;
    @Deprecated
    private String topcolor;

    private Map<String, TemplateParam> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Deprecated
    public String getTopcolor() {
        return topcolor;
    }

    @Deprecated
    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Map<String, TemplateParam> getData() {
        return data;
    }

    public void setData(Map<String, TemplateParam> data) {
        this.data = data;
    }
}
