package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.AbstractScannerTaskFactory;

/**
 * Created by Administrator on 2017/3/9.
 */
public class LocalScannerTaskFactory extends AbstractScannerTaskFactory {

     public ScannerTask getTask() {
        if(hasMoreTask()){
            return createTask();
        }
        return null;
    }
}
