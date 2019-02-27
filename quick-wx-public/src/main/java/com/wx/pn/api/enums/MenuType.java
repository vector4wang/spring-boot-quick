package com.wx.pn.api.enums;

public enum MenuType {
    /**
     * 点击推事件
     */
    CLICK("click"),

    /**
     * 跳转URL
     */
    VIEW("view"),

    /*-------------------------以下仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户------------------------*/

    /**
     * 扫码推事件
     */
    SCANCODE_PUSH("scancode_push"),

    /**
     * 扫码推事件且弹出“消息接收中”提示框
     */
    SCANCODE_WAITMSG("scancode_waitmsg"),

    /**
     * 弹出系统拍照发图
     */
    PIC_SYSPHOTO("pic_sysphoto"),

    /**
     * 弹出拍照或者相册发图
     */
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),

    /**
     * 弹出微信相册发图器
     */
    PIC_WEIXIN("pic_weixin"),

    /**
     * 弹出地理位置选择器
     */
    LOCATION_SELECT("location_select"),


    /*-----------------------------以下专门给第三方平台旗下未微信认证（具体而言，是资质认证未通过）的订阅号准备的事件类型，它们是没有事件推送的，能力相对受限，其他类型的公众号不必使用--------------------------*/

    /**
     * 下发消息（除文本消息）
     */
    MEDIA_ID("media_id"),

    /**
     * 跳转图文消息URL
     */
    VIEW_LIMITED("view_limited");

    String value;

    MenuType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
