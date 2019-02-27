package com.wx.pn.api.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public abstract class BaseModel implements Model {

    public static final SerializerFeature[] DEFAULT_FORMAT = {SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.QuoteFieldNames, SerializerFeature.SkipTransientField,
            SerializerFeature.SortField, SerializerFeature.PrettyFormat};

    /**
     * 注意DEFAULT_FORMAT
     * 对于枚举类
     * VIEW 会 转化为 view
     * @return
     */
	@Override
	public String toJsonString() {
		return JSON.toJSONString(this,DEFAULT_FORMAT);
	}
}
