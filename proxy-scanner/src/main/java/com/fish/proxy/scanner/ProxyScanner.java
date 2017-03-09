package com.fish.proxy.scanner;

import com.fish.proxy.scanner.component.ScannerResultHandler;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import com.fish.proxy.scanner.component.ScannerTaskFactory;

/**
 * Created by Administrator on 2017/3/9 0009.
 */
public class ProxyScanner {
    private final ScannerTaskFactory scannerTaskFactory;
    private final ScannerTaskExecutor scannerTaskExecutor;
    private final ScannerResultHandler scannerResultHandler;

    private final static ScannerTaskFactory DEFAULT_TASK_FACTORY = null;
    private final static ScannerTaskExecutor DEFAULT_TASK_EXECUTOR = null;
    private final static ScannerResultHandler DEFAULT_RESULT_HANDLER = null;

    public ProxyScanner(){
        scannerResultHandler = DEFAULT_RESULT_HANDLER;
        scannerTaskExecutor = DEFAULT_TASK_EXECUTOR;
        scannerTaskFactory = DEFAULT_TASK_FACTORY;
    }



    public void start(){
        while (true){
            scannerResultHandler.handle(scannerTaskExecutor.execute(scannerTaskFactory.createTask()));
        }
    }
}
