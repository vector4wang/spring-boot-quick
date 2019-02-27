package com.wx.pn.api;

import com.alibaba.fastjson.JSON;
import com.wx.pn.api.config.ApiConfig;
import com.wx.pn.api.enums.ResultType;
import com.wx.pn.api.model.BaseResponse;
import com.wx.pn.api.model.Industry;
import com.wx.pn.api.model.TemplateMsg;
import com.wx.pn.api.response.AddTemplateResponse;
import com.wx.pn.api.response.SendTemplateResponse;
import com.wx.pn.api.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vector
 * @date: 2018/11/16 0016 10:58
 */
public class TemplateMsgApi extends BaseApi {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateMsgApi.class);


    private ApiConfig apiConfig;

    public TemplateMsgApi(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    /**
     * 设置行业
     *
     * @param industry 行业参数
     * @return 操作结果
     */
    public ResultType setIndustry(Industry industry) {
        LOG.debug("设置行业......");
        BeanUtil.requireNonNull(industry, "行业对象为空");
        String url = BASE_API_URL + "cgi-bin/template/api_set_industry?access_token=" + apiConfig.getAccessToken();
        BaseResponse response = executePost(url, industry.toJsonString());
        return ResultType.get(response.getErrcode());
    }

    /**
     * 添加模版
     *
     * @param shortTemplateId 模版短id
     * @return 操作结果
     */
    public AddTemplateResponse addTemplate(String shortTemplateId) {
        LOG.debug("添加模版......");
        BeanUtil.requireNonNull(shortTemplateId, "短模版id必填");
        String url = BASE_API_URL + "cgi-bin/template/api_add_template?access_token=" + apiConfig.getAccessToken();
        ;
        Map<String, String> params = new HashMap<String, String>();
        params.put("template_id_short", shortTemplateId);
        BaseResponse r = executePost(url, JSON.toJSONString(params));
        String resultJson = isSuccess(r.getErrcode()) ? r.getErrmsg() : r.toJsonString();
        return JSON.parseObject(resultJson, AddTemplateResponse.class);
    }

    /**
     * 发送模版消息
     *
     * @param msg 消息
     * @return 发送结果
     */
    public SendTemplateResponse send(TemplateMsg msg) {
        LOG.debug("发送模版消息......");
        BeanUtil.requireNonNull(msg.getTouser(), "openid is null");
        BeanUtil.requireNonNull(msg.getTemplateId(), "template_id is null");
        BeanUtil.requireNonNull(msg.getData(), "data is null");
//        BeanUtil.requireNonNull(msg.getTopcolor(), "top color is null");
//        BeanUtil.requireNonNull(msg.getUrl(), "url is null");
        String url = BASE_API_URL + "cgi-bin/message/template/send?access_token=" + apiConfig.getAccessToken();
        ;
        BaseResponse r = executePost(url, msg.toJsonString());
        String resultJson = isSuccess(r.getErrcode()) ? r.getErrmsg() : r.toJsonString();
        SendTemplateResponse result = JSON.parseObject(resultJson, SendTemplateResponse.class);
        return result;
    }

}
