package com.fish.proxy.bean.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 数据模型基类
 */
public abstract class BaseModel implements Serializable {

    /** serialVersionUID */
//    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
