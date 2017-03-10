package com.fish.proxy.scanner;

import com.fish.proxy.scanner.component.ScannerResultHandler;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import com.fish.proxy.scanner.component.ScannerTaskFactory;
import com.fish.proxy.scanner.component.impl.BIOScannerTaskExecutor;
import com.fish.proxy.scanner.component.impl.LocalScannerTaskFactory;
import com.fish.proxy.scanner.component.impl.NormalScannerResultHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/9 0009.
 */
public class ProxyScanner {
    private final ScannerTaskFactory scannerTaskFactory;
    private final ScannerTaskExecutor scannerTaskExecutor;
    private final ScannerResultHandler scannerResultHandler;

    private final static ScannerTaskFactory DEFAULT_TASK_FACTORY = new LocalScannerTaskFactory();
    private final static ScannerTaskExecutor DEFAULT_TASK_EXECUTOR = new BIOScannerTaskExecutor();
    private final static ScannerResultHandler DEFAULT_RESULT_HANDLER = new NormalScannerResultHandler();

    public ProxyScanner(){
        scannerResultHandler = DEFAULT_RESULT_HANDLER;
        scannerTaskExecutor = DEFAULT_TASK_EXECUTOR;
        scannerTaskFactory = DEFAULT_TASK_FACTORY;
    }



    public void start(){
        int poolSize = 1000;
        ExecutorService service = Executors.newFixedThreadPool(poolSize);
        for(int i = 0; i < poolSize; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        scannerResultHandler.handle(scannerTaskExecutor.execute(scannerTaskFactory.createTask()));
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new ProxyScanner().start();
    }
}
