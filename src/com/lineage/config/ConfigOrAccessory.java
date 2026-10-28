package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import org.apache.commons.logging.Log;

public final class ConfigOrAccessory
{
    public static int ran1;
    public static int removeitem1;
    public static int ran2;
    public static int removeitem2;
    public static int ran3;
    public static int removeitem3;
    public static int ran4;
    public static int removeitem4;
    public static int ran5;
    public static int removeitem5;
    public static int ran6;
    public static int removeitem6;
    public static int ran7;
    public static int removeitem7;
    public static int ran8;
    public static int removeitem8;
    public static int ran9;
    public static int removeitem9;
    public static String msg1;
    public static String msg2;
    public static String msg3;
    public static String msg4;
    private static Log _log;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/歐林強化設置表.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/歐林強化設置表.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigOrAccessory.ran1 = Integer.parseInt(set.getProperty("ran1", "0"));
            ConfigOrAccessory.removeitem1 = Integer.parseInt(set.getProperty("removeitem1", "0"));
            ConfigOrAccessory.ran2 = Integer.parseInt(set.getProperty("ran2", "0"));
            ConfigOrAccessory.removeitem2 = Integer.parseInt(set.getProperty("removeitem2", "0"));
            ConfigOrAccessory.ran3 = Integer.parseInt(set.getProperty("ran3", "0"));
            ConfigOrAccessory.removeitem3 = Integer.parseInt(set.getProperty("removeitem3", "0"));
            ConfigOrAccessory.ran4 = Integer.parseInt(set.getProperty("ran4", "0"));
            ConfigOrAccessory.removeitem4 = Integer.parseInt(set.getProperty("removeitem4", "0"));
            ConfigOrAccessory.ran5 = Integer.parseInt(set.getProperty("ran5", "0"));
            ConfigOrAccessory.removeitem5 = Integer.parseInt(set.getProperty("removeitem5", "0"));
            ConfigOrAccessory.ran6 = Integer.parseInt(set.getProperty("ran6", "0"));
            ConfigOrAccessory.removeitem6 = Integer.parseInt(set.getProperty("removeitem6", "0"));
            ConfigOrAccessory.ran7 = Integer.parseInt(set.getProperty("ran7", "0"));
            ConfigOrAccessory.removeitem7 = Integer.parseInt(set.getProperty("removeitem7", "0"));
            ConfigOrAccessory.ran8 = Integer.parseInt(set.getProperty("ran8", "0"));
            ConfigOrAccessory.removeitem8 = Integer.parseInt(set.getProperty("removeitem8", "0"));
            ConfigOrAccessory.ran9 = Integer.parseInt(set.getProperty("ran9", "0"));
            ConfigOrAccessory.removeitem9 = Integer.parseInt(set.getProperty("removeitem9", "0"));
            ConfigOrAccessory.msg1 = set.getProperty("msg1", "");
            ConfigOrAccessory.msg2 = set.getProperty("msg2", "");
            ConfigOrAccessory.msg3 = set.getProperty("msg3", "");
            ConfigOrAccessory.msg4 = set.getProperty("msg4", "");
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/歐林強化設置表.properties");
        }
        finally {
            set.clear();
        }
    }
}
