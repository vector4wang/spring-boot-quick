package com.wx.pn.api.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wx.pn.api.exception.WeixinException;

import java.util.List;

/**
 * @author vector
 * @date: 2018/11/8 0008 17:12
 */
public class Menu extends BaseModel {
    private List<MenuButton> button;

    private Matchrule matchrule;

    @JSONField(name = "menuid")
    private String menuId;

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        if (null == button || button.size() > 3) {
            throw new WeixinException("主菜单最多3个");
        }
        this.button = button;
    }

    public Matchrule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(Matchrule matchrule) {
        this.matchrule = matchrule;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
