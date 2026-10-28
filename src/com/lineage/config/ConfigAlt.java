package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;

public final class ConfigAlt
{
    private static final Log _log;
    public static short GLOBAL_CHAT_LEVEL;
    public static short WHISPER_CHAT_LEVEL;
    public static byte AUTO_LOOT;
    public static int LOOTING_RANGE;
    public static boolean ALT_NONPVP;
    public static boolean ALT_PUNISHMENT;
    public static boolean ALT_ATKMSG;
    public static boolean CLAN_ALLIANCE;
    public static int ALT_ITEM_DELETION_TIME;
    public static boolean ALT_WHO_COMMANDX;
    public static int ALT_WHO_TYPE;
    public static double ALT_WHO_COUNT;
    public static int ALT_WAR_TIME;
    public static int ALT_WAR_TIME_UNIT;
    public static int ALT_WAR_INTERVAL;
    public static int ALT_WAR_INTERVAL_UNIT;
    public static int ALT_RATE_OF_DUTY;
    public static boolean SPAWN_HOME_POINT;
    public static int SPAWN_HOME_POINT_RANGE;
    public static int SPAWN_HOME_POINT_COUNT;
    public static int SPAWN_HOME_POINT_DELAY;
    public static int ELEMENTAL_STONE_AMOUNT;
    public static int HOUSE_TAX_INTERVAL;
    public static int HOUSE_TAX_ADENA;
    public static int MAX_DOLL_COUNT;
    public static int MAX_NPC_ITEM;
    public static int MAX_PERSONAL_WAREHOUSE_ITEM;
    public static int MAX_CLAN_WAREHOUSE_ITEM;
    public static int DELETE_CHARACTER_AFTER_LV;
    public static boolean DELETE_CHARACTER_AFTER_7DAYS;
    public static int NPC_DELETION_TIME;
    public static int DEFAULT_CHARACTER_SLOT;
    public static int MEDICINE;
    public static int POWER;
    public static int POWERMEDICINE;
    public static int BaseResetPOWER;
    public static boolean DORP_ITEM;
    public static final int MAX_NPC = 35;
    public static int MAX_PARTY_SIZE;
    public static boolean METE_GIVE_POTION;
    public static List<Integer> DRAGON_KEY_MAP_LIST;
    public static List<Integer> NO_AI_MAP_LIST;
    public static boolean GM_OVERHEARD;
    public static boolean GM_OVERHEARD0;
    public static boolean GM_OVERHEARD4;
    public static boolean GM_OVERHEARD11;
    public static boolean GM_OVERHEARD13;
    public static boolean ALT_WARPUNISHMENT;
    public static int CHAR_BOOK_INIT_COUNT;
    public static int CHAR_BOOK_MAX_CHARGE;
    public static boolean QUIZ_SET_SWITCH;
    public static Calendar QUIZ_SET_RESET_TIME;
    public static int QUIZ_SET_TYPE;
    public static int QUIZ_SET_LEVEL;
    public static int[][] QUIZ_SET_LIST;
    public static String NO_CD;
public static List<Integer> AUTO_LOOT2_MAP_LIST = new ArrayList<>();
    private static final String ALT_SETTINGS_FILE = "./config/altsettings.properties";
    
