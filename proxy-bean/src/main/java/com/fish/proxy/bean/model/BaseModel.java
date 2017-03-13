package com.fish.proxy.bean.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 数据模型基类
 */
public abstract class BaseModel implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
