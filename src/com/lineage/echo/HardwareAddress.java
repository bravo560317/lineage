package com.lineage.echo;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.net.InetAddress;

public final class HardwareAddress
{
    public static final StringBuilder getLocalAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; ++i) {
                sb.append(String.format("%02X%s", (Byte)mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb;
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (SocketException e2) {
            e2.printStackTrace();
        }
        return null;
    }
    
    public static final StringBuilder getMacAddress(String ipAddress) {
        InputStreamReader ir = null;
        LineNumberReader input = null;
        try {
            Process process = Runtime.getRuntime().exec("nbtstat -a " + ipAddress);
            ir = new InputStreamReader(process.getInputStream());
            input = new LineNumberReader(ir);
            StringBuilder sb = new StringBuilder();
            String str = "";
            int i = 1;
            int n = 100;
            while (i < n) {
                str = input.readLine();
                if (str != null && str.indexOf("MAC Address") > 1) {
                    sb.append(str.substring(str.indexOf("MAC Address") + 14, str.length()));
                    break;
                }
                else {
                    ++i;
                }
            }
            return sb;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (ir != null) {
                try {
                    ir.close();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }
}
