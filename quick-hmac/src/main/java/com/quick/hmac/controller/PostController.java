package com.quick.hmac.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * https://www.cnblogs.com/softidea/p/5745369.html
 */
@RestController
@RequestMapping("post")
public class PostController {

    /**
     * application/x-www-form-urlencoded
     *
     * @return
     */
    @RequestMapping("formUrlencoded")
    public Object formUrlencoded(HttpServletRequest request) {
        printReq(request);
        return null;
    }


    /**
     * multipart/form-data
     *
     * @return
     */
    @RequestMapping("formData")
    public Object formData(HttpServletRequest request) {
        printReq(request);

        return null;
    }

    /**
     * application/json
     *
     * @return
     */
    @RequestMapping("bodyJson")
    public Object bodyJson(HttpServletRequest request) {
        printReq(request);

        return null;
    }

    /**
     * text/xml
     *
     * @return
     */
    @RequestMapping("textXml")
    public Object textXml(HttpServletRequest request) {

        printReq(request);
        return null;
    }

    private void printReq(HttpServletRequest request) {
        //获取请求头信息
        Enumeration headerNames = request.getHeaderNames();
        //使用循环遍历请求头，并通过getHeader()方法获取一个指定名称的头字段
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            System.out.println(headerName + " : " + request.getHeader(headerName));
        }
        System.out.println("============================================================================================");
        //获取请求行的相关信息
        System.out.println("getMethod:" + request.getMethod());
        System.out.println("getQueryString:" + request.getQueryString());
        System.out.println("getProtocol:" + request.getProtocol());
        System.out.println("getContextPath" + request.getContextPath());
        System.out.println("getPathInfo:" + request.getPathInfo());
        System.out.println("getPathTranslated:" + request.getPathTranslated());
        System.out.println("getServletPath:" + request.getServletPath());
        System.out.println("getRemoteAddr:" + request.getRemoteAddr());
        System.out.println("getRemoteHost:" + request.getRemoteHost());
        System.out.println("getRemotePort:" + request.getRemotePort());
        System.out.println("getLocalAddr:" + request.getLocalAddr());
        System.out.println("getLocalName:" + request.getLocalName());
        System.out.println("getLocalPort:" + request.getLocalPort());
        System.out.println("getServerName:" + request.getServerName());
        System.out.println("getServerPort:" + request.getServerPort());
        System.out.println("getScheme:" + request.getScheme());
        System.out.println("getRequestURL:" + request.getRequestURL());


        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                System.out.println(en + "===>" + value);
            }
        }
//        StringBuffer jb = new StringBuffer();
//        String line = null;
//        try {
//            BufferedReader reader = request.getReader();
//            while ((line = reader.readLine()) != null)
//                jb.append(line);
//        } catch (Exception e) { /*report an error*/ }
//
//        try {
//            System.out.println(JSONUtil.toJsonPrettyStr(jb.toString()));
//        } catch (Exception e) {
//            // crash and burn
//            e.printStackTrace();
//        }
    }
}
