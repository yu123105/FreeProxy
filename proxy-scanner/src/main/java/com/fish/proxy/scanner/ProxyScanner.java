package com.fish.proxy.scanner;

import com.fish.proxy.scanner.component.ScannerResultHandler;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import com.fish.proxy.scanner.component.ScannerTaskFactory;
import com.fish.proxy.scanner.component.impl.BIOScannerTaskExecutor;
import com.fish.proxy.scanner.component.impl.LocalScannerTaskFactory;
import com.fish.proxy.scanner.component.impl.NormalScannerResultHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyScanner {

    public ProxyScanner(){
    }



    public void start(){
        int poolSize = 1000;
        ExecutorService service = Executors.newFixedThreadPool(poolSize);
        for(int i = 0; i < poolSize; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        //scannerTaskExecutor.execute(scannerTaskFactory.createTask());
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new ProxyScanner().start();
    }
}
