package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class Configdead
{
    public static int KillTargetlawful;
    public static boolean pkpinkcheck;
    public static boolean deaddropitem;
    public static int deadleveldrop;
    public static int deaditemran1;
    public static int deaditemcount1;
    public static int deaditemran2;
    public static int deaditemcount2;
    public static int deaditemran3;
    public static int deaditemcount3;
    public static int deaditemran4;
    public static int deaditemcount4;
    public static int deaditemran5;
    public static int deaditemcount5;
    public static int deaditemran6;
    public static int deaditemcount6;
    public static int deaditemran7;
    public static int deaditemcount7;
    public static int deaditemran8;
    public static int deaditemcount8;
    public static int deaditemran9;
    public static int deaditemcount9;
    public static boolean deaddropskill;
    public static int deadlevelskill;
    public static int deadskillran1;
    public static int deadskillcount1;
    public static int deadskillran2;
    public static int deadskillcount2;
    public static int deadskillran3;
    public static int deadskillcount3;
    public static int deadskillran4;
    public static int deadskillcount4;
    public static int deadskillran5;
    public static int deadskillcount5;
    public static int deadskillran6;
    public static int deadskillcount6;
    public static int deadskillran7;
    public static int deadskillcount7;
    public static int deadskillran8;
    public static int deadskillcount8;
    public static int deadskillran9;
    public static int deadskillcount9;
    private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/紅人噴裝備技能設置表.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/其他控制端/紅人噴裝備技能設置表.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            Configdead.KillTargetlawful = Integer.parseInt(set.getProperty("KillTargetlawful", "10"));
            Configdead.pkpinkcheck = Boolean.parseBoolean(set.getProperty("pkpinkcheck", "false"));
            Configdead.deaddropitem = Boolean.parseBoolean(set.getProperty("deaddropitem", "false"));
            Configdead.deadleveldrop = Integer.parseInt(set.getProperty("deadleveldrop", "0"));
            Configdead.deaditemran1 = Integer.parseInt(set.getProperty("deaditemran1", "0"));
            Configdead.deaditemcount1 = Integer.parseInt(set.getProperty("deaditemcount1", "0"));
            Configdead.deaditemran2 = Integer.parseInt(set.getProperty("deaditemran2", "0"));
            Configdead.deaditemcount2 = Integer.parseInt(set.getProperty("deaditemcount2", "0"));
            Configdead.deaditemran3 = Integer.parseInt(set.getProperty("deaditemran3", "0"));
            Configdead.deaditemcount3 = Integer.parseInt(set.getProperty("deaditemcount3", "0"));
            Configdead.deaditemran4 = Integer.parseInt(set.getProperty("deaditemran4", "0"));
            Configdead.deaditemcount4 = Integer.parseInt(set.getProperty("deaditemcount4", "0"));
            Configdead.deaditemran5 = Integer.parseInt(set.getProperty("deaditemran5", "0"));
            Configdead.deaditemcount5 = Integer.parseInt(set.getProperty("deaditemcount5", "0"));
            Configdead.deaditemran6 = Integer.parseInt(set.getProperty("deaditemran6", "0"));
            Configdead.deaditemcount6 = Integer.parseInt(set.getProperty("deaditemcount6", "0"));
            Configdead.deaditemran7 = Integer.parseInt(set.getProperty("deaditemran7", "0"));
            Configdead.deaditemcount7 = Integer.parseInt(set.getProperty("deaditemcount7", "0"));
            Configdead.deaditemran8 = Integer.parseInt(set.getProperty("deaditemran8", "0"));
            Configdead.deaditemcount8 = Integer.parseInt(set.getProperty("deaditemcount8", "0"));
            Configdead.deaditemran9 = Integer.parseInt(set.getProperty("deaditemran9", "0"));
            Configdead.deaditemcount9 = Integer.parseInt(set.getProperty("deaditemcount9", "0"));
            Configdead.deaddropskill = Boolean.parseBoolean(set.getProperty("deaddropskill", "false"));
            Configdead.deadlevelskill = Integer.parseInt(set.getProperty("deadlevelskill", "0"));
            Configdead.deadskillran1 = Integer.parseInt(set.getProperty("deadskillran1", "0"));
            Configdead.deadskillcount1 = Integer.parseInt(set.getProperty("deadskillcount1", "0"));
            Configdead.deadskillran2 = Integer.parseInt(set.getProperty("deadskillran2", "0"));
            Configdead.deadskillcount2 = Integer.parseInt(set.getProperty("deadskillcount2", "0"));
            Configdead.deadskillran3 = Integer.parseInt(set.getProperty("deadskillran3", "0"));
            Configdead.deadskillcount3 = Integer.parseInt(set.getProperty("deadskillcount3", "0"));
            Configdead.deadskillran4 = Integer.parseInt(set.getProperty("deadskillran4", "0"));
            Configdead.deadskillcount4 = Integer.parseInt(set.getProperty("deadskillcount4", "0"));
            Configdead.deadskillran5 = Integer.parseInt(set.getProperty("deadskillran5", "0"));
            Configdead.deadskillcount5 = Integer.parseInt(set.getProperty("deadskillcount5", "0"));
            Configdead.deadskillran6 = Integer.parseInt(set.getProperty("deadskillran6", "0"));
            Configdead.deadskillcount6 = Integer.parseInt(set.getProperty("deadskillcount6", "0"));
            Configdead.deadskillran7 = Integer.parseInt(set.getProperty("deadskillran7", "0"));
            Configdead.deadskillcount7 = Integer.parseInt(set.getProperty("deadskillcount7", "0"));
            Configdead.deadskillran8 = Integer.parseInt(set.getProperty("deadskillran8", "0"));
            Configdead.deadskillcount8 = Integer.parseInt(set.getProperty("deadskillcount8", "0"));
            Configdead.deadskillran9 = Integer.parseInt(set.getProperty("deadskillran9", "0"));
            Configdead.deadskillcount9 = Integer.parseInt(set.getProperty("deadskillcount9", "0"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/紅人噴裝備技能設置表.properties");
        }
        finally {
            set.clear();
        }
    }
}
