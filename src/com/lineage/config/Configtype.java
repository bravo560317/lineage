package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import org.apache.commons.logging.Log;

public final class Configtype
{
    public static int weaponbroadfail;
    public static int armorbroadfail;
    public static int weaponbroadtrue;
    public static int armorbroadtrue;
    public static int Accessoryfalse;
    public static int Accessorytrue;
    public static int weapon_savebroad;
    public static int armor_savebroad;
    public static int Accessory_savebroad;
    public static String msg1_fail;
    public static String msg1_true;
    public static String msg4_fail;
    public static String msg4_true;
    public static int armorlv;
    public static int weaponlv;
    private static Log _log;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/武卷防捲設定表.properties";
    
    static {
        Configtype.weapon_savebroad = 0;
        Configtype.armor_savebroad = 0;
        Configtype.Accessory_savebroad = 0;
    }
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/武卷防捲設定表.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            Configtype.Accessoryfalse = Integer.parseInt(set.getProperty("Accessoryfalse", "0"));
            Configtype.Accessorytrue = Integer.parseInt(set.getProperty("Accessorytrue", "0"));
            Configtype.weaponbroadfail = Integer.parseInt(set.getProperty("weaponbroadfail", "0"));
            Configtype.armorbroadfail = Integer.parseInt(set.getProperty("armorbroadfail", "0"));
            Configtype.weaponbroadtrue = Integer.parseInt(set.getProperty("weaponbroadtrue", "0"));
            Configtype.armorbroadtrue = Integer.parseInt(set.getProperty("armorbroadtrue", "0"));
            Configtype.weapon_savebroad = Integer.parseInt(set.getProperty("weapon_savebroad", ""));
            Configtype.armor_savebroad = Integer.parseInt(set.getProperty("armor_savebroad", ""));
            Configtype.Accessory_savebroad = Integer.parseInt(set.getProperty("Accessory_savebroad", ""));
            Configtype.msg1_fail = set.getProperty("msg1_fail", "");
            Configtype.msg1_true = set.getProperty("msg1_true", "");
            Configtype.msg4_fail = set.getProperty("msg4_fail", "");
            Configtype.msg4_true = set.getProperty("msg4_true", "");
            Configtype.armorlv = Integer.parseInt(set.getProperty("armorlv", "10"));
            Configtype.weaponlv = Integer.parseInt(set.getProperty("weaponlv", "10"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/武卷防捲設定表.properties");
        }
        finally {
            set.clear();
        }
    }
}
