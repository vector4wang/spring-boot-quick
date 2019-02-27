package com.wx.pn.api;


import com.wx.pn.api.config.ApiConfig;
import com.wx.pn.api.enums.ResultType;
import com.wx.pn.api.exception.WeixinException;
import com.wx.pn.api.model.BaseResponse;

import java.util.Objects;

/**
 * @author vector
 * @date: 2018/11/5 0005 17:54
 */
public class UserInfoApi extends BaseApi {

    /**
     * 获取用户信息(微信中)
     * @param config
     * @param openId
     * @return
     */
    public String getUsers(ApiConfig config, String openId) {
        String url = String.format(BASE_API_URL + "cgi-bin/user/info?access_token=%s&lang=zh_CN&openid=%s", config.getAccessToken(), openId);
        BaseResponse baseResponse = executeGet(url);
        if (Objects.equals(ResultType.get(baseResponse.getErrcode()), ResultType.SUCCESS)) {
            return baseResponse.getErrmsg();
        }else{
            throw new WeixinException("获取用户信息失败");
        }

    }
}
