package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.scanner.bean.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskFactory;
import com.fish.proxy.scanner.utils.IpIterator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/3/9.
 */
public class LocalScannerTaskFactory implements ScannerTaskFactory {
    private static Integer portPoint = 0;
    private static Integer[] ports = new Integer[]{80, 8090, 8080};
    private IpIterator ipIterator = new IpIterator("", "");
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
