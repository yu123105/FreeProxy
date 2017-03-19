package com.fish.proxy.scanner.component;


import com.alibaba.fastjson.JSONObject;
import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务执行器
 */
public abstract class ScannerTaskExecutor {
    protected ScannerTaskFactory scannerTaskFactory;
    protected ScannerResultHandler<HttpResponse> scannerResultHandler;
    protected volatile boolean stopped = false;
    protected AtomicInteger runningTaskCount =  new AtomicInteger(0);
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String HOST = "23.83.238.104";
    //private static final String HOST = "127.0.0.1";
    private static final String PATH = "/proxy-record";
    private static final String SERVER_MESSAGE_PATH = "/server-message";
    private static final Integer PORT = 8080;
    private static Long serverTime;
    private static Long diff;
    private static String localIp;

    public abstract void init();

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }


    public Integer incrementTaskCount(){
        System.out.println("-------------------------------------increment--------------------------------------" + runningTaskCount.get());
        return runningTaskCount.incrementAndGet();
    }
    public Integer decrementTaskCount(){
        System.out.println("-------------------------------------decrement--------------------------------------" + runningTaskCount.get());
        return runningTaskCount.decrementAndGet();
    }
    public Integer getRunningTaskCount(){
        return runningTaskCount.get();
    }
    protected ScannerTaskExecutor(){
    }
    public void executeTaskWithFactory(){

    }
    public void executeTask(ScannerTask task){
        throw new UnsupportedOperationException();
    }
    public void asyncExecuteTask(ScannerTask task){
        throw new UnsupportedOperationException();
    }
    public abstract void stop();
    protected URI getHttpUrl(String proxyIp, Integer port){
        URIBuilder builder = new URIBuilder();
        try {
            return builder.setScheme(HTTP).setHost(HOST).setPort(PORT).setPath(PATH)
                    .setParameter("time", System.currentTimeMillis() + diff + "")
                    .setParameter("localIp", localIp)
                    .setParameter("proxyIp",proxyIp)
                    .setParameter("port", port+"").build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected URI getHttpsUrl(String proxyIp, Integer port){
        URIBuilder builder = new URIBuilder();
        try {
            return builder.setScheme(HTTPS).setHost(HOST).setPort(PORT).setPath(PATH)
                    .setParameter("time", System.currentTimeMillis() + diff +"")
                    .setParameter("localIp", localIp)
                    .setParameter("proxyIp",proxyIp)
                    .setParameter("port", port+"").build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected URI getServerMessageUrl(){
        URIBuilder builder = new URIBuilder();
        try {
            return builder.setScheme(HTTP).setHost(HOST).setPort(PORT).setPath(SERVER_MESSAGE_PATH).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void getServerMessage(){
        try {
            URL url = getServerMessageUrl().toURL();
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(5000);

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Long time = System.currentTimeMillis();
                InputStream stream = connection.getInputStream();
                StringBuffer   out   =   new   StringBuffer();
                byte[] buffer = new byte[2048];
                for(int i; (i = stream.read(buffer)) != -1; ){
                    out.append(new String(buffer, 0, i, "UTF-8"));
                }
               String s = out.toString();
                Map map = (Map)JSONObject.parse(s);
                serverTime = (Long)map.get("time");
                diff = serverTime - time;
                localIp = (String)map.get("localIp");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    protected abstract RequestResult getData(ScannerTask task);

}
