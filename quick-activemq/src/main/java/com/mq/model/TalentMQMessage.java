package com.mq.model;

import java.io.Serializable;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/13
 * Time: 14:54
 * Description:
 */
public class TalentMQMessage implements Serializable {

    /**
     * TalentId : a848030e-b09b-4c93-a10e-37e8cc956621
     * CallbackData :
     * CallbackQueue :
     */

    private String TalentId;
    private String CallbackData;
    private String CallbackQueue;


    public String getTalentId() {
        return TalentId;
    }

    public void setTalentId(String TalentId) {
        this.TalentId = TalentId;
    }

    public String getCallbackData() {
        return CallbackData;
    }

    public void setCallbackData(String CallbackData) {
        this.CallbackData = CallbackData;
    }

    public String getCallbackQueue() {
        return CallbackQueue;
    }

    public void setCallbackQueue(String CallbackQueue) {
        this.CallbackQueue = CallbackQueue;
    }

    @Override
    public String toString() {
        return "TalentMQMessage{" +
                "TalentId='" + TalentId + '\'' +
                ", CallbackData='" + CallbackData + '\'' +
                ", CallbackQueue='" + CallbackQueue + '\'' +
                '}';
    }
}
