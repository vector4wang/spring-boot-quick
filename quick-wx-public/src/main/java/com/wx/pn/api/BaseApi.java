package com.wx.pn.api;

import com.wx.pn.api.enums.ResultType;
import com.wx.pn.api.model.BaseResponse;
import com.wx.pn.api.utils.BeanUtil;
import com.wx.pn.api.utils.NetWorkCenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vector
 * @date: 2018/11/5 0005 17:58
 */
public abstract class BaseApi {
    protected static final String BASE_API_URL = "https://api.weixin.qq.com/";

    protected BaseResponse executeGet(String url) {
        BaseResponse response;
        BeanUtil.requireNonNull(url, "url is null");
        response = NetWorkCenter.get(url);
        return response;
    }

    protected BaseResponse executePost(String url, String json, File file) {
        BaseResponse response;
        BeanUtil.requireNonNull(url, "url is null");
        List<File> files = null;
        if (null != file) {
            files = new ArrayList<File>() {{
                    add(file);
                }};
        }
        response = NetWorkCenter.post(url, json, files);
        return response;
    }


    protected BaseResponse executePost(String url, String json) {
        return executePost(url, json, null);
    }

    protected boolean isSuccess(String errCode) {
        return ResultType.SUCCESS.getCode().toString().equals(errCode);
    }
}

