package com.wx.pn.api.model;

public final class EventType {

    public static final String SUBSCRIBE       = "subscribe";
    public static final String UNSUBSCRIBE     = "unsubscribe";
    public static final String CLICK           = "CLICK";
    public static final String VIEW            = "VIEW";
    public static final String LOCATION        = "LOCATION";
    public static final String SCAN            = "SCAN";
    public static final String SCANCODEPUSH    = "scancode_push";
    public static final String SCANCODEWAITMSG = "scancode_waitmsg";
    public static final String PICSYSPHOTO     = "pic_sysphoto";
    public static final String PICPHOTOORALBUM = "pic_photo_or_album";
    public static final String PICWEIXIN       = "pic_weixin";
    public static final String LOCATIONSELECT  = "location_select";
    public static final String TEMPLATESENDJOBFINISH  = "TEMPLATESENDJOBFINISH";
    public static final String MASSSENDJOBFINISH="MASSSENDJOBFINISH";

    private EventType() {
    }

}
