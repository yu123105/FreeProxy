package com.fish.proxy.repositories.impl;

import com.fish.proxy.repositories.RedisLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisLockRepositoryImpl implements RedisLockRepository {

    private static final String KEY = "REDIS_LOCK";
    private static final String VALUE = "1";
    private static final Integer TRY_TIMES = 3;

    private RedisTemplate<String, String> redisTemplate;
    private ListOperations<String, String> listOps;

    @Autowired
    private RedisLockRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        listOps = redisTemplate.opsForList();
    }

    @Override
    public void getLock() throws IOException {
        Integer time = 1;
        while (listOps.leftPop(KEY, 60, TimeUnit.SECONDS) == null && time < TRY_TIMES){
            time++;
        }
        if(time > TRY_TIMES){
            throw new IOException();
        }
    }

    @Override
    public void releaseLock() {
        listOps.leftPush(KEY, VALUE);
    }
}
