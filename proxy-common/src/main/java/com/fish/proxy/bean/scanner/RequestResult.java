package com.fish.proxy.bean.scanner;

/**
 * Created by Administrator on 2017/3/9.
 */
public class RequestResult {
    private Integer status;
    private String content;

    public RequestResult(Integer status, String content){
        this.status = status;
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
