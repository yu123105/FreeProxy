package com.fish.proxy.scanner.component.impl;


import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import com.fish.proxy.scanner.component.ScannerTaskFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Component
public class AIOScannerTaskExecutor extends ScannerTaskExecutor{

    private CloseableHttpAsyncClient httpclient;

    private CountDownLatch stopLatch;
    private ConnectingIOReactor ioReactor;
    private PoolingNHttpClientConnectionManager cm;
    @Override
    public void init() {
        try {
            getServerMessage();
            httpclient = HttpAsyncClients.createDefault();
            httpclient.start();
            scannerResultHandler = new NormalScannerResultHandler(this);
            ioReactor = new DefaultConnectingIOReactor();
            cm = new PoolingNHttpClientConnectionManager(ioReactor);
            cm.setMaxTotal(100);
            httpclient = HttpAsyncClients.custom().setConnectionManager(cm).build();
            httpclient.start();
        } catch (IOReactorException e) {
            e.printStackTrace();
        }

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
            if(getRunningTaskCount() > 50000){
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(6000));
            }
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
        final ScannerTaskExecutor scannerTaskExecutor = this;
        httpclient.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                scannerTaskExecutor.decrementTaskCount();
            }

            @Override
            public void failed(Exception e) {
                scannerTaskExecutor.decrementTaskCount();
            }

            @Override
            public void cancelled() {
                scannerTaskExecutor.decrementTaskCount();
            }
        });
        incrementTaskCount();
        httpclient.execute(httpsRequest, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                scannerTaskExecutor.decrementTaskCount();
            }

            @Override
            public void failed(Exception e) {
                scannerTaskExecutor.decrementTaskCount();
            }

            @Override
            public void cancelled() {
                scannerTaskExecutor.decrementTaskCount();
            }
        });
        incrementTaskCount();
        return null;
    }

    /*public static void main(String[] args) {
        new AIOScannerTaskExecutor().getData(new ScannerTask("207.99.118.74", 8080));
    }*/
}
