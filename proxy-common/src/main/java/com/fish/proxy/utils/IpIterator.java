package com.fish.proxy.utils;


import java.util.Arrays;

public class IpIterator {
    private Integer[] minIpData;
    private Integer[] maxIpData;
    private Integer[] currentIpData;
    private String minIp;
    private String maxIp;
    private IpIterator(){}
    public IpIterator(String minIp, String maxIp){
        if(IpOperations.isIpStrValid(minIp) && IpOperations.isIpStrValid(maxIp)){
            this.minIp = minIp;
            this.maxIp = maxIp;
            minIpData = IpOperations.strIp2IntArray(minIp);
            maxIpData = IpOperations.strIp2IntArray(maxIp);
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
        return IpOperations.compositData(currentIpData);
    }
    public String getCurrentIp(){
        return currentIpData[0] + "." + currentIpData[1] + "." + currentIpData[2] + "." + currentIpData[3];
    }
    public Boolean hasNextIp(){
        return (currentIpData[0] < maxIpData[0] || currentIpData[1] < maxIpData[1] || currentIpData[2] < maxIpData[2] || currentIpData[3] < maxIpData[3]);
    }

    public String getMinIp() {
        return minIp;
    }

    public void setMinIp(String minIp) {
        this.minIp = minIp;
    }

    public String getMaxIp() {
        return maxIp;
    }

    public void setMaxIp(String maxIp) {
        this.maxIp = maxIp;
    }
}
