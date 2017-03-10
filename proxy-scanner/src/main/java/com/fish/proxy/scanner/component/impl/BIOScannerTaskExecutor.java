package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.scanner.component.ScannerTaskExecutor;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by Administrator on 2017/3/9.
 */
public class BIOScannerTaskExecutor extends ScannerTaskExecutor {

    @Override
    protected RequestResult getData(ScannerTask task) {
        URL url = new URL(getWebsiteUrl());
        InetSocketAddress address = new InetSocketAddress(task.getIp(), task.getPort());
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection(proxy);
        connection.setConnectTimeout(3);

        return null;
    }
}
