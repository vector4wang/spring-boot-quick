package com.wx.pn.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.wx.pn.api.model.BaseResponse;

public class SendTemplateResponse extends BaseResponse {

    /**
     * 消息id
     */
    @JSONField(name = "msgid")
    private String msgid;

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
}
