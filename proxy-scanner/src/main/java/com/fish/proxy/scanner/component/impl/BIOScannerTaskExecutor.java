package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;

import java.io.IOException;
import java.net.*;

public class BIOScannerTaskExecutor extends ScannerTaskExecutor {

    @Override
    protected RequestResult getData(ScannerTask task) {
        try {
            URL url = new URL(getWebsiteUrl());
            InetSocketAddress address = new InetSocketAddress(task.getIp(), task.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(proxy);
            //HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(5000);
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                return new RequestResult(connection.getResponseCode(), null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail:" + task.getIp() + ":" + task.getPort());
            return new RequestResult(HttpURLConnection.HTTP_BAD_REQUEST, null);
        }

        return new RequestResult(HttpURLConnection.HTTP_BAD_REQUEST, null);
    }

    public static void main(String[] args) {
        new BIOScannerTaskExecutor().getData(new ScannerTask("120.26.163.155", 1080));
    }
}
