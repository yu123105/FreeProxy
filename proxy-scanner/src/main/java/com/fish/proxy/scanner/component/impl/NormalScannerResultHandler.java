package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.scanner.component.ScannerResultHandler;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import org.apache.http.HttpResponse;
import org.omg.CORBA.portable.OutputStream;

import java.io.IOException;
import java.io.InputStream;

public class NormalScannerResultHandler implements ScannerResultHandler<HttpResponse> {

    private ScannerTaskExecutor scannerTaskExecutor;

    private NormalScannerResultHandler(){}

    public NormalScannerResultHandler(ScannerTaskExecutor scannerTaskExecutor){
        this.scannerTaskExecutor = scannerTaskExecutor;
    }


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
}
