package com.quick.controller;

import com.quick.exception.BusinessException;
import com.quick.service.SseEmitterService;
import com.quick.vo.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SseEmitterController {

    @Autowired
    private SseEmitterService sseEmitterService;




    /**
     * 创建SSE长链接
     *
     * @param clientId   客户端唯一ID(如果为空，则由后端生成并返回给前端)
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     * @author re
     * @date 2021/12/12
     **/
    @CrossOrigin //如果nginx做了跨域处理，此处可去掉
    @GetMapping("/CreateSseConnect")
    public SseEmitter createSseConnect(@RequestParam(name = "clientId", required = false) String clientId) throws BusinessException {
        return sseEmitterService.createSseConnect(clientId);
    }

    /**
     * 关闭SSE连接
     *
     * @param clientId 客户端ID
     * @author re
     * @date 2021/12/13
     **/
    @GetMapping("/CloseSseConnect")
    public String closeSseConnect(String clientId) {
        sseEmitterService.closeSseConnect(clientId);
        return "success";
    }


    @PostMapping( "/chat/stream")
    public SseEmitter chatStream(@RequestBody ChatRequest request) throws Exception {
        return sseEmitterService.streamChat(request);
    }

}