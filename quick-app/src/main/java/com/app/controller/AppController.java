package com.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController("index")
public class AppController {

    @RequestMapping
    public Object hello(HttpServletRequest request) {
        String remoteHost = request.getRemoteHost();
        String html = "<h3>Hello " + UUID.randomUUID().toString() + "!</h3> " +
                "<b>Hostname:</b> " + remoteHost + "<br/> ";

        return html;
    }
}
