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
            connection.setConnectTimeout(3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
