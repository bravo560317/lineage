package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import org.apache.commons.logging.Log;

public final class ConfigTemplThread
{
    public static int dropitem1;
    public static int dropitem2;
    public static int dropitem3;
    public static int dropitem4;
    public static int dropitem5;
    public static int dropitem6;
    public static int dropitem7;
    public static int dropitem8;
    public static int dropitem9;
    public static int dropitem10;
    public static int dropitem11;
    public static int dropitem12;
    public static int dropitem13;
    public static int dropitem14;
    public static int dropitem15;
    public static int dropitem16;
    public static int dropitem17;
    public static int dropitem18;
    public static int dropitem19;
    public static int dropitem20;
    public static int dropitem21;
    public static int dropitem22;
    public static int dropitem23;
    public static int dropitem24;
    public static int dropitem25;
    public static int dropitem26;
    public static int dropran;
    private static Log _log;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/夢幻之島副本設定表.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/夢幻之島副本設定表.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigTemplThread.dropitem1 = Integer.parseInt(set.getProperty("dropitem1", "1"));
            ConfigTemplThread.dropitem2 = Integer.parseInt(set.getProperty("dropitem2", "1"));
            ConfigTemplThread.dropitem3 = Integer.parseInt(set.getProperty("dropitem3", "1"));
            ConfigTemplThread.dropitem4 = Integer.parseInt(set.getProperty("dropitem4", "1"));
            ConfigTemplThread.dropitem5 = Integer.parseInt(set.getProperty("dropitem5", "1"));
            ConfigTemplThread.dropitem6 = Integer.parseInt(set.getProperty("dropitem6", "1"));
            ConfigTemplThread.dropitem7 = Integer.parseInt(set.getProperty("dropitem7", "1"));
            ConfigTemplThread.dropitem8 = Integer.parseInt(set.getProperty("dropitem8", "1"));
            ConfigTemplThread.dropitem9 = Integer.parseInt(set.getProperty("dropitem9", "1"));
            ConfigTemplThread.dropitem10 = Integer.parseInt(set.getProperty("dropitem10", "1"));
            ConfigTemplThread.dropitem11 = Integer.parseInt(set.getProperty("dropitem11", "1"));
            ConfigTemplThread.dropitem12 = Integer.parseInt(set.getProperty("dropitem12", "1"));
            ConfigTemplThread.dropitem13 = Integer.parseInt(set.getProperty("dropitem13", "1"));
            ConfigTemplThread.dropitem14 = Integer.parseInt(set.getProperty("dropitem14", "1"));
            ConfigTemplThread.dropitem15 = Integer.parseInt(set.getProperty("dropitem15", "1"));
            ConfigTemplThread.dropitem16 = Integer.parseInt(set.getProperty("dropitem16", "1"));
            ConfigTemplThread.dropitem17 = Integer.parseInt(set.getProperty("dropitem17", "1"));
            ConfigTemplThread.dropitem18 = Integer.parseInt(set.getProperty("dropitem18", "1"));
            ConfigTemplThread.dropitem19 = Integer.parseInt(set.getProperty("dropitem19", "1"));
            ConfigTemplThread.dropitem20 = Integer.parseInt(set.getProperty("dropitem20", "1"));
            ConfigTemplThread.dropitem21 = Integer.parseInt(set.getProperty("dropitem21", "1"));
            ConfigTemplThread.dropitem22 = Integer.parseInt(set.getProperty("dropitem22", "1"));
            ConfigTemplThread.dropitem23 = Integer.parseInt(set.getProperty("dropitem23", "1"));
            ConfigTemplThread.dropitem24 = Integer.parseInt(set.getProperty("dropitem24", "1"));
            ConfigTemplThread.dropitem25 = Integer.parseInt(set.getProperty("dropitem25", "1"));
            ConfigTemplThread.dropitem26 = Integer.parseInt(set.getProperty("dropitem26", "1"));
            ConfigTemplThread.dropran = Integer.parseInt(set.getProperty("dropran", "1"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/夢幻之島副本設定表.properties");
        }
        finally {
            set.clear();
        }
    }
}
