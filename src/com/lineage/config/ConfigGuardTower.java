package com.lineage.config;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import java.util.List;

public final class ConfigGuardTower
{
    public static boolean ISOPEN;
    public static int LEVEL;
    public static int TOWERID;
    public static int TOWERX;
    public static int TOWERY;
    public static int MAXPC;
    public static int MAPX;
    public static int MAPY;
    public static int MAPID;
    public static int NEEDITEM;
    public static int NEEDITEMCOUNT;
    public static int GIFT_Count;
    public static int ComfortGift_COUNT;
    public static int GIFT;
    public static int COMFORTGIFT;
    public static List<Integer> X;
    public static List<Integer> Y;
    private static final Log _log;
    private static final String GUARD_SETTINGS_FILE = "./config/其他控制端/守塔系統設置.properties";
    
    static {
        ConfigGuardTower.X = new ArrayList();
        ConfigGuardTower.Y = new ArrayList();
        _log = LogFactory.getLog(ConfigGuardTower.class);
    }
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/守塔系統設置.properties"));
            set.load(is);
            is.close();
            if (set.getProperty("MobX") != null) {
                String[] split;
                for (int length = (split = set.getProperty("MobX").split(",")).length, i = 0; i < length; ++i) {
                    String str = split[i];
                    ConfigGuardTower.X.add(Integer.parseInt(str));
                }
            }
            if (set.getProperty("MobY") != null) {
                String[] split2;
                for (int length2 = (split2 = set.getProperty("MobY").split(",")).length, j = 0; j < length2; ++j) {
                    String str = split2[j];
                    ConfigGuardTower.Y.add(Integer.parseInt(str));
                }
            }
            ConfigGuardTower.ISOPEN = Boolean.parseBoolean(set.getProperty("IsOpen", "false"));
            ConfigGuardTower.LEVEL = Integer.parseInt(set.getProperty("PcLevel", "50"));
            ConfigGuardTower.TOWERID = Integer.parseInt(set.getProperty("TowerId", "989997"));
            ConfigGuardTower.TOWERX = Integer.parseInt(set.getProperty("TowerX", "32701"));
            ConfigGuardTower.TOWERY = Integer.parseInt(set.getProperty("TowerY", "32896"));
            ConfigGuardTower.MAXPC = Integer.parseInt(set.getProperty("MaxPlayer", "30"));
            ConfigGuardTower.MAPX = Integer.parseInt(set.getProperty("MapX", "32700"));
            ConfigGuardTower.MAPY = Integer.parseInt(set.getProperty("MapY", "32886"));
            ConfigGuardTower.MAPID = Integer.parseInt(set.getProperty("MapId", "95"));
            ConfigGuardTower.NEEDITEM = Integer.parseInt(set.getProperty("NeedItemId", "40308"));
            ConfigGuardTower.NEEDITEMCOUNT = Integer.parseInt(set.getProperty("NeedCount", "10000"));
            ConfigGuardTower.GIFT = Integer.parseInt(set.getProperty("GiftId", "60"));
            ConfigGuardTower.COMFORTGIFT = Integer.parseInt(set.getProperty("ComfortGift", "40308"));
            ConfigGuardTower.GIFT_Count = Integer.parseInt(set.getProperty("GIFT_Count", "10000"));
            ConfigGuardTower.ComfortGift_COUNT = Integer.parseInt(set.getProperty("ComfortGift_COUNT", "10000"));
        }
        catch (Exception e) {
            ConfigGuardTower._log.error(e.getLocalizedMessage(), e);
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/守塔系統設置.properties");
        }
        finally {
            set.clear();
        }
    }
}
