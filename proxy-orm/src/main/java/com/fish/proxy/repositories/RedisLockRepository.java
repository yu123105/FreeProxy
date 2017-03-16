package com.fish.proxy.repositories;

import org.springframework.data.repository.CrudRepository;

import java.io.IOException;


public interface RedisLockRepository{
    void getLock() throws IOException;
    void releaseLock();
}
