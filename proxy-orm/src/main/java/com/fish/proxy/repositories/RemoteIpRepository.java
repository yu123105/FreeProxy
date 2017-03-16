package com.fish.proxy.repositories;


public interface RemoteIpRepository {
    String getCurrentRemoteIp();

    String getNextRemoteIp();
}
