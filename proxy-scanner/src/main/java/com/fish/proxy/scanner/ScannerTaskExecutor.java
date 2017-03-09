package com.fish.proxy.scanner;

/**
 * 任务执行器
 */
public abstract class ScannerTaskExecutor {
    private RequestResultParser requestResultParser;

    abstract ScannerResult excute(ScannerTask task);
}
