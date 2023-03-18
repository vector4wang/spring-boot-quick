package com.quick.constant;

public class RedisKeyConstant {
    
    /**
     * redis key : 每日一题
     */
    public static final String DAILY_QUESTION_KEY = "question_daily:question";
    
    /**
     * redis key : 用户每日一题做题情况
     */
    public static final String USER_DAILY_QUESTION_KEY = "question_daily:user:%s";
    
    
    /**
     * redis key : 点赞父评论
     */
    public static final String ROOT_COMMENT_LIKE_KEY = "like:root_comment";
    
    /**
     * redis key : 点赞子评论
     */
    public static final String SON_COMMENT_LIKE_KEY = "like:son_comment";
    
    /**
     * redis key : 点赞题解
     */
    public static final String NOTE_LIKE_KEY = "like:note";
    
    /**
     * redis key : 点赞问题
     */
    public static final String QUESTION_LIKE_KEY = "like:question";
    
    /**
     * redis key : 自测代码限流
     */
    public static final String TEST_RUN_CODE_KEY = "runcode:test:%s";
    
    /**
     * redis key : 提交代码限流
     */
    public static final String COMMIT_RUN_CODE_KEY = "runcode:commit:%s";
    
    /**
     * redis key : 消息模板
     */
    public static final String MESSAGE_TEMPLATE_KEY = "template_key:message";
    
    /**
     * redis key : 短信模板
     */
    public static final String SMS_TEMPLATE_KEY = "template_key:sms";
    
    /**
     * redis key : 邮件模板
     */
    public static final String EMAIL_TEMPLATE_KEY = "template_key:email";
}
