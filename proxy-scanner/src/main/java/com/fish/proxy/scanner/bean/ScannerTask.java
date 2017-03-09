package com.fish.proxy.scanner.bean;

import java.io.Serializable;

/**
 * 扫描端口任务类
 */
public class ScannerTask implements Serializable {
    private static final long serialVersionUID = 7582052123118639548L;
    private String ip;
    private Integer port;

    public ScannerTask(String ip, Integer port){
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
