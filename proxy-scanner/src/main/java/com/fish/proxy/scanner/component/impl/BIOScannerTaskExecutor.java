package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.ScannerTaskExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class BIOScannerTaskExecutor extends ScannerTaskExecutor {

    @Override
    public void init() {

    }

    @Override
    public ScannerResult executeTask(ScannerTask task) {
        return requestResultParser.handleRequestResult(task, getData(task));
    }

    @Override
    public void stop() {

    }

    @Override
    protected RequestResult getData(ScannerTask task) {
        try {
            URL url = new URL(getHttpUrl());
            InetSocketAddress address = new InetSocketAddress(task.getIp(), task.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(proxy);
            connection.setConnectTimeout(5000);

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream stream = connection.getInputStream();
                String s = "";
                int i = 0;
                byte[] buffer = new byte[1024];
                while ((i = stream.read(buffer)) > 0){
                    System.out.println(new String(buffer, "UTF-8"));
                }
                System.out.println("ok");
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
        new BIOScannerTaskExecutor().getData(new ScannerTask("207.99.118.74", 8080));
    }
}
