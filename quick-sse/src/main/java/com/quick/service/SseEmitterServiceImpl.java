package com.quick.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.quick.constant.SseEmitterConstant;
import com.quick.event.ChatGPTEventListener;
import com.quick.exception.BusinessException;
import com.quick.vo.ChatRequest;
import com.quick.vo.SseEmitterResultVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
@Slf4j
public class SseEmitterServiceImpl implements SseEmitterService {

    private static final String URL = "https://api.openai.com/v1/chat/completions";

    /**
     * 容器，保存连接，用于输出返回
     */
    private static Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();

    private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    private OkHttpClient okHttpClient;

    @Value("${chatgpt.key:}")
    private String chatgptKey;


    @Override
    @SneakyThrows
    public SseEmitter streamChat(ChatRequest request) {
        request.setStream(true);
        SseEmitter sseEmitter = new SseEmitter(300000L);
        sseEmitter.onCompletion(() -> log.info("答案已返回，请处理后续逻辑"));
        String traceId = UUID.randomUUID().toString();
        sseEmitter.send(SseEmitter.event().id(traceId).name("update"));
        ChatGPTEventListener chatGPTEventListener = new ChatGPTEventListener(sseEmitter,traceId);
        streamChat(request,chatGPTEventListener,traceId);
        log.info("请求已结束");
        return sseEmitter;
    }

    public void streamChat(ChatRequest request, EventSourceListener eventSourceListener, String traceId) {
        try {
            EventSource.Factory factory = EventSources.createFactory(this.okHttpClient);

            Request apiRequest = new Request.Builder()
                    .url(URL)
                    .post(RequestBody.create(JSONUtil.toJsonStr(request), okhttp3.MediaType.parse("application/json")))
                    .header("Authorization", "Bearer " + chatgptKey)
                    .build();
            factory.newEventSource(apiRequest, eventSourceListener);
        } catch (Exception e) {
            log.error("请求服务器异常!：traceId{}", traceId, e);
        }
    }



    @Override
    public SseEmitter createSseConnect(String clientId) throws BusinessException {
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
        SseEmitter sseEmitter = new SseEmitter(0L);
        // 是否需要给客户端推送ID
        if (StrUtil.isBlank(clientId)) {
            clientId = IdUtil.simpleUUID();
        }
        // 注册回调
        sseEmitter.onCompletion(completionCallBack(clientId));
        sseCache.put(clientId, sseEmitter);
        log.info("创建新的sse连接，当前用户：{}", clientId);

        try {

//                sseEmitter.send(SseEmitter.event().id(SseEmitterConstant.CLIENT_ID).data(clientId+i));
            cachedThreadPool.execute(() -> {
                try {
                    for (int i = 0; i < 10; i++) {
                        sseEmitter.send(IdUtil.simpleUUID());
                        TimeUnit.SECONDS.sleep(1);
                    }
                    sseEmitter.complete();
                } catch (Exception e) {
                    sseEmitter.completeWithError(e);
                    log.error("SseEmitterServiceImpl->createSseConnect error", e);
                }
            });


        } catch (Exception e) {
            log.error("SseEmitterServiceImpl[createSseConnect]: 创建长链接异常，客户端ID:{}", clientId, e);
            throw new BusinessException("创建连接异常！", e);
        }
        return sseEmitter;
    }

    @Override
    public void closeSseConnect(String clientId) {
        SseEmitter sseEmitter = sseCache.get(clientId);
        if (sseEmitter != null) {
            sseEmitter.complete();
            removeUser(clientId);
        }
    }

    // 根据客户端id获取SseEmitter对象
    @Override
    public SseEmitter getSseEmitterByClientId(String clientId) {
        return sseCache.get(clientId);
    }

    // 推送消息到客户端，此处结合业务代码，业务中需要推送消息处调用即可向客户端主动推送消息
    @Override
    public void sendMsgToClient(List<SseEmitterResultVO> sseEmitterResultVOList) {
        if (CollectionUtil.isEmpty(sseCache)) {
            return;
        }
        for (Map.Entry<String, SseEmitter> entry : sseCache.entrySet()) {
            sendMsgToClientByClientId(entry.getKey(), sseEmitterResultVOList, entry.getValue());
        }
    }

