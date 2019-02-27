package com.wx.pn.api.model;

import java.io.Serializable;

public interface Model extends Serializable {

    /**
     * 将model转成json字符串
     *
     * @return json字符串
     */
    String toJsonString();
}