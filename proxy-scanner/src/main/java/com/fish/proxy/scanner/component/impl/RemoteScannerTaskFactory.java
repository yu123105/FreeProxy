package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.repositories.RemoteIpRepository;
import com.fish.proxy.scanner.component.AbstractScannerTaskFactory;
import com.fish.proxy.utils.IpIterator;
import com.fish.proxy.utils.IpOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RemoteScannerTaskFactory extends AbstractScannerTaskFactory {

    @Autowired
    private RemoteIpRepository remoteIpRepositoryImpl;



    @PostConstruct
    public void init(){
        String ip = remoteIpRepositoryImpl.getNextRemoteIp();
        ipIterator = new IpIterator(ip, IpOperations.nextIp(ip, IpOperations.step));
    }

    @Override
    public ScannerTask getTask() {
        ScannerTask task = null;
        if(hasMoreTask()){
            task = createTask();
        }
        if(task == null){
            try {
                lock.lock();
                String ip = remoteIpRepositoryImpl.getNextRemoteIp();
                System.out.println(ip);
                ipIterator = new IpIterator(ip, IpOperations.nextIp(ip, IpOperations.step));
                task = createTask();
            }finally {
                lock.unlock();
            }
        }
        return task;
    }
}
