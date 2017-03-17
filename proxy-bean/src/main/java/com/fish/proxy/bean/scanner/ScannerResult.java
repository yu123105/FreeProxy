package com.fish.proxy.bean.scanner;


import com.fish.proxy.bean.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 扫描结果
 */
@Entity
public class ScannerResult extends BaseModel{

    private static final long serialVersionUID = -3066430089296652384L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long delayTime;

    private Boolean emberCss;

    private Boolean emberJs;

    private Integer supportType;

    private Integer anonymous;

    private ScannerTask task;

    private Integer status;


    private Integer proxyType;




    protected ScannerResult(){}

    public ScannerResult(Long delayTime, Boolean emberCss, Boolean emberJs, Integer supportType, Integer anonymous, ScannerTask task) {
        this.delayTime = delayTime;
        this.emberCss = emberCss;
        this.emberJs = emberJs;
        this.supportType = supportType;
        this.anonymous = anonymous;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Long delayTime) {
        this.delayTime = delayTime;
    }

    public Boolean getEmberCss() {
        return emberCss;
    }

    public void setEmberCss(Boolean emberCss) {
        this.emberCss = emberCss;
    }

    public Boolean getEmberJs() {
        return emberJs;
    }

    public void setEmberJs(Boolean emberJs) {
        this.emberJs = emberJs;
    }

    public Integer getSupportType() {
        return supportType;
    }

    public void setSupportType(Integer supportType) {
        this.supportType = supportType;
    }

    public Integer getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }


    public ScannerTask getTask() {
        return task;
    }

    public void setTask(ScannerTask task) {
        this.task = task;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProxyType() {
        return proxyType;
    }

    public void setProxyType(Integer proxyType) {
        this.proxyType = proxyType;
    }
}
