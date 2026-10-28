package com.lineage;

import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;
import java.util.Iterator;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.File;

public class SerialNumberUtil
{
    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result = result + line;
            }
            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
    
    public static String getHardDiskSN(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";
            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result = result + line;
            }
            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
    
    public static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";
            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result = result + line;
            }
            input.close();
            file.delete();
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }
        if (result.trim().length() < 1 || result == null) {
            result = "無CPU_ID被讀取";
        }
        return result.trim();
    }
    
    private static List<String> getLocalHostLANAddress() throws UnknownHostException, SocketException {
        List<String> ips = new ArrayList();
        Enumeration<NetworkInterface> interfs = NetworkInterface.getNetworkInterfaces();
        while (interfs.hasMoreElements()) {
            NetworkInterface interf = interfs.nextElement();
            Enumeration<InetAddress> addres = interf.getInetAddresses();
            while (addres.hasMoreElements()) {
                InetAddress in = addres.nextElement();
                if (in instanceof Inet4Address) {
                    System.out.println("v4:" + in.getHostAddress());
                    if (!"127.0.0.1".equals(in.getHostAddress())) {
                        ips.add(in.getHostAddress());
                    }
                    else {
                        continue;
                    }
                }
            }
        }
        return ips;
    }
    
    public static String getMac() {
        try {
            String resultStr = "";
            List<String> ls = getLocalHostLANAddress();
            for (String str : ls) {
                InetAddress ia = InetAddress.getByName(str);
                byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; ++i) {
                    if (i != 0) {
                        sb.append("-");
                    }
                    String s = Integer.toHexString(mac[i] & 0xFF);
                    sb.append((s.length() == 1) ? (String.valueOf(0) + s) : s);
                }
                resultStr = resultStr + sb.toString().toUpperCase() + ",";
            }
            return resultStr;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static String executeLinuxCmd(String cmd) {
        try {
            System.out.println("got cmd job : " + cmd);
            Runtime run = Runtime.getRuntime();
            Process process = run.exec(cmd);
            InputStream in = process.getInputStream();
            BufferedReader bs = new BufferedReader(new InputStreamReader(in));
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[8192];
            int n;
            while ((n = in.read(b)) != -1) {
                out.append(new String(b, 0, n));
            }
            in.close();
            process.destroy();
            return out.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getSerialNumber(String cmd, String record, String symbol) {
        String execResult = executeLinuxCmd(cmd);
        String[] infos = execResult.split("\n");
        String[] array;
        int length = (array = infos).length;
        int i = 0;
        while (i < length) {
            String info = array[i];
            info = info.trim();
            if (info.indexOf(record) != -1) {
                info.replace(" ", "");
                String[] sn = info.split(symbol);
                return sn[1];
            }
            else {
                ++i;
            }
        }
        return null;
    }
    
    public static Map<String, String> getAllSn() {
        String os = System.getProperty("os.name");
        os = os.toUpperCase();
        System.out.println(os);
        Map<String, String> snVo = new HashMap();
        if ("LINUX".equals(os)) {
            System.out.println("=============>for linux");
            String cpuid = getSerialNumber("dmidecode -t processor | grep 'ID'", "ID", ":");
            System.out.println("cpuid : " + cpuid);
            String mainboardNumber = getSerialNumber("dmidecode |grep 'Serial Number'", "Serial Number", ":");
            System.out.println("mainboardNumber : " + mainboardNumber);
            String diskNumber = getSerialNumber("fdisk -l", "Disk identifier", ":");
            System.out.println("diskNumber : " + diskNumber);
            String mac = getSerialNumber("ifconfig -a", "ether", " ");
            snVo.put("cpuid", cpuid.toUpperCase().replace(" ", ""));
            snVo.put("diskid", diskNumber.toUpperCase().replace(" ", ""));
            snVo.put("mac", mac.toUpperCase().replace(" ", ""));
            snVo.put("mainboard", mainboardNumber.toUpperCase().replace(" ", ""));
        }
        else {
            System.out.println("=============>for windows");
            String cpuid = getCPUSerial();
            String mainboard = getMotherboardSN();
            String disk = getHardDiskSN("c");
            String mac = getMac();
            System.out.println("CPU  SN:" + cpuid);
            System.out.println("主機板  SN:" + mainboard);
            System.out.println("C盤   SN:" + disk);
            System.out.println("MAC  SN:" + mac);
            snVo.put("cpuid", cpuid.toUpperCase().replace(" ", ""));
            snVo.put("diskid", disk.toUpperCase().replace(" ", ""));
            snVo.put("mac", mac.toUpperCase().replace(" ", ""));
            snVo.put("mainboard", mainboard.toUpperCase().replace(" ", ""));
        }
        return snVo;
    }
    
    public static void main(String[] args) {
        getAllSn();
    }
}
