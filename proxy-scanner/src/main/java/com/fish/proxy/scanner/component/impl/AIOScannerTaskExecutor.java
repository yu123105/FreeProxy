package com.fish.proxy.scanner.component.impl;


import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import com.fish.proxy.scanner.component.ScannerTaskFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
@Component
public class AIOScannerTaskExecutor extends ScannerTaskExecutor{

    private CloseableHttpAsyncClient httpclient;

    private CountDownLatch stopLatch;

    @Override
    public void init() {
        getServerMessage();
        httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        scannerResultHandler = new NormalScannerResultHandler(this);
    }

    @Autowired
    public void setScannerTaskFactory(ScannerTaskFactory remoteScannerTaskFactory){
        this.scannerTaskFactory = remoteScannerTaskFactory;
    }
    public AIOScannerTaskExecutor(){
        init();
    }

    @Override
    public void executeTaskWithFactory(){
        stopLatch = new CountDownLatch(1);
        while (!isStopped()){
            asyncExecuteTask(scannerTaskFactory.getTask());
        }
        try {
            stopLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void asyncExecuteTask(ScannerTask task) {
        getData(task);
    }

    @Override
    public void stop() {
        this.stopped = true;
        while (getRunningTaskCount() != 0){
            try {
                Thread.currentThread().sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            stopLatch.countDown();
        }
    }

    @Override
    protected RequestResult getData(ScannerTask task) {
        HttpHost proxy = new HttpHost(task.getIp(), task.getPort());
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(5000)
                .build();

        HttpGet request = new HttpGet(getHttpUrl(task.getIp(), task.getPort()));
        request.setConfig(config);
        HttpGet httpsRequest = new HttpGet(getHttpsUrl(task.getIp(), task.getPort()));
        httpsRequest.setConfig(config);
        httpclient.execute(request,scannerResultHandler);
        incrementTaskCount();
        httpclient.execute(httpsRequest, scannerResultHandler);
        incrementTaskCount();
        return null;
    }

    public static void main(String[] args) {
        new AIOScannerTaskExecutor().getData(new ScannerTask("207.99.118.74", 8080));
    }
}
