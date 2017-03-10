package com.fish.proxy.bean.scanner;

/**
 * 扫描结果
 */
public class ScannerResult {
    private ScannerTask task;
    private RequestResult requestResult;

    public ScannerResult(ScannerTask task, RequestResult requestResult) {
        this.task = task;
        this.requestResult = requestResult;
    }

    public Integer getResponseCode(){
        return requestResult.getStatus();
    }

    public ScannerTask getTask() {
        return task;
    }

    public void setTask(ScannerTask task) {
        this.task = task;
    }

    public RequestResult getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }
}
