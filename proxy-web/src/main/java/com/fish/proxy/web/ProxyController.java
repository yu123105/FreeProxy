package com.fish.proxy.web;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fish.proxy.bean.enums.AnonymousLevel;
import com.fish.proxy.bean.enums.ProxyStatus;
import com.fish.proxy.bean.enums.ProxySupportProtocol;
import com.fish.proxy.bean.enums.ProxyType;
import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.repositories.ScannerResultRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProxyController {

    @Autowired
    private ScannerResultRepository scannerResultRepository;

    @RequestMapping("/proxy-record")
    @ResponseBody
    public void proxyVerify(HttpServletRequest request,
                            @RequestParam String localIp,
                            @RequestParam String proxyIp,
                            @RequestParam Integer port,
                            @RequestParam Long time){

        String requestRemoteIp = request.getRemoteAddr();

        if(localIp.equals(requestRemoteIp)){
            return;
        }

        ScannerResult result = scannerResultRepository.findByIp(proxyIp);
        result = result == null ? new ScannerResult() : result;

        String via = request.getHeader("via");

        String xForwarded = request.getHeader("x-forwarded-for");

        if(StringUtils.isNotBlank(xForwarded) || StringUtils.isNotBlank(via)){
            if(xForwarded.equals(localIp)){
                //透明代理
                result.setAnonymous(AnonymousLevel.TRANSPARENT);
            } else if (xForwarded.equals(proxyIp)){
                //普通匿名代理
                result.setAnonymous(AnonymousLevel.ANONYOUSLEVEL);
            } else{
                //欺骗代理
                result.setAnonymous(AnonymousLevel.DISTORING);
            }
        }else {
            if(requestRemoteIp.equals(proxyIp) ){
                //高匿名
                result.setAnonymous(AnonymousLevel.HIGH_ANONYOUSLEVEL);
            }else {
                //多重代理
                result.setAnonymous(AnonymousLevel.DISTORING);
            }
        }
        result.setProxyType(ProxyType.FREE);
        result.setTask(new ScannerTask(proxyIp, port));
        result.setDelayTime(System.currentTimeMillis() - time);
        result.setStatus(ProxyStatus.RUNNING);
        result.setUpdateTime(new Date());
        String protocol = request.getScheme();
        if(protocol.equals("https")){
            result.setSupportType(ProxySupportProtocol.HTTPS);
        }else {
            if(protocol.equals("http") && !ProxySupportProtocol.HTTPS.equals(result.getSupportType())){
                result.setSupportType(ProxySupportProtocol.HTTP);
            }
        }
        scannerResultRepository.save(result);


    }
    @RequestMapping("/server-message")
    @ResponseBody
    public Map<String, Object> realIp(HttpServletRequest request){
        Map<String, Object> ret = new HashMap<>();
        ret.put("localIp", request.getRemoteAddr());
        ret.put("time", System.currentTimeMillis());
        return ret;
    }
}
