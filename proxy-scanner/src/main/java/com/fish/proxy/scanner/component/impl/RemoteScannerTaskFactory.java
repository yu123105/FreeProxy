package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.repositories.RemoteIpRepository;
import com.fish.proxy.utils.IpIterator;
import com.fish.proxy.utils.IpOperations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class RemoteScannerTaskFactory extends AbstractScannerTaskFactory {

    @Autowired
    private RemoteIpRepository remoteIpRepositoryImpl;

    @PostConstruct
    public void init(){
        String ip = remoteIpRepositoryImpl.getNextRemoteIp();
        ipIterator = new IpIterator(ip, IpOperations.nextIp(ip, IpOperations.Step.TWO));
    }

    @Override
    public ScannerTask getTask() {
        if(hasMoreTask()){
            return createTask();
        }else {
            String ip = remoteIpRepositoryImpl.getNextRemoteIp();
            ipIterator = new IpIterator(ip, IpOperations.nextIp(ip, IpOperations.Step.TWO));
            return createTask();
        }
    }
}
