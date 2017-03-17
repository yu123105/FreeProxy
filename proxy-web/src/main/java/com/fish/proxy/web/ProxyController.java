package com.fish.proxy.web;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProxyController {

    @RequestMapping("/")
    @ResponseBody
    public String proxyVerify(HttpServletRequest request){
        Enumeration<String> names = request.getHeaderNames();
        Map<String, String> heads = new HashMap<>();
        Map<String, String[]> params = request.getParameterMap();
        ObjectMapper objectMapper = new ObjectMapper();


        while (names.hasMoreElements()){
            String name = names.nextElement();
            String value = request.getHeader(name);
            heads.put(name, value);
        }
        try {
            String headStr = objectMapper.writeValueAsString(heads);
            String paramStr = objectMapper.writeValueAsString(params);
            return compositeHtml(headStr, paramStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
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
