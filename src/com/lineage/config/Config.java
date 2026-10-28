package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class Config
{
    public static final int vergame = 3800;
    public static String checkip = "";
    public static boolean CACHE_MAP_FILES;
    public static boolean LOGINS_TO_AUTOENTICATION;
    public static String RSA_KEY_E;
    public static String RSA_KEY_N;
    public static boolean DEBUG;
    public static int SERVERNO;
    public static boolean ISUBUNTU;
    public static boolean ISWORKCD; // false=舊法(外部重啟)；true=新法(程式內啟 EXE)
    public static String GAME_SERVER_HOST_NAME;
    public static String GAME_SERVER_PORT;
    public static String SERVERNAME;
    public static String CHAT_SERVER_HOST_NAME;
    public static int CHAT_SERVER_PORT;
    public static String TIME_ZONE;
    public static int CLIENT_LANGUAGE;
    public static String CLIENT_LANGUAGE_CODE;
    public static String[] LANGUAGE_CODE_ARRAY;
    public static String[] AUTORESTART;
    public static boolean AUTO_CREATE_ACCOUNTS;
    public static short MAX_ONLINE_USERS;
    public static int AUTOSAVE_INTERVAL;
    public static int AUTOSAVE_INTERVAL_INVENTORY;
    public static int PC_RECOGNIZE_RANGE;
    public static int RESTART_LOGIN;
    public static boolean NEWS;
    public static boolean GUI;
    
    public static String RESTART_EXE_PATH;
    public static File RESTART_WORK_DIR;
    
    private static final String SERVER_CONFIG_FILE = "./config/server.properties";
    static {
        Config.checkip = "";
        Config.DEBUG = false;
        Config.ISUBUNTU = false;
        Config.LANGUAGE_CODE_ARRAY = new String[] { "UTF8", "EUCKR", "UTF8", "BIG5", "SJIS", "GBK" };
        Config.AUTORESTART = null;
        Config.MAX_ONLINE_USERS = 10;
    }
        
    public static void load() throws ConfigErrorException {
        Properties pack = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/pack.properties"));
            pack.load(is);
            is.close();
            Config.LOGINS_TO_AUTOENTICATION = Boolean.parseBoolean(pack.getProperty("Autoentication", "false"));
            Config.RSA_KEY_E = pack.getProperty("RSA_KEY_E", "0");
            Config.RSA_KEY_N = pack.getProperty("RSA_KEY_N", "0");
        }
        catch (Exception e) {
            //System.err.println("沒有找到登入器加密設置檔案: ./config/pack.properties");
        }
        finally {
            pack.clear();
        }
        Properties set = new Properties();
        try (InputStream is2 = new FileInputStream(new File("./config/server.properties"));
        	     InputStreamReader reader = new InputStreamReader(is2, StandardCharsets.UTF_8)) {
            set.load(reader);
            is2.close();
            Config.GUI = Boolean.parseBoolean(set.getProperty("GUI", "true"));
            Config.SERVERNO = Integer.parseInt(set.getProperty("ServerNo", "1"));
            Config.GAME_SERVER_HOST_NAME = set.getProperty("GameserverHostname", "*");
            Config.GAME_SERVER_PORT = set.getProperty("GameserverPort", "2000-2001");
            Config.CLIENT_LANGUAGE = Integer.parseInt(set.getProperty("ClientLanguage", "3"));
            Config.CLIENT_LANGUAGE_CODE = Config.LANGUAGE_CODE_ARRAY[Config.CLIENT_LANGUAGE];
            String tmp = set.getProperty("AutoRestart", "");
            if (!tmp.equalsIgnoreCase("null")) {
                Config.AUTORESTART = tmp.split(",");
            }
            Config.CACHE_MAP_FILES = Boolean.parseBoolean(set.getProperty("CacheMapFiles", "false"));
            Config.TIME_ZONE = set.getProperty("TimeZone", "CST");
            Config.AUTO_CREATE_ACCOUNTS = Boolean.parseBoolean(set.getProperty("AutoCreateAccounts", "true"));
            Config.MAX_ONLINE_USERS = Short.parseShort(set.getProperty("MaximumOnlineUsers", "30"));
            Config.AUTOSAVE_INTERVAL = Integer.parseInt(set.getProperty("AutosaveInterval", "1200"), 10);
            Config.AUTOSAVE_INTERVAL /= 60;
            if (Config.AUTOSAVE_INTERVAL <= 0) {
                Config.AUTOSAVE_INTERVAL = 20;
            }
            Config.AUTOSAVE_INTERVAL_INVENTORY = Integer.parseInt(set.getProperty("AutosaveIntervalOfInventory", "300"), 10);
            Config.AUTOSAVE_INTERVAL_INVENTORY /= 60;
            if (Config.AUTOSAVE_INTERVAL_INVENTORY <= 0) {
                Config.AUTOSAVE_INTERVAL_INVENTORY = 5;
            }
            Config.PC_RECOGNIZE_RANGE = Integer.parseInt(set.getProperty("PcRecognizeRange", "13"));
            Config.RESTART_LOGIN = Integer.parseInt(set.getProperty("restartlogin", "30"));
            Config.NEWS = Boolean.parseBoolean(set.getProperty("News", "false"));
            Config.ISWORKCD = Integer.parseInt(set.getProperty("ISWORKCD", "1")) == 1; // 1=True, 0=False
            
            // ★ 新增：重啟 EXE 與目錄
            Config.RESTART_EXE_PATH = set.getProperty("RestartExePath", "D:\\祕密天堂381\\381server.exe");
            Config.RESTART_WORK_DIR = new File(set.getProperty("RestartWorkDir", "D:\\祕密天堂381"));
        }
        catch (Exception e2) {
            throw new ConfigErrorException("設置檔案遺失: ./config/server.properties");
        }
        finally {
            set.clear();
        }
    }
}