    @Override
    public SseEmitter ask(String clientId) throws BusinessException {
        SseEmitter sseEmitter = new SseEmitter(0L);
        if (StrUtil.isBlank(clientId)) {
            clientId = IdUtil.simpleUUID();
        }
        // 注册回调
        sseEmitter.onCompletion(completionCallBack(clientId));
        sseCache.put(clientId, sseEmitter);
        log.info("创建新的sse连接，当前用户：{}", clientId);

        try {
            // 主线程不能阻塞
            sseEmitter.send(SseEmitter.event().id(SseEmitterConstant.CLIENT_ID).data(clientId));
            cachedThreadPool.execute(() -> {
                try {
                    for (int i = 0; i < 10; i++) {
                        String now = DateUtil.now();
                        sseEmitter.send(now+": "+IdUtil.simpleUUID());
                        TimeUnit.SECONDS.sleep(1);
                    }
                    sseEmitter.complete();
                } catch (Exception e) {
                    sseEmitter.completeWithError(e);
                    log.error("SseEmitterServiceImpl->createSseConnect error", e);
                }
            });
//            while (cachedThreadPool.awaitTermination(10,TimeUnit.SECONDS));
//            for (int i = 0; i < 10; i++) {
//                String now = DateUtil.now();
//                sseEmitter.send(now+": "+IdUtil.simpleUUID());
//                TimeUnit.SECONDS.sleep(1);
//            }
//            sseEmitter.complete();
            log.info("main thread");
        } catch (Exception e) {
            log.error("SseEmitterServiceImpl[createSseConnect]: 创建长链接异常，客户端ID:{}", clientId, e);
            throw new BusinessException("创建连接异常！", e);
        }
        return sseEmitter;
    }

    /**
     * 推送消息到客户端
     * 此处做了推送失败后，重试推送机制，可根据自己业务进行修改
     *
     * @param clientId               客户端ID
     * @param sseEmitterResultVOList 推送信息，此处结合具体业务，定义自己的返回值即可
     * @author re
     * @date 2022/3/30
     **/
    private void sendMsgToClientByClientId(String clientId, List<SseEmitterResultVO> sseEmitterResultVOList, SseEmitter sseEmitter) {
        if (sseEmitter == null) {
            log.error("SseEmitterServiceImpl[sendMsgToClient]: 推送消息失败：客户端{}未创建长链接,失败消息:{}",
                    clientId, sseEmitterResultVOList.toString());
            return;
        }

        SseEmitter.SseEventBuilder sendData = SseEmitter.event().id(SseEmitterConstant.TASK_RESULT).data(sseEmitterResultVOList, MediaType.APPLICATION_JSON);
        try {
            sseEmitter.send(sendData);
        } catch (IOException e) {
            // 推送消息失败，记录错误日志，进行重推
            log.error("SseEmitterServiceImpl[sendMsgToClient]: 推送消息失败：{},尝试进行重推", sseEmitterResultVOList.toString(), e);
            boolean isSuccess = true;
            // 推送消息失败后，每隔10s推送一次，推送5次
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10000);
                    sseEmitter = sseCache.get(clientId);
                    if (sseEmitter == null) {
                        log.error("SseEmitterServiceImpl[sendMsgToClient]：{}的第{}次消息重推失败，未创建长链接", clientId, i + 1);
                        continue;
                    }
                    sseEmitter.send(sendData);
                } catch (Exception ex) {
                    log.error("SseEmitterServiceImpl[sendMsgToClient]：{}的第{}次消息重推失败", clientId, i + 1, ex);
                    continue;
                }
                log.info("SseEmitterServiceImpl[sendMsgToClient]：{}的第{}次消息重推成功,{}", clientId, i + 1, sseEmitterResultVOList.toString());
                return;
            }
        }
    }

    /**
     * 长链接完成后回调接口(即关闭连接时调用)
     *
     * @param clientId 客户端ID
     * @return java.lang.Runnable
     * @author re
     * @date 2021/12/14
     **/
    private Runnable completionCallBack(String clientId) {
        return () -> {
            log.info("结束连接：{}", clientId);
            removeUser(clientId);
        };
    }

    /**
     * 连接超时时调用
     *
     * @param clientId 客户端ID
     * @return java.lang.Runnable
     * @author re
     * @date 2021/12/14
     **/
    private Runnable timeoutCallBack(String clientId) {
        return () -> {
            log.info("连接超时：{}", clientId);
            removeUser(clientId);
        };
    }

    /**
     * 推送消息异常时，回调方法
     *
     * @param clientId 客户端ID
     * @return java.util.function.Consumer<java.lang.Throwable>
     * @author re
     * @date 2021/12/14
     **/
    private Consumer<Throwable> errorCallBack(String clientId) {
        return throwable -> {
            log.error("SseEmitterServiceImpl[errorCallBack]：连接异常,客户端ID:{}", clientId);

            // 推送消息失败后，每隔10s推送一次，推送5次
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10000);
                    SseEmitter sseEmitter = sseCache.get(clientId);
                    if (sseEmitter == null) {
                        log.error("SseEmitterServiceImpl[errorCallBack]：第{}次消息重推失败,未获取到 {} 对应的长链接", i + 1, clientId);
                        continue;
                    }
                    sseEmitter.send("失败后重新推送");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 移除用户连接
     *
     * @param clientId 客户端ID
     * @author re
     * @date 2021/12/14
     **/
    private void removeUser(String clientId) {
        sseCache.remove(clientId);
        log.info("SseEmitterServiceImpl[removeUser]:移除用户：{}", clientId);
    }
}