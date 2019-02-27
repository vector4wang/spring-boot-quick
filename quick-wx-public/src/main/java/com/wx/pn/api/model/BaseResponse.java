package com.wx.pn.api.model;

import com.wx.pn.api.enums.ResultType;
import com.wx.pn.api.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;

public class BaseResponse extends BaseModel {

	private String errcode;
	private String errmsg;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		String result = this.errmsg;
		//将接口返回的错误信息转换成中文，方便提示用户出错原因
		if (StringUtils.isNotBlank(this.errcode) && !ResultType.SUCCESS.getCode().toString().equals(this.errcode)) {
			ResultType resultType = ResultType.get(this.errcode);
			if (BeanUtil.nonNull(resultType)) {
				result = resultType.getDescription();
			}
		}
		return result;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
