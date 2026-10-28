package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import org.apache.commons.logging.Log;

public final class ConfigItem
{
    public static int bd_Time;
    public static int itemid1;
    public static int itemid2;
    public static int itemid3;
    public static int itemid4;
    public static int itemid5;
    public static int itemid6;
    public static int itemid7;
    public static int itemid8;
    public static int itemid9;
    public static int itemid10;
    public static int itemid11;
    public static int itemid12;
    public static int itemid13;
    public static int itemid14;
    public static int itemid15;
    public static int itemid16;
    public static int itemid17;
    public static int itemid18;
    public static int itemid19;
    public static int itemid20;
    public static int itemid21;
    public static int itemid22;
    public static int itemid23;
    public static int itemid24;
    public static int itemid25;
    public static int itemid26;
    public static int itemid27;
    public static int itemid28;
    public static int itemid29;
    public static int itemid30;
    private static Log _log;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/重啟強制刪除背包道具.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/重啟強制刪除背包道具.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigItem.bd_Time = Integer.parseInt(set.getProperty("bd_Time", "0"));
            ConfigItem.itemid1 = Integer.parseInt(set.getProperty("itemid1", "0"));
            ConfigItem.itemid2 = Integer.parseInt(set.getProperty("itemid2", "0"));
            ConfigItem.itemid3 = Integer.parseInt(set.getProperty("itemid3", "0"));
            ConfigItem.itemid4 = Integer.parseInt(set.getProperty("itemid4", "0"));
            ConfigItem.itemid5 = Integer.parseInt(set.getProperty("itemid5", "0"));
            ConfigItem.itemid6 = Integer.parseInt(set.getProperty("itemid6", "0"));
            ConfigItem.itemid7 = Integer.parseInt(set.getProperty("itemid7", "0"));
            ConfigItem.itemid8 = Integer.parseInt(set.getProperty("itemid8", "0"));
            ConfigItem.itemid9 = Integer.parseInt(set.getProperty("itemid9", "0"));
            ConfigItem.itemid10 = Integer.parseInt(set.getProperty("itemid10", "0"));
            ConfigItem.itemid11 = Integer.parseInt(set.getProperty("itemid11", "0"));
            ConfigItem.itemid12 = Integer.parseInt(set.getProperty("itemid12", "0"));
            ConfigItem.itemid13 = Integer.parseInt(set.getProperty("itemid13", "0"));
            ConfigItem.itemid14 = Integer.parseInt(set.getProperty("itemid14", "0"));
            ConfigItem.itemid15 = Integer.parseInt(set.getProperty("itemid15", "0"));
            ConfigItem.itemid16 = Integer.parseInt(set.getProperty("itemid16", "0"));
            ConfigItem.itemid17 = Integer.parseInt(set.getProperty("itemid17", "0"));
            ConfigItem.itemid18 = Integer.parseInt(set.getProperty("itemid18", "0"));
            ConfigItem.itemid19 = Integer.parseInt(set.getProperty("itemid19", "0"));
            ConfigItem.itemid20 = Integer.parseInt(set.getProperty("itemid20", "0"));
            ConfigItem.itemid21 = Integer.parseInt(set.getProperty("itemid21", "0"));
            ConfigItem.itemid22 = Integer.parseInt(set.getProperty("itemid22", "0"));
            ConfigItem.itemid23 = Integer.parseInt(set.getProperty("itemid23", "0"));
            ConfigItem.itemid24 = Integer.parseInt(set.getProperty("itemid24", "0"));
            ConfigItem.itemid25 = Integer.parseInt(set.getProperty("itemid25", "0"));
            ConfigItem.itemid26 = Integer.parseInt(set.getProperty("itemid26", "0"));
            ConfigItem.itemid27 = Integer.parseInt(set.getProperty("itemid27", "0"));
            ConfigItem.itemid28 = Integer.parseInt(set.getProperty("itemid28", "0"));
            ConfigItem.itemid29 = Integer.parseInt(set.getProperty("itemid29", "0"));
            ConfigItem.itemid30 = Integer.parseInt(set.getProperty("itemid30", "0"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/重啟強制刪除背包道具.properties");
        }
        finally {
            set.clear();
        }
    }
}
