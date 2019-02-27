package com.wx.pn.api.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author vector
 * @date: 2018/11/8 0008 17:29
 */
public class Matchrule extends BaseModel {
    @JSONField(name = "tag_id")
    private String tagId;

    @Deprecated
    @JSONField(name = "group_id")
    private String groupId;

    private String sex;

    private String country;

    private String province;

    private String city;

    @JSONField(name = "client_platform_type")
    private String clientPlatformType;

    /**
     * @return 群组ID
     * @deprecated 微信官方不再建议使用群组, 用标签代替
     */
    @Deprecated
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId 群组ID
     * @deprecated 微信官方不再建议使用群组, 用标签代替
     */
    @Deprecated
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Matchrule{" +
                "tagId='" + tagId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", sex='" + sex + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", clientPlatformType='" + clientPlatformType + '\'' +
                '}';
    }
}
