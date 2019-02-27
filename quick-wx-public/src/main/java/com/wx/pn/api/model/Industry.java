package com.wx.pn.api.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Industry extends BaseModel {
    /**
     * 行业1
     */
    @JSONField(name = "industry_id1")
    private String industryId1;
    /**
     * 行业2
     */
    @JSONField(name = "industry_id2")
    private String industryId2;

    public String getIndustryId1() {
        return industryId1;
    }

    public void setIndustryId1(String industryId1) {
        this.industryId1 = industryId1;
    }

    public String getIndustryId2() {
        return industryId2;
    }

    public void setIndustryId2(String industryId2) {
        this.industryId2 = industryId2;
    }
}
