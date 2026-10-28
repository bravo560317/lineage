package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class ConfigAi
{
    public static boolean longntimeai_3;
    public static int logintime;
    public static int aitimeran;
    public static int aitimelast;
    public static boolean ALTE;
    public static int aialteitem;
    public static int aialteitemcount;
    public static boolean kickai;
    public static int aiX;
    public static int aiY;
    public static int aimap;
    public static String msg0;
    public static String msg1;
    public static String msg2;
    public static String msg3;
    public static String msg4;
    public static String msg5;
    public static String msg6;
    public static String msg7;
    public static String msg8;
    public static String msg9;
    public static String msg10;
    public static String msg11;
    public static String msg12;
    public static int AIeffect;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/外掛偵測.properties";
    
    static {
        ConfigAi.aialteitemcount = 0;
        ConfigAi.aiX = 0;
        ConfigAi.aiY = 0;
        ConfigAi.aimap = 0;
    }
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/外掛偵測.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigAi.longntimeai_3 = Boolean.parseBoolean(set.getProperty("longntimeai_3", "false"));
            ConfigAi.logintime = Integer.parseInt(set.getProperty("logintime", "0"));
            ConfigAi.aitimeran = Integer.parseInt(set.getProperty("aitimeran", "0"));
            ConfigAi.aitimelast = Integer.parseInt(set.getProperty("aitimelast", "0"));
            ConfigAi.ALTE = Boolean.parseBoolean(set.getProperty("ALTE", "false"));
            ConfigAi.aialteitem = Integer.parseInt(set.getProperty("aialteitem", "0"));
            ConfigAi.aialteitemcount = Integer.parseInt(set.getProperty("aialteitemcount", "0"));
            ConfigAi.kickai = Boolean.parseBoolean(set.getProperty("kickai", "false"));
            ConfigAi.aiX = Integer.parseInt(set.getProperty("aiX", "0"));
            ConfigAi.aiY = Integer.parseInt(set.getProperty("aiY", "0"));
            ConfigAi.aimap = Integer.parseInt(set.getProperty("aimap", "0"));
            ConfigAi.msg0 = set.getProperty("msg0", "");
            ConfigAi.msg1 = set.getProperty("msg1", "");
            ConfigAi.msg2 = set.getProperty("msg2", "");
            ConfigAi.msg3 = set.getProperty("msg3", "");
            ConfigAi.msg4 = set.getProperty("msg4", "");
            ConfigAi.msg5 = set.getProperty("msg5", "");
            ConfigAi.msg6 = set.getProperty("msg6", "");
            ConfigAi.msg7 = set.getProperty("msg7", "");
            ConfigAi.msg8 = set.getProperty("msg8", "");
            ConfigAi.msg9 = set.getProperty("msg9", "");
            ConfigAi.msg10 = set.getProperty("msg10", "");
            ConfigAi.msg11 = set.getProperty("msg11", "");
            ConfigAi.msg12 = set.getProperty("msg12", "");
            ConfigAi.AIeffect = Integer.parseInt(set.getProperty("AIeffect", "0"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/外掛偵測.properties");
        }
        finally {
            set.clear();
        }
    }
}
