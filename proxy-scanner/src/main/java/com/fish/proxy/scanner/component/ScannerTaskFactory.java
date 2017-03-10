package com.fish.proxy.scanner.component;

import com.fish.proxy.scanner.ScannerTask;

/**
 * Created by Administrator on 2017/3/9 0009.
 */
public interface ScannerTaskFactory {
    ScannerTask getTask();
    Boolean hasMoreTask();
    ScannerTask createTask();
}
