package com.fish.proxy.scanner.component;


import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.impl.DefaultRequestResultParser;

/**
 * 任务执行器
 */
public abstract class ScannerTaskExecutor {
    private RequestResultParser requestResultParser;
    private static final RequestResultParser DEFAULT_PARSER = new DefaultRequestResultParser();
    public static final String WEBSITE = "http://www.chapaiming.com";

    protected ScannerTaskExecutor(){
        requestResultParser = DEFAULT_PARSER;
    }
    protected ScannerTaskExecutor(RequestResultParser requestResultParser){
        this.requestResultParser = requestResultParser;
    }
    public ScannerResult execute(ScannerTask task){
        return requestResultParser.handleRequestResult(task, getData(task));
    }
    protected String getWebsiteUrl(){
        return ScannerTaskExecutor.WEBSITE;
    }
    protected abstract RequestResult getData(ScannerTask task);

}
