package com.fish.proxy.scanner.utils;


import sun.net.util.IPAddressUtil;

import java.util.Arrays;

public class IpIterator {
    private Integer[] minIpData;
    private Integer[] maxIpData;
    private Integer[] currentIpData;
    private IpIterator(){}
    public IpIterator(String minIp, String maxIp){
        if(isIpStrValid(minIp) && isIpStrValid(maxIp)){
            minIpData = strIp2IntArray(minIp);
            maxIpData = strIp2IntArray(maxIp);
            currentIpData = Arrays.copyOf(minIpData, minIpData.length);
        }
    }
    public String getNextIp(){
        currentIpData[3]++;
        if(currentIpData[3] > 255){
            currentIpData[3] %= 255;
            currentIpData[2]++;
        }
        if(currentIpData[2] > 255){
            currentIpData[2] %= 255;
            currentIpData[1]++;
        }
        if(currentIpData[1] > 255){
            currentIpData[1] %= 255;
            currentIpData[0]++;
        }
        return currentIpData[0] + "." + currentIpData[1] + "." + currentIpData[2] + "." + currentIpData[3];
    }
    public String getCurrentIp(){
        return currentIpData[0] + "." + currentIpData[1] + "." + currentIpData[2] + "." + currentIpData[3];
    }
    public Boolean hasNextIp(){
        return (currentIpData[0] < maxIpData[0] || currentIpData[1] < maxIpData[1] || currentIpData[2] < maxIpData[2] || currentIpData[3] < maxIpData[3]);
    }
    private Integer[] strIp2IntArray(String ip){
        Integer[] data = new Integer[4];
        String s[] = ip.split("\\.");
        data[0] = Integer.parseInt(s[0]);
        data[1] = Integer.parseInt(s[1]);
        data[2] = Integer.parseInt(s[2]);
        data[3] = Integer.parseInt(s[3]);
        return data;
    }
    private Boolean isIpStrValid(String ip){
        if(ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
            String s[] = ip.split("\\.");
             return Integer.parseInt(s[0])<255 && Integer.parseInt(s[1])<255 && Integer.parseInt(s[2])<255 &&Integer.parseInt(s[3])<255;
        }
        return false;
    }
}
