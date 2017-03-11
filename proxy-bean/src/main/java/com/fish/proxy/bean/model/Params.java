package com.fish.proxy.bean.model;

import java.util.HashMap;

public class Params extends HashMap<String, Object>{

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    public Params() {
    }
    
    public Params(int initialCapacity) {
        super(initialCapacity);
    }
    
    
    /**
     * 创建Params实例
     * @param key
     * @param value
     * @return
     */
    public static Params create(String key, Object value) {
        return new Params(1, key, value);
    }
    
    /**
     * 创建Params实例
     * @param key
     * @param value
     * @return
     */
    public static Params create(int initialCapacity, String key, Object value) {
        return new Params(initialCapacity, key, value);
    }

    private Params(int initialCapacity, String key, Object value) {
        super(initialCapacity);
        this.put(key, value);
    }
    
    public Params set(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
