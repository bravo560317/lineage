package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class ConfigClastle
{
    public static int KENT_TOWER_X;
    public static int KENT_TOWER_Y;
    public static int KENT_TOWER_MAP;
    public static int OT_TOWER_X;
    public static int OT_TOWER_Y;
    public static int OT_TOWER_MAP;
    public static int WW_TOWER_X;
    public static int WW_TOWER_Y;
    public static int WW_TOWER_MAP;
    public static int HEINE_TOWER_X;
    public static int HEINE_TOWER_Y;
    public static int HEINE_TOWER_MAP;
    public static int DOWA_TOWER_X;
    public static int DOWA_TOWER_Y;
    public static int DOWA_TOWER_MAP;
    public static int ADEN_TOWER_X;
    public static int ADEN_TOWER_Y;
    public static int ADEN_TOWER_MAP;
    public static int DIAD_TOWER_X;
    public static int DIAD_TOWER_Y;
    public static int DIAD_TOWER_MAP;
    public static int AIeffect;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/城堡守塔座標設定表.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/城堡守塔座標設定表.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigClastle.KENT_TOWER_X = Integer.parseInt(set.getProperty("KENT_TOWER_X", "0"));
            ConfigClastle.KENT_TOWER_Y = Integer.parseInt(set.getProperty("KENT_TOWER_Y", "0"));
            ConfigClastle.KENT_TOWER_MAP = Integer.parseInt(set.getProperty("KENT_TOWER_MAP", "0"));
            ConfigClastle.OT_TOWER_X = Integer.parseInt(set.getProperty("OT_TOWER_X", "0"));
            ConfigClastle.OT_TOWER_Y = Integer.parseInt(set.getProperty("OT_TOWER_Y", "0"));
            ConfigClastle.OT_TOWER_MAP = Integer.parseInt(set.getProperty("OT_TOWER_MAP", "0"));
            ConfigClastle.WW_TOWER_X = Integer.parseInt(set.getProperty("WW_TOWER_X", "0"));
            ConfigClastle.WW_TOWER_Y = Integer.parseInt(set.getProperty("WW_TOWER_Y", "0"));
            ConfigClastle.WW_TOWER_MAP = Integer.parseInt(set.getProperty("WW_TOWER_MAP", "0"));
            ConfigClastle.HEINE_TOWER_X = Integer.parseInt(set.getProperty("HEINE_TOWER_X", "0"));
            ConfigClastle.HEINE_TOWER_Y = Integer.parseInt(set.getProperty("HEINE_TOWER_Y", "0"));
            ConfigClastle.HEINE_TOWER_MAP = Integer.parseInt(set.getProperty("HEINE_TOWER_MAP", "0"));
            ConfigClastle.DOWA_TOWER_X = Integer.parseInt(set.getProperty("DOWA_TOWER_X", "0"));
            ConfigClastle.DOWA_TOWER_Y = Integer.parseInt(set.getProperty("DOWA_TOWER_Y", "0"));
            ConfigClastle.DOWA_TOWER_MAP = Integer.parseInt(set.getProperty("DOWA_TOWER_MAP", "0"));
            ConfigClastle.ADEN_TOWER_X = Integer.parseInt(set.getProperty("ADEN_TOWER_X", "0"));
            ConfigClastle.ADEN_TOWER_Y = Integer.parseInt(set.getProperty("ADEN_TOWER_Y", "0"));
            ConfigClastle.ADEN_TOWER_MAP = Integer.parseInt(set.getProperty("ADEN_TOWER_MAP", "0"));
            ConfigClastle.DIAD_TOWER_X = Integer.parseInt(set.getProperty("DIAD_TOWER_X", "0"));
            ConfigClastle.DIAD_TOWER_Y = Integer.parseInt(set.getProperty("DIAD_TOWER_Y", "0"));
            ConfigClastle.DIAD_TOWER_MAP = Integer.parseInt(set.getProperty("DIAD_TOWER_MAP", "0"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/城堡守塔座標設定表.properties");
        }
        finally {
            set.clear();
        }
    }
}
