package com.quick.service;

import com.quick.exception.BusinessException;
import com.quick.vo.ChatRequest;
import com.quick.vo.SseEmitterResultVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface SseEmitterService {
    SseEmitter streamChat(ChatRequest request);

    public SseEmitter createSseConnect(String clientId) throws BusinessException;

    public void closeSseConnect(String clientId) ;

    // 根据客户端id获取SseEmitter对象
    public SseEmitter getSseEmitterByClientId(String clientId);

    // 推送消息到客户端，此处结合业务代码，业务中需要推送消息处调用即可向客户端主动推送消息
    public void sendMsgToClient(List<SseEmitterResultVO> sseEmitterResultVOList) ;

    SseEmitter ask(String clientId) throws BusinessException;
}
