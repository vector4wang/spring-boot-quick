package com.wx.pn.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.wx.pn.api.model.BaseResponse;

public class GetJsApiTicketResponse extends BaseResponse {

    private String ticket;

    @JSONField(name = "expires_in")
    private Integer expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
