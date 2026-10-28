package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class ConfigClan
{
    public static int clanlv1;
    public static int clanlv2;
    public static int clanlv3;
    public static int clanlv4;
    public static int clanlv5;
    public static int clanlv6;
    public static int clanlv7;
    public static int clanlv8;
    public static int clanlv9;
    public static int clanlv10;
    public static int clanlvcount1;
    public static int clanlvcount2;
    public static int clanlvcount3;
    public static int clanlvcount4;
    public static int clanlvcount5;
    public static int clanlvcount6;
    public static int clanlvcount7;
    public static int clanlvcount8;
    public static int clanlvcount9;
    public static int clanlvcount10;
    public static int clanenergy1;
    public static int clanenergy2;
    public static int clanenergy3;
    public static int clanenergy4;
    public static int clanenergy5;
    public static int clanenergy6;
    public static int clanenergy7;
    public static int clanenergy8;
    public static int clanenergy9;
    public static int clanenergy10;
    public static int clanlevel;
    public static String clanmsg1;
    public static boolean clandelt;
    public static boolean clanskill;
    public static int clanresttime;
    public static int PcContribution;
    public static int PcClanAdena;
    public static int ClanItem44070;
    public static int ClanContribution;
    public static int ClanItem44070_1;
    public static int ClanContribution_1;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/血盟等級設定表.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/血盟等級設定表.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigClan.clanlv1 = Integer.parseInt(set.getProperty("clanlv1", "99999"));
            ConfigClan.clanlv2 = Integer.parseInt(set.getProperty("clanlv2", "99999"));
            ConfigClan.clanlv3 = Integer.parseInt(set.getProperty("clanlv3", "0"));
            ConfigClan.clanlv4 = Integer.parseInt(set.getProperty("clanlv4", "0"));
            ConfigClan.clanlv5 = Integer.parseInt(set.getProperty("clanlv5", "0"));
            ConfigClan.clanlv6 = Integer.parseInt(set.getProperty("clanlv6", "0"));
            ConfigClan.clanlv7 = Integer.parseInt(set.getProperty("clanlv7", "0"));
            ConfigClan.clanlv8 = Integer.parseInt(set.getProperty("clanlv8", "0"));
            ConfigClan.clanlv9 = Integer.parseInt(set.getProperty("clanlv9", "0"));
            ConfigClan.clanlv10 = Integer.parseInt(set.getProperty("clanlv10", "0"));
            ConfigClan.clanlvcount1 = Integer.parseInt(set.getProperty("clanlvcount1", "999999"));
            ConfigClan.clanlvcount2 = Integer.parseInt(set.getProperty("clanlvcount2", "999999"));
            ConfigClan.clanlvcount3 = Integer.parseInt(set.getProperty("clanlvcount3", "0"));
            ConfigClan.clanlvcount4 = Integer.parseInt(set.getProperty("clanlvcount4", "0"));
            ConfigClan.clanlvcount5 = Integer.parseInt(set.getProperty("clanlvcount5", "0"));
            ConfigClan.clanlvcount6 = Integer.parseInt(set.getProperty("clanlvcount6", "0"));
            ConfigClan.clanlvcount7 = Integer.parseInt(set.getProperty("clanlvcount7", "0"));
            ConfigClan.clanlvcount8 = Integer.parseInt(set.getProperty("clanlvcount8", "0"));
            ConfigClan.clanlvcount9 = Integer.parseInt(set.getProperty("clanlvcount9", "0"));
            ConfigClan.clanlvcount10 = Integer.parseInt(set.getProperty("clanlvcount10", "0"));
            ConfigClan.clanenergy1 = Integer.parseInt(set.getProperty("clanenergy1", "5000"));
            ConfigClan.clanenergy2 = Integer.parseInt(set.getProperty("clanenergy2", "0"));
            ConfigClan.clanenergy3 = Integer.parseInt(set.getProperty("clanenergy3", "0"));
            ConfigClan.clanenergy4 = Integer.parseInt(set.getProperty("clanenergy4", "0"));
            ConfigClan.clanenergy5 = Integer.parseInt(set.getProperty("clanenergy5", "0"));
            ConfigClan.clanenergy6 = Integer.parseInt(set.getProperty("clanenergy6", "0"));
            ConfigClan.clanenergy7 = Integer.parseInt(set.getProperty("clanenergy7", "0"));
            ConfigClan.clanenergy8 = Integer.parseInt(set.getProperty("clanenergy8", "0"));
            ConfigClan.clanenergy9 = Integer.parseInt(set.getProperty("clanenergy9", "0"));
            ConfigClan.clanenergy10 = Integer.parseInt(set.getProperty("clanenergy10", "0"));
            ConfigClan.clanlevel = Integer.parseInt(set.getProperty("clanlevel", "0"));
            ConfigClan.clanmsg1 = set.getProperty("clanmsg1", "");
            ConfigClan.PcClanAdena = Integer.parseInt(set.getProperty("PcClanAdena", "0"));
            ConfigClan.ClanItem44070 = Integer.parseInt(set.getProperty("ClanItem44070", "0"));
            ConfigClan.ClanContribution = Integer.parseInt(set.getProperty("ClanContribution", "0"));
            ConfigClan.ClanItem44070_1 = Integer.parseInt(set.getProperty("ClanItem44070_1", "0"));
            ConfigClan.ClanContribution_1 = Integer.parseInt(set.getProperty("ClanContribution_1", "0"));
            ConfigClan.clandelt = Boolean.parseBoolean(set.getProperty("clandelt", "true"));
            ConfigClan.clanskill = Boolean.parseBoolean(set.getProperty("clanskill", "true"));
            ConfigClan.clanresttime = Integer.parseInt(set.getProperty("clanresttime", "0"));
            ConfigClan.PcContribution = Integer.parseInt(set.getProperty("PcContribution", "0"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/血盟等級設定表.properties");
        }
        finally {
            set.clear();
        }
    }
}
