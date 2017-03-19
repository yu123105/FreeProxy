package com.fish.proxy.scanner.component;


import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskFactory;
import com.fish.proxy.utils.IpIterator;
import com.fish.proxy.utils.IpOperations;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractScannerTaskFactory implements ScannerTaskFactory{

    private static Integer portPoint = 0;
    private static Integer[] ports = new Integer[]{80,81,82,90,3128,7000,8000,8080,8081,9000};
    protected IpIterator ipIterator;
    protected Lock lock = new ReentrantLock();

    public abstract ScannerTask getTask();

    @Override
    public String getCurrentTaskIP(){
        return ipIterator.getCurrentIp();
    }
    @Override
    public String getMinTaskIP(){
        return ipIterator.getMinIp();
    }
    @Override
    public String getMaxTaskIP(){
        return ipIterator.getMaxIp();
    }

    @Override
    public Boolean hasMoreTask() {
        return (ipIterator.hasNextIp() || portPoint < ports.length);
    }

    @Override
    public ScannerTask createTask() {
        try {
            lock.lock();
            if (!hasMoreTask()){
                return null;
            }
            String ip = ipIterator.getCurrentIp();
            if(portPoint > ports.length - 1){
                portPoint %= (ports.length - 1);
                ip = ipIterator.getNextIp();
            }
            ScannerTask task = new ScannerTask(ip, ports[portPoint]);
            System.out.println(ip + ":" + ports[portPoint]);
            portPoint++;
            return task;
        } finally {
            lock.unlock();
        }
    }
}
