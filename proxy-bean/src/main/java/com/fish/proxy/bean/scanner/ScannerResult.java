package com.fish.proxy.bean.scanner;


import com.fish.proxy.bean.enums.AnonymousLevel;
import com.fish.proxy.bean.enums.ProxyStatus;
import com.fish.proxy.bean.enums.ProxySupportProtocol;
import com.fish.proxy.bean.enums.ProxyType;
import com.fish.proxy.bean.model.BaseModel;

import javax.persistence.*;
import java.util.Date;

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

    private Date updateTime;

    private ScannerTask task;

    @Enumerated(EnumType.ORDINAL)
    private ProxySupportProtocol supportType;

    @Enumerated(EnumType.ORDINAL)
    private AnonymousLevel anonymous;

    @Enumerated(EnumType.ORDINAL)
    private ProxyStatus status;

    @Enumerated(EnumType.ORDINAL)
    private ProxyType proxyType;

    public ScannerResult(){}


    public ScannerResult(Long delayTime, ProxySupportProtocol supportType, AnonymousLevel anonymous, ScannerTask task, ProxyStatus status, Date updateTime, ProxyType proxyType) {
        this.delayTime = delayTime;
        this.supportType = supportType;
        this.anonymous = anonymous;
        this.task = task;
        this.status = status;
        this.updateTime = updateTime;
        this.proxyType = proxyType;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    public ProxySupportProtocol getSupportType() {
        return supportType;
    }

    public void setSupportType(ProxySupportProtocol supportType) {
        this.supportType = supportType;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public ScannerTask getTask() {
        return task;
    }

    public void setTask(ScannerTask task) {
        this.task = task;
    }

    public AnonymousLevel getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(AnonymousLevel anonymous) {
        this.anonymous = anonymous;
    }

    public ProxyStatus getStatus() {
        return status;
    }

    public void setStatus(ProxyStatus status) {
        this.status = status;
    }
}
