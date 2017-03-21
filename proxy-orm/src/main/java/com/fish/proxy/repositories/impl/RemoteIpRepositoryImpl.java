package com.fish.proxy.repositories.impl;


import com.fish.proxy.repositories.RedisLockRepository;
import com.fish.proxy.repositories.RemoteIpRepository;
import com.fish.proxy.utils.IpOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Repository
public class RemoteIpRepositoryImpl implements RemoteIpRepository{

    private static final String KEY = "REMOTE_IP";

    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOps;

    @Autowired
    RedisLockRepository redisLockRepository;

    @Autowired
    private RemoteIpRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        valueOps = redisTemplate.opsForValue();
    }

    @Override
    public String getCurrentRemoteIp() {
        return valueOps.get(KEY);
    }

    @Override
    public String getNextRemoteIp() {
        try {
            redisLockRepository.getLock();
            String currentIp = getCurrentRemoteIp();
            String nextIp = IpOperations.nextIp(currentIp, IpOperations.INDEX, IpOperations.STEP);
            valueOps.set(KEY, nextIp);
            return nextIp;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            redisLockRepository.releaseLock();
        }
        return null;
    }
}
