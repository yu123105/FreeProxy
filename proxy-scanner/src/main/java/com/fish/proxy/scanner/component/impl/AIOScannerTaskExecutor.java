package com.fish.proxy.scanner.component.impl;


import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;

public class AIOScannerTaskExecutor extends ScannerTaskExecutor{

    private CloseableHttpAsyncClient httpclient;

    @Override
    public void init() {
        httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        scannerResultHandler = new NormalScannerResultHandler(this);
    }

    public AIOScannerTaskExecutor(){
        init();
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
        }
    }

    @Override
    protected RequestResult getData(ScannerTask task) {
        HttpHost proxy = new HttpHost(task.getIp(), task.getPort());
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        HttpGet request = new HttpGet(getHttpUrl());
        request.setConfig(config);
        HttpGet httpsRequest = new HttpGet(getHttpsUrl());
        httpsRequest.setConfig(config);
        httpclient.execute(request,scannerResultHandler);
        incrementTaskCount();
        httpclient.execute(httpsRequest, scannerResultHandler);
        incrementTaskCount();
        return null;
    }
}
