package com.wx.pn.api.utils;


import com.thoughtworks.xstream.XStream;
import com.wx.pn.api.model.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    private static Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    private final static Pattern p = Pattern.compile("/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>");

//    private static ApiConfig apiConfig = new ApiConfig();

    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 将发送消息封装成对应的xml格式
     */
    public static String messageToxml(BaseMessage message) {
        XStream xstream = new XStream();
        xstream.alias("xml", message.getClass());
        return xstream.toXML(message);
    }

    /**
     * 封装发送消息对象,封装时，需要将调换发送者和接收者的关系
     *
     * @param FromUserName
     * @param ToUserName
     */
    public static String subscribeMessage(String FromUserName, String ToUserName) {
        String message = "Hi！我的小伙伴~欢迎关注人事情报科！\n" +
                "\n" +
                "快速搜索查看2000万人才简历，点击<a href=\"http://wx.rchezi.com\">【搜索】</a>\n" +
                "\n" +
                "与HR和猎头加好友交换简历~~点击 <a href=\"http://www.rchezi.com/?from=WeChatGZH\">【交换简历】</a>\n" +
                "\n" +
                "体验产品，领新手红包，点击<a href=\"http://channel.rchezi.com/download?c=WeChatGZH\">【下载App】</a>\n" +
                "\n" +
                "更多人事资讯请时刻关注我们哦，定期免费课程大放送~";
        return reversalMessage(FromUserName, ToUserName, message);
    }

    /**
     * 封装发送消息对象,封装时，需要将调换发送者和接收者的关系
     *
     * @param fromUserName
     * @param toUserName
     * @param Content
     */
    public static String reversalMessage(String fromUserName, String toUserName, String Content) {
        TextMessage text = new TextMessage();
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName);
        text.setContent(Content);
        text.setCreateTime(new Date().getTime());
        text.setMsgType(ReqType.TEXT);
        return messageToxml(text);
    }

    /**
     * 封装voice消息对象
     *
     * @param fromUserName
     * @param toUserName
     * @param mediaId
     * @return
     */
    public static String reversalVoiceMessage(String fromUserName, String toUserName, String mediaId) {
        VoiceMessage voiceMessage = new VoiceMessage();
        voiceMessage.setToUserName(fromUserName);
        voiceMessage.setFromUserName(toUserName);
        voiceMessage.setVoice(new Voice(mediaId));
        voiceMessage.setCreateTime(new Date().getTime());
        voiceMessage.setMsgType(ReqType.VOICE);
        return messageToxml(voiceMessage);
    }

    /**
     * 判断是否是QQ表情
     *
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
        boolean result = false;
        // 判断QQ表情的正则表达式
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }

}