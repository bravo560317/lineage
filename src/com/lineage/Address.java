package com.lineage;

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.InetAddress;

public class Address
{
    public static void main(String[] args) {
        new IP();
    }
    
    public static void IP() throws IOException {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String hostName = inetAddress.getHostName();
            String canonicalHostName = inetAddress.getCanonicalHostName();
            byte[] address = inetAddress.getAddress();
            int a = 0;
            if (address[3] < 0) {
                a = address[3] + 256;
            }
            String hostAddress = inetAddress.getHostAddress();
            boolean reachable = inetAddress.isReachable(2000);
            System.out.println(inetAddress.toString());
            System.out.println("主机名为：" + hostName);
            System.out.println("此IP地址的完全限定域名：" + canonicalHostName);
            System.out.println("原始IP地址为：" + (int)address[0] + "." + (int)address[1] + "." + (int)address[2] + "." + a);
            System.out.println("IP地址为：" + hostAddress);
            System.out.println("是否能到达此IP地址：" + reachable);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
