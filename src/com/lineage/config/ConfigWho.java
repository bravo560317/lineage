package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import org.apache.commons.logging.Log;

public final class ConfigWho
{
    public static int RATE_XP_WHO;
    public static boolean whoitemeq;
    public static boolean check_who_exp;
    public static int whoexp;
    public static boolean check_who_armor;
    public static int whoarmor;
    public static boolean check_who_weapon;
    public static int whoweapon;
    private static Log _log;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/WHO設定表.properties";
    
    static {
        ConfigWho.RATE_XP_WHO = 1;
    }
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/WHO設定表.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigWho.RATE_XP_WHO = Integer.parseInt(set.getProperty("rate_xp_who", "1"));
            ConfigWho.whoitemeq = Boolean.parseBoolean(set.getProperty("whoitemeq", "false"));
            ConfigWho.check_who_exp = Boolean.parseBoolean(set.getProperty("check_who_exp", "false"));
            ConfigWho.whoexp = Integer.parseInt(set.getProperty("whoexp", "1"));
            ConfigWho.check_who_weapon = Boolean.parseBoolean(set.getProperty("check_who_weapon", "false"));
            ConfigWho.whoweapon = Integer.parseInt(set.getProperty("whoweapon", "1"));
            ConfigWho.check_who_armor = Boolean.parseBoolean(set.getProperty("check_who_armor", "false"));
            ConfigWho.whoarmor = Integer.parseInt(set.getProperty("whoarmor", "1"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/WHO設定表.properties");
        }
        finally {
            set.clear();
        }
    }
}
