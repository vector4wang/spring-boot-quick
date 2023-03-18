package com.quick.event;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Slf4j
@AllArgsConstructor
public class ChatGPTEventListener extends EventSourceListener {

    private SseEmitter sseEmitter;

    private String traceId;

    private List<String> answer = new ArrayList<>();


    public ChatGPTEventListener(SseEmitter sseEmitter, String traceId) {
        this.sseEmitter = sseEmitter;
        this.traceId = traceId;
    }

    public ChatGPTEventListener() {
    }

    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("OpenAI服务器连接成功!,traceId[{}]", traceId);
    }


    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("on event data: {}", data);
        if (data.equals("[DONE]")) {
            log.info("OpenAI服务器发送结束标志!,traceId[{}]", traceId);

            /**
             * 1、完成token计算
             * 2、完成数据存储
             * 3、返回json格式，用于页面渲染
             */
            sseEmitter.send(SseEmitter.event()
                    .id("[DONE]")
                    .data("[DONE]")
                    .reconnectTime(3000));
            return;
        }
        JSONObject jsonObject = JSONUtil.parseObj(data);
        JSONArray choicesJsonArray = jsonObject.getJSONArray("choices");
        String content = null;
        if (choicesJsonArray.isEmpty()) {
            content = "";
        } else {
            JSONObject choiceJson = choicesJsonArray.getJSONObject(0);
            JSONObject deltaJson = choiceJson.getJSONObject("delta");
            String text = deltaJson.getStr("content");
            if (text != null) {
                content = text;
                answer.add(content);
                sseEmitter.send(SseEmitter.event()
                        .data(content)
                        .reconnectTime(2000));
            }
        }

    }


    @Override
    public void onClosed(EventSource eventSource) {
        log.info("OpenAI服务器关闭连接!,traceId[{}]", traceId);
        log.info("complete answer: {}", StrUtil.join("", answer));
        sseEmitter.complete();
    }


    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        // TODO 处理前端中断
        log.error("OpenAI服务器连接异常!response：[{}]，traceId[{}] 当前内容:{}", response, traceId, StrUtil.join("", answer), t);
        eventSource.cancel();
        sseEmitter.completeWithError(t);
    }
}