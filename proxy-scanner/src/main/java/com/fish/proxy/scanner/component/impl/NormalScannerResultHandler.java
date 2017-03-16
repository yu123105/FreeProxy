package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.scanner.component.ScannerResultHandler;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import org.apache.http.HttpResponse;
import org.omg.CORBA.portable.OutputStream;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Administrator on 2017/3/10.
 */
public class NormalScannerResultHandler implements ScannerResultHandler<HttpResponse> {

    private ScannerTaskExecutor scannerTaskExecutor;

    private NormalScannerResultHandler(){}

    public NormalScannerResultHandler(ScannerTaskExecutor scannerTaskExecutor){
        this.scannerTaskExecutor = scannerTaskExecutor;
    }

    @Override
    public void handle(ScannerResult result) {

    }


    @Override
    public void completed(HttpResponse httpResponse) {
        try {
            InputStream stream = httpResponse.getEntity().getContent();
            String s = "";
            byte[] buffer = new byte[1024];
            while ((stream.read(buffer)) > 0){
                s += new String(buffer, "UTF-8");
            }
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scannerTaskExecutor.decrementTaskCount();
    }

    @Override
    public void failed(Exception e) {
        System.out.println("failed");
        scannerTaskExecutor.decrementTaskCount();
    }

    @Override
    public void cancelled() {
        System.out.println("cancelled");
        scannerTaskExecutor.decrementTaskCount();
    }
}
