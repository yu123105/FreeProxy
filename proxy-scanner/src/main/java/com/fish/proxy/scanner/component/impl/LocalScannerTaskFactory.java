package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskFactory;
import com.fish.proxy.utils.IpIterator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/3/9.
 */
public class LocalScannerTaskFactory implements ScannerTaskFactory {
    private static Integer portPoint = 0;
    private static Integer[] ports = new Integer[]{80,81,82,90,3128,7000,8000,8080,8081,9000};
    private IpIterator ipIterator = new IpIterator("1.12.0.0", "1.15.255.255");
    Lock lock = new ReentrantLock();
    public ScannerTask getTask() {
        if(hasMoreTask()){
            return createTask();
        }
        return null;
    }

    public Boolean hasMoreTask() {
        return null;
    }

    public ScannerTask createTask() {
        try {
            lock.lock();
            if(!hasMoreTask()){
                return null;
            }
            String ip = ipIterator.getCurrentIp();
            if(portPoint > ports.length - 1){
                portPoint %= (ports.length - 1);
                ip = ipIterator.getNextIp();
            }
            return new ScannerTask(ip, ports[portPoint]);
        }finally {
            lock.unlock();
        }
    }
}
