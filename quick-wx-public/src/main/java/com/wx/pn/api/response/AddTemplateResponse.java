package com.wx.pn.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.wx.pn.api.model.BaseResponse;

public class AddTemplateResponse extends BaseResponse {
    /**
     * 模版id
     */
    @JSONField(name = "template_id")
    private String templateId;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
