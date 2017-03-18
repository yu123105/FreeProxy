package com.fish.proxy.web;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProxyController {

    @RequestMapping("/message")
    @ResponseBody
    public String proxyVerify(HttpServletRequest request){
        Enumeration<String> names = request.getHeaderNames();
        Map<String, String> heads = new HashMap<>();
        Map<String, String[]> params = request.getParameterMap();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            String value = request.getHeader(name);
            heads.put(name, value);
        }
        heads.put("remote-address", request.getRemoteAddr());

        String headStr = JSON.toJSONString(heads);
        String paramStr = JSON.toJSONString(params);
        return compositeHtml(headStr, paramStr);
    }
    @RequestMapping("real-ip")
    @ResponseBody
    public String realIp(HttpServletRequest request){
        return request.getRemoteAddr();
    }
    private String compositeHtml(String heads, String params){
        String html = "<html>";
        html +="<head>";
        html +="</head>";
        html +="<body>";
        html +="{";
        html +="heads:" + heads + ",";
        html +="params:" + params ;
        html +="}";
        html +="</body>";
        html +="</html>";
        return html;
    }
}
