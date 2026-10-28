package com.lineage.config;

import java.util.StringTokenizer;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import org.apache.commons.logging.Log;
import java.util.ArrayList;

public final class ConfigGuaji
{
    public static int[] N_followmap;
    public static ArrayList<Integer> USE_SKILL;
    public static ArrayList<Integer> AREA_SKILL;
    public static boolean Guaji_tele;
    public static int Guaji_tele_item;
    public static int Guaji_tele_itemcount;
    public static int itemadena1;
    public static int itemadena2;
    public static int itemadena3;
    public static int itemadena4;
    public static int itemadena5;
    public static int itemadena6;
    public static int itemadena7;
    public static int itemadena8;
    public static int itemadena9;
    public static int itemid1;
    public static int itemid2;
    public static int itemid3;
    public static int itemid4;
    public static int itemid5;
    public static int itemid6;
    public static int itemid7;
    public static int itemid8;
    public static int itemid9;
    public static boolean followtele;
    public static boolean Guaji_save;
    public static int Guaji_level;
    public static String guaji;
    public static double adena;
    public static double guajiexp2;
    public static double guajidrop;
    public static int guajiitem;
    public static boolean checktimeguaji;
    public static boolean Guaji_action;
    public static int[] Guaji_map_stopskill;
    public static int[] GUAJI_ITEM;
    public static String itemname1;
    public static String itemname2;
    public static String itemname3;
    public static String itemname4;
    public static String itemname5;
    public static String itemname6;
    public static String itemname7;
    public static String itemname8;
    public static String itemname9;
    private static Log _log;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/掛機相關設置表.properties";
    
    static {
        ConfigGuaji.USE_SKILL = new ArrayList();
        ConfigGuaji.AREA_SKILL = new ArrayList();
    }
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/掛機相關設置表.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            if (set.getProperty("USE_SKILL") != null) {
                String[] arrayOfString;
                int i = (arrayOfString = set.getProperty("USE_SKILL").split(",")).length;
                for (byte b = 0; b < i; ++b) {
                    String S = arrayOfString[b];
                    ConfigGuaji.USE_SKILL.add(Integer.parseInt(S));
                }
            }
            if (set.getProperty("AREA_SKILL") != null) {
                String[] arrayOfString;
                int i = (arrayOfString = set.getProperty("AREA_SKILL").split(",")).length;
                for (byte b = 0; b < i; ++b) {
                    String S = arrayOfString[b];
                    ConfigGuaji.AREA_SKILL.add(Integer.parseInt(S));
                }
            }
            ConfigGuaji.itemname1 = set.getProperty("itemname1", "");
            ConfigGuaji.itemname2 = set.getProperty("itemname2", "");
            ConfigGuaji.itemname3 = set.getProperty("itemname3", "");
            ConfigGuaji.itemname4 = set.getProperty("itemname4", "");
            ConfigGuaji.itemname5 = set.getProperty("itemname5", "");
            ConfigGuaji.itemname6 = set.getProperty("itemname6", "");
            ConfigGuaji.itemname7 = set.getProperty("itemname7", "");
            ConfigGuaji.itemname8 = set.getProperty("itemname8", "");
            ConfigGuaji.itemname9 = set.getProperty("itemname9", "");
            ConfigGuaji.Guaji_tele = Boolean.parseBoolean(set.getProperty("Guaji_tele", "true"));
            ConfigGuaji.Guaji_tele_item = Integer.parseInt(set.getProperty("Guaji_tele_item", "100"));
            ConfigGuaji.Guaji_tele_itemcount = Integer.parseInt(set.getProperty("Guaji_tele_itemcount", "100"));
            ConfigGuaji.guaji = set.getProperty("guaji", "");
            ConfigGuaji.adena = Double.parseDouble(set.getProperty("adena", "0.0"));
            if (ConfigGuaji.adena < 0.0) {
                ConfigGuaji.adena = 0.0;
            }
            ConfigGuaji.guajidrop = Double.parseDouble(set.getProperty("guajidrop", "1.0"));
            if (ConfigGuaji.guajidrop < 0.0) {
                ConfigGuaji.guajidrop = 0.0;
            }
            ConfigGuaji.guajiitem = Integer.parseInt(set.getProperty("guajiitem", "0"));
            ConfigGuaji.itemadena1 = Integer.parseInt(set.getProperty("itemadena1", "100"));
            ConfigGuaji.itemadena2 = Integer.parseInt(set.getProperty("itemadena2", "100"));
            ConfigGuaji.itemadena3 = Integer.parseInt(set.getProperty("itemadena3", "100"));
            ConfigGuaji.itemadena4 = Integer.parseInt(set.getProperty("itemadena4", "100"));
            ConfigGuaji.itemadena5 = Integer.parseInt(set.getProperty("itemadena5", "100"));
            ConfigGuaji.itemadena6 = Integer.parseInt(set.getProperty("itemadena6", "100"));
            ConfigGuaji.itemadena7 = Integer.parseInt(set.getProperty("itemadena7", "100"));
            ConfigGuaji.itemadena8 = Integer.parseInt(set.getProperty("itemadena8", "100"));
            ConfigGuaji.itemadena9 = Integer.parseInt(set.getProperty("itemadena9", "100"));
            ConfigGuaji.N_followmap = toIntArray(set.getProperty("N_followmap", ""), ",");
            ConfigGuaji.itemid1 = Integer.parseInt(set.getProperty("itemid1", "100"));
            ConfigGuaji.itemid2 = Integer.parseInt(set.getProperty("itemid2", "100"));
            ConfigGuaji.itemid3 = Integer.parseInt(set.getProperty("itemid3", "100"));
            ConfigGuaji.itemid4 = Integer.parseInt(set.getProperty("itemid4", "100"));
            ConfigGuaji.itemid5 = Integer.parseInt(set.getProperty("itemid5", "100"));
            ConfigGuaji.itemid6 = Integer.parseInt(set.getProperty("itemid6", "100"));
            ConfigGuaji.itemid7 = Integer.parseInt(set.getProperty("itemid7", "100"));
            ConfigGuaji.itemid8 = Integer.parseInt(set.getProperty("itemid8", "100"));
            ConfigGuaji.itemid9 = Integer.parseInt(set.getProperty("itemid9", "100"));
            ConfigGuaji.Guaji_save = Boolean.parseBoolean(set.getProperty("Guaji_save", "true"));
            ConfigGuaji.Guaji_level = Integer.parseInt(set.getProperty("Guaji_level", "100"));
            ConfigGuaji.GUAJI_ITEM = toIntArray(set.getProperty("GUAJI_ITEM", ""), ",");
            ConfigGuaji.Guaji_map_stopskill = toIntArray(set.getProperty("Guaji_map_stopskill", ""), ",");
            ConfigGuaji.checktimeguaji = Boolean.parseBoolean(set.getProperty("checktimeguaji", "true"));
            ConfigGuaji.followtele = Boolean.parseBoolean(set.getProperty("followtele", "true"));
            ConfigGuaji.guajiexp2 = Double.parseDouble(set.getProperty("guajiexp2", "0.0"));
            ConfigGuaji.Guaji_action = Boolean.parseBoolean(set.getProperty("Guaji_action", "true"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/掛機相關設置表.properties");
        }
        finally {
            set.clear();
        }
    }
    
    public static int[] toIntArray(String text, String type) {
        StringTokenizer st = new StringTokenizer(text, type);
        int[] iReturn = new int[st.countTokens()];
        for (int i = 0; i < iReturn.length; ++i) {
            iReturn[i] = Integer.parseInt(st.nextToken());
        }
        return iReturn;
    }
}
