package com.fish.proxy.scanner.component;

import com.fish.proxy.scanner.RequestResult;
import com.fish.proxy.scanner.ScannerResult;
import com.fish.proxy.scanner.ScannerTask;

/**
 * 任务执行器
 */
public abstract class ScannerTaskExecutor {
    private RequestResultParser requestResultParser;
    private static final RequestResultParser DEFAULT_PARSER = null;
    public static final String WEBSITE = "https://www.baidu.com";

    protected ScannerTaskExecutor(){
        requestResultParser = DEFAULT_PARSER;
    }
    protected ScannerTaskExecutor(RequestResultParser requestResultParser){
        this.requestResultParser = requestResultParser;
    }
    protected ScannerResult execute(ScannerTask task){
        return requestResultParser.handleRequestResult(task, getData(task));
    }
    protected String getWebsiteUrl(){
        return ScannerTaskExecutor.WEBSITE;
    }
    protected abstract RequestResult getData(ScannerTask task);

}
