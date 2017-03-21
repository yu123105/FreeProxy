package com.fish.proxy.utils;


public class IpOperations {
    public final static String DEFAULT_MIN_IP= "0.0.0.0";

    public enum Index{
        ONE,TWO,THREE,FOUR
    }

    public static final Index INDEX = Index.TWO;

    public static final Integer STEP = 50;

    public static Boolean isIpStrValid(String ip){
        if(ip == null){
            return false;
        }
        if(ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
            String s[] = ip.split("\\.");
            return Integer.parseInt(s[0])<=255 && Integer.parseInt(s[1])<=255 && Integer.parseInt(s[2])<=255 &&Integer.parseInt(s[3])<=255;
        }
        return false;
    }

    public static Integer[] strIp2IntArray(String ip){
        Integer[] data = new Integer[4];
        String s[] = ip.split("\\.");
        data[0] = Integer.parseInt(s[0]);
        data[1] = Integer.parseInt(s[1]);
        data[2] = Integer.parseInt(s[2]);
        data[3] = Integer.parseInt(s[3]);
        return data;
    }

    public static String nextIp(String ip, Index index, Integer step){
        if(!isIpStrValid(ip)){
            return DEFAULT_MIN_IP;
        }
        Integer[] data = strIp2IntArray(ip);
        return nextIpByData(data, index, step);

    }
    private static String nextIpByData(Integer[] data, Index index, Integer step){
        index = index == null ? Index.ONE : index;
        if(index.equals(Index.ONE)){
            data[3] += step;
        }
        if(data[3] > 255){
            data[3] %= 255;
            data[2]++;
        }
        if(index.equals(Index.TWO)){
            data[2] += step;
        }
        if(data[2] > 255){
            data[2] %= 255;
            data[1]++;
        }
        if(index.equals(Index.THREE)){
            data[1] += step;
        }
        if(data[1] > 255){
            data[1] %= 255;
            data[0]++;
        }
        if(index.equals(Index.FOUR)){
            data[0] += step;
        }
        data[0] %= 255;
        return compositeData(data);
    }
    public static String compositeData(Integer data[]){
        if(data == null || data.length < 4){
            return null;
        }
        return data[0] + "." + data[1] + "." + data[2] + "." + data[3];
    }
}
