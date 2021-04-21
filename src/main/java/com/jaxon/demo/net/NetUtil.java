package com.jaxon.demo.net;

import com.alibaba.fastjson.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetUtil {


    public static void main(String[] args) throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        while(networkInterfaces.hasMoreElements()) {

            NetworkInterface networkInterface = networkInterfaces.nextElement();

//            System.out.println(JSONObject.toJSONString(networkInterface));

            if(networkInterface.isLoopback()||networkInterface.isVirtual()||!networkInterface.isUp()||networkInterface.getDisplayName().contains("VM")){
                //继续下次循环
                continue;
            }

            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while(inetAddresses.hasMoreElements()){
                InetAddress inetAddress = inetAddresses.nextElement();
                if(inetAddress != null && inetAddress instanceof Inet4Address){
                    System.out.println(JSONObject.toJSONString(inetAddress));
                }
            }


        }
    }
}
