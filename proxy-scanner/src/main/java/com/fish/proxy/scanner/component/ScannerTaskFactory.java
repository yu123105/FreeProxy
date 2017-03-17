package com.fish.proxy.scanner.component;


import com.fish.proxy.bean.scanner.ScannerTask;

public interface ScannerTaskFactory {
    ScannerTask getTask();
    Boolean hasMoreTask();
    ScannerTask createTask();
    String getCurrentTaskIP();
    String getMinTaskIP();
    String getMaxTaskIP();
}
