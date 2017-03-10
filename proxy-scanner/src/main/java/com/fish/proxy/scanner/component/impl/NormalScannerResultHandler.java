package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.scanner.component.ScannerResultHandler;

import java.net.HttpURLConnection;

/**
 * Created by Administrator on 2017/3/10.
 */
public class NormalScannerResultHandler implements ScannerResultHandler {
    @Override
    public void handle(ScannerResult result) {
        if(result.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("success:" + result.getTask().getIp() + ":" + result.getTask().getPort());
        }
    }
}
