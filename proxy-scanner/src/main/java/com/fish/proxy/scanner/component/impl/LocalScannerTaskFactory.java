package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.scanner.bean.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/3/9.
 */
public class LocalScannerTaskFactory implements ScannerTaskFactory {
    private static Integer portPoint = 0;

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

        }finally {
            lock.unlock();
        }
        return null;
    }
}