    static {
        _log = LogFactory.getLog(ConfigAlt.class);
        ConfigAlt.DRAGON_KEY_MAP_LIST = new ArrayList();
        ConfigAlt.NO_AI_MAP_LIST = new ArrayList();
    }
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/altsettings.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigAlt.GM_OVERHEARD = Boolean.parseBoolean(set.getProperty("GM_OVERHEARD", "false"));
            ConfigAlt.GM_OVERHEARD0 = Boolean.parseBoolean(set.getProperty("GM_OVERHEARD0", "false"));
            ConfigAlt.GM_OVERHEARD4 = Boolean.parseBoolean(set.getProperty("GM_OVERHEARD4", "false"));
            ConfigAlt.GM_OVERHEARD11 = Boolean.parseBoolean(set.getProperty("GM_OVERHEARD11", "false"));
            ConfigAlt.GM_OVERHEARD13 = Boolean.parseBoolean(set.getProperty("GM_OVERHEARD13", "false"));
            ConfigAlt.GLOBAL_CHAT_LEVEL = Short.parseShort(set.getProperty("GlobalChatLevel", "30"));
            ConfigAlt.WHISPER_CHAT_LEVEL = Short.parseShort(set.getProperty("WhisperChatLevel", "5"));
            ConfigAlt.AUTO_LOOT = Byte.parseByte(set.getProperty("AutoLoot", "2"));
            ConfigAlt.LOOTING_RANGE = Integer.parseInt(set.getProperty("LootingRange", "3"));
            ConfigAlt.ALT_NONPVP = Boolean.parseBoolean(set.getProperty("NonPvP", "true"));
            ConfigAlt.ALT_PUNISHMENT = Boolean.parseBoolean(set.getProperty("Punishment", "true"));
            ConfigAlt.ALT_WARPUNISHMENT = Boolean.parseBoolean(set.getProperty("WarPunishment", "false"));
            ConfigAlt.CLAN_ALLIANCE = Boolean.parseBoolean(set.getProperty("ClanAlliance", "true"));
            ConfigAlt.ALT_ITEM_DELETION_TIME = Integer.parseInt(set.getProperty("ItemDeletionTime", "10"));
            if (ConfigAlt.ALT_ITEM_DELETION_TIME > 60) {
                ConfigAlt.ALT_ITEM_DELETION_TIME = 60;
            }
            ConfigAlt.ALT_WHO_COMMANDX = Boolean.parseBoolean(set.getProperty("WhoCommandx", "false"));
            ConfigAlt.ALT_WHO_TYPE = Integer.parseInt(set.getProperty("Who_type", "0"));
            ConfigAlt.ALT_WHO_COUNT = Double.parseDouble(set.getProperty("WhoCommandcount", "1.0"));
            if (ConfigAlt.ALT_WHO_COUNT < 1.0) {
                ConfigAlt.ALT_WHO_COUNT = 1.0;
            }
            String strWar = set.getProperty("WarTime", "2h");
            if (strWar.indexOf("d") >= 0) {
                ConfigAlt.ALT_WAR_TIME_UNIT = 5;
                strWar = strWar.replace("d", "");
            }
            else if (strWar.indexOf("h") >= 0) {
                ConfigAlt.ALT_WAR_TIME_UNIT = 11;
                strWar = strWar.replace("h", "");
            }
            else if (strWar.indexOf("m") >= 0) {
                ConfigAlt.ALT_WAR_TIME_UNIT = 12;
                strWar = strWar.replace("m", "");
            }
            ConfigAlt.ALT_WAR_TIME = Integer.parseInt(strWar);
            strWar = set.getProperty("WarInterval", "4d");
            if (strWar.indexOf("d") >= 0) {
                ConfigAlt.ALT_WAR_INTERVAL_UNIT = 5;
                strWar = strWar.replace("d", "");
            }
            else if (strWar.indexOf("h") >= 0) {
                ConfigAlt.ALT_WAR_INTERVAL_UNIT = 11;
                strWar = strWar.replace("h", "");
            }
            else if (strWar.indexOf("m") >= 0) {
                ConfigAlt.ALT_WAR_INTERVAL_UNIT = 12;
                strWar = strWar.replace("m", "");
            }

ConfigAlt.AUTO_LOOT2_MAP_LIST.clear();

if (set.getProperty("AutoLoot2Map") != null) {
    String[] maps = set.getProperty("AutoLoot2Map").split(",");
    for (String map : maps) {
        ConfigAlt.AUTO_LOOT2_MAP_LIST.add(Integer.parseInt(map.trim()));
    }
}
            ConfigAlt.ALT_WAR_INTERVAL = Integer.parseInt(strWar);
            ConfigAlt.SPAWN_HOME_POINT = Boolean.parseBoolean(set.getProperty("SpawnHomePoint", "true"));
            ConfigAlt.SPAWN_HOME_POINT_COUNT = Integer.parseInt(set.getProperty("SpawnHomePointCount", "2"));
            ConfigAlt.SPAWN_HOME_POINT_DELAY = Integer.parseInt(set.getProperty("SpawnHomePointDelay", "100"));
            ConfigAlt.SPAWN_HOME_POINT_RANGE = Integer.parseInt(set.getProperty("SpawnHomePointRange", "8"));
            ConfigAlt.ELEMENTAL_STONE_AMOUNT = Integer.parseInt(set.getProperty("ElementalStoneAmount", "300"));
            ConfigAlt.HOUSE_TAX_INTERVAL = Integer.parseInt(set.getProperty("HouseTaxInterval", "10"));
            ConfigAlt.HOUSE_TAX_ADENA = Integer.parseInt(set.getProperty("HouseTaxAdena", "2000"));
            ConfigAlt.MAX_DOLL_COUNT = Integer.parseInt(set.getProperty("MaxDollCount", "1"));
            ConfigAlt.MAX_NPC_ITEM = Integer.parseInt(set.getProperty("MaxNpcItem", "8"));
            ConfigAlt.MAX_PERSONAL_WAREHOUSE_ITEM = Integer.parseInt(set.getProperty("MaxPersonalWarehouseItem", "100"));
            ConfigAlt.MAX_CLAN_WAREHOUSE_ITEM = Integer.parseInt(set.getProperty("MaxClanWarehouseItem", "200"));
            ConfigAlt.DELETE_CHARACTER_AFTER_LV = Integer.parseInt(set.getProperty("DeleteCharacterAfterLV", "60"));
            ConfigAlt.DELETE_CHARACTER_AFTER_7DAYS = Boolean.parseBoolean(set.getProperty("DeleteCharacterAfter7Days", "True"));
            ConfigAlt.NPC_DELETION_TIME = Integer.parseInt(set.getProperty("NpcDeletionTime", "10"));
            ConfigAlt.DEFAULT_CHARACTER_SLOT = Integer.parseInt(set.getProperty("DefaultCharacterSlot", "4"));
            ConfigAlt.MEDICINE = Integer.parseInt(set.getProperty("Medicine", "20"));
            ConfigAlt.POWER = Integer.parseInt(set.getProperty("Power", "35"));
            ConfigAlt.POWERMEDICINE = Integer.parseInt(set.getProperty("MedicinePower", "45"));
            ConfigAlt.BaseResetPOWER = Integer.parseInt(set.getProperty("BaseResetPOWER", "37"));
            ConfigAlt.DORP_ITEM = Boolean.parseBoolean(set.getProperty("dorpitem", "true"));
            ConfigAlt.METE_GIVE_POTION = Boolean.parseBoolean(set.getProperty("MeteGivePotion", "false"));
            ConfigAlt.CHAR_BOOK_INIT_COUNT = Integer.parseInt(set.getProperty("CharBookInitCount", "60"));
            ConfigAlt.CHAR_BOOK_MAX_CHARGE = Integer.parseInt(set.getProperty("CharBookMaxCharge", "4"));
            ConfigAlt.NO_CD = set.getProperty("NO_CD", "null");
            ConfigAlt.QUIZ_SET_SWITCH = Boolean.parseBoolean(set.getProperty("QuizSetSwitch", "false"));
            String tmp10 = set.getProperty("QuizSetResetTime", "");
            if (!tmp10.equalsIgnoreCase("null")) {
                String[] temp = tmp10.split(":");
                if (temp.length == 3) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(11, Integer.parseInt(temp[0]));
                    cal.set(12, Integer.parseInt(temp[1]));
                    cal.set(13, Integer.parseInt(temp[2]));
                    ConfigAlt.QUIZ_SET_RESET_TIME = cal;
                }
                else {
                    ConfigAlt._log.info("[每日一題系統] 重置時間有誤, 請重新設置!");
                }
            }
            ConfigAlt.QUIZ_SET_TYPE = Integer.parseInt(set.getProperty("QuizSetType", "1"));
            ConfigAlt.QUIZ_SET_LEVEL = Integer.parseInt(set.getProperty("QuizSetLevel", "0"));
            String tmp11 = set.getProperty("QuizSetList", "");
            if (!tmp11.equalsIgnoreCase("null")) {
                String[] temp2 = tmp11.split(",");
                int size = temp2.length;
                ConfigAlt.QUIZ_SET_LIST = new int[size][2];
                for (int i = 0; i < size; ++i) {
                    String[] temp3 = temp2[i].split(":");
                    ConfigAlt.QUIZ_SET_LIST[i][0] = (int)Integer.valueOf(temp3[0]);
                    ConfigAlt.QUIZ_SET_LIST[i][1] = (int)Integer.valueOf(temp3[1]);
                }
            }
            if (set.getProperty("DragonKeyMapList") != null) {
                String[] split;
                for (int length = (split = set.getProperty("DragonKeyMapList").split(",")).length, j = 0; j < length; ++j) {
                    String str = split[j];
                    ConfigAlt.DRAGON_KEY_MAP_LIST.add(Integer.parseInt(str));
                }
            }
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/altsettings.properties");
        }
        finally {
            set.clear();
        }
    }
}
