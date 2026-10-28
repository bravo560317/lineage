package com.lineage.config;

import java.io.InputStream;
import com.lineage.commons.system.LanSecurityManager;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class ConfigIpCheck
{
    public static int ipcount;
    public static int timeOutSocket;
    public static boolean IPCHECKPACK;
    public static boolean IPCHECK;
    public static int TIMEMILLIS;
    public static int COUNT;
    public static boolean SETDB;
    public static boolean UFW;
    public static boolean ISONEIP;
    public static int ONETIMEMILLIS;
    private static final String _ipcheck = "./config/ipcheck.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/ipcheck.properties"));
            set.load(is);
            is.close();
            ConfigIpCheck.IPCHECKPACK = Boolean.parseBoolean(set.getProperty("IPCHECKPACK", "false"));
            if (ConfigIpCheck.IPCHECKPACK) {
                LanSecurityManager manager = new LanSecurityManager();
                manager.stsrt_cmd_tmp();
            }
            ConfigIpCheck.IPCHECK = Boolean.parseBoolean(set.getProperty("IPCHECK", "false"));
            ConfigIpCheck.TIMEMILLIS = Integer.parseInt(set.getProperty("TIMEMILLIS", "1000"));
            ConfigIpCheck.ipcount = Integer.parseInt(set.getProperty("ipcount", "3"));
            ConfigIpCheck.COUNT = Integer.parseInt(set.getProperty("COUNT", "10"));
            ConfigIpCheck.SETDB = Boolean.parseBoolean(set.getProperty("SETDB", "false"));
            ConfigIpCheck.UFW = Boolean.parseBoolean(set.getProperty("UFW", "true"));
            ConfigIpCheck.ISONEIP = Boolean.parseBoolean(set.getProperty("ISONEIP", "false"));
            ConfigIpCheck.ONETIMEMILLIS = Integer.parseInt(set.getProperty("ONETIMEMILLIS", "20000"));
            ConfigIpCheck.timeOutSocket = Integer.parseInt(set.getProperty("timeOutSocket", "60"));
            if (ConfigIpCheck.ONETIMEMILLIS != 0) {
                LanSecurityManager manager = new LanSecurityManager();
                manager.stsrt_cmd();
            }
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/ipcheck.properties");
        }
        finally {
            set.clear();
        }
    }
}
