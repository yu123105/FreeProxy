package com.fish.proxy.scanner.component;


import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.impl.DefaultRequestResultParser;
import org.apache.http.HttpResponse;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务执行器
 */
public abstract class ScannerTaskExecutor {
    protected RequestResultParser requestResultParser;
    protected ScannerTaskFactory scannerTaskFactory;
    protected ScannerResultHandler<HttpResponse> scannerResultHandler;
    protected volatile boolean stopped = false;
    protected AtomicInteger runningTaskCount =  new AtomicInteger(0);
    private static final RequestResultParser DEFAULT_PARSER = new DefaultRequestResultParser();
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final String HOST = "www.baidu.com";

    public abstract void init();

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }


    public Integer incrementTaskCount(){
        return runningTaskCount.incrementAndGet();
    }
    public Integer decrementTaskCount(){
        return runningTaskCount.decrementAndGet();
    }
    public Integer getRunningTaskCount(){
        return runningTaskCount.get();
    }
    protected ScannerTaskExecutor(){
        requestResultParser = DEFAULT_PARSER;
    }
    protected ScannerTaskExecutor(RequestResultParser requestResultParser){
        this.requestResultParser = requestResultParser;
    }
    public void executeTaskWithFactory(){

    }
    public ScannerResult executeTask(ScannerTask task){
        throw new UnsupportedOperationException();
    }
    public void asyncExecuteTask(ScannerTask task){
        throw new UnsupportedOperationException();
    }
    public abstract void stop();
    protected String getHttpUrl(){
        return HTTP + HOST;
    }
    protected String getHttpsUrl(){
        return HTTPS + HOST;
    }
    protected abstract RequestResult getData(ScannerTask task);

}
