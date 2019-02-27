package com.wx.pn.api.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wx.pn.api.enums.MenuType;
import com.wx.pn.api.exception.WeixinException;

import java.util.List;

/**
 * @author vector
 * @date: 2018/11/8 0008 17:13
 */
public class MenuButton extends BaseModel {
    private MenuType type;
    private String name;
    private String key;
    private String url;

    /**
     * 菜单显示的永久素材的MaterialID,当MenuType值为media_id和view_limited时必需
     */
    @JSONField(name = "media_id")
    private String mediaId;

    /**
     * 二级菜单列表，每个一级菜单下最多5个
     */
    @JSONField(name = "sub_button")
    private List<MenuButton> subButton;

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public List<MenuButton> getSubButton() {
        return subButton;
    }

    public void setSubButton(List<MenuButton> subButton) {
        if (null == subButton || subButton.size() > 5) {
            throw new WeixinException("子菜单最多只有5个");
        }
        this.subButton = subButton;
    }
}
