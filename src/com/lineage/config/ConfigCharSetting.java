package com.lineage.config;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class ConfigCharSetting
{
    public static int PRINCE_MAX_HP;
    public static int PRINCE_MAX_MP;
    public static int KNIGHT_MAX_HP;
    public static int KNIGHT_MAX_MP;
    public static int ELF_MAX_HP;
    public static int ELF_MAX_MP;
    public static int WIZARD_MAX_HP;
    public static int WIZARD_MAX_MP;
    public static int DARKELF_MAX_HP;
    public static int DARKELF_MAX_MP;
    public static int DRAGONKNIGHT_MAX_HP;
    public static int DRAGONKNIGHT_MAX_MP;
    public static int ILLUSIONIST_MAX_HP;
    public static int ILLUSIONIST_MAX_MP;
    public static int PRINCE_HP;
    public static int KNIGHT_HP;
    public static int ELF_HP;
    public static int WIZARD_HP;
    public static int DARKELF_HP;
    public static int DRAGONKNIGHT_HP;
    public static int ILLUSIONIST_HP;
    private static final String CHAR_SETTINGS_CONFIG_FILE = "./config/charsettings.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/charsettings.properties"));
            set.load(is);
            is.close();
            ConfigCharSetting.PRINCE_MAX_HP = Integer.parseInt(set.getProperty("PrinceMaxHP", "1000"));
            ConfigCharSetting.PRINCE_MAX_MP = Integer.parseInt(set.getProperty("PrinceMaxMP", "800"));
            ConfigCharSetting.KNIGHT_MAX_HP = Integer.parseInt(set.getProperty("KnightMaxHP", "1400"));
            ConfigCharSetting.KNIGHT_MAX_MP = Integer.parseInt(set.getProperty("KnightMaxMP", "600"));
            ConfigCharSetting.ELF_MAX_HP = Integer.parseInt(set.getProperty("ElfMaxHP", "1000"));
            ConfigCharSetting.ELF_MAX_MP = Integer.parseInt(set.getProperty("ElfMaxMP", "900"));
            ConfigCharSetting.WIZARD_MAX_HP = Integer.parseInt(set.getProperty("WizardMaxHP", "800"));
            ConfigCharSetting.WIZARD_MAX_MP = Integer.parseInt(set.getProperty("WizardMaxMP", "1200"));
            ConfigCharSetting.DARKELF_MAX_HP = Integer.parseInt(set.getProperty("DarkelfMaxHP", "1000"));
            ConfigCharSetting.DARKELF_MAX_MP = Integer.parseInt(set.getProperty("DarkelfMaxMP", "900"));
            ConfigCharSetting.DRAGONKNIGHT_MAX_HP = Integer.parseInt(set.getProperty("DragonKnightMaxHP", "1400"));
            ConfigCharSetting.DRAGONKNIGHT_MAX_MP = Integer.parseInt(set.getProperty("DragonKnightMaxMP", "600"));
            ConfigCharSetting.ILLUSIONIST_MAX_HP = Integer.parseInt(set.getProperty("IllusionistMaxHP", "900"));
            ConfigCharSetting.ILLUSIONIST_MAX_MP = Integer.parseInt(set.getProperty("IllusionistMaxMP", "1100"));
            ConfigCharSetting.PRINCE_HP = Integer.parseInt(set.getProperty("PRINCE_HP", "13"));
            ConfigCharSetting.KNIGHT_HP = Integer.parseInt(set.getProperty("KNIGHT_HP", "19"));
            ConfigCharSetting.ELF_HP = Integer.parseInt(set.getProperty("ELF_HP", "12"));
            ConfigCharSetting.WIZARD_HP = Integer.parseInt(set.getProperty("WIZARD_HP", "9"));
            ConfigCharSetting.DARKELF_HP = Integer.parseInt(set.getProperty("DARKELF_HP", "12"));
            ConfigCharSetting.DRAGONKNIGHT_HP = Integer.parseInt(set.getProperty("DRAGONKNIGHT_HP", "18"));
            ConfigCharSetting.ILLUSIONIST_HP = Integer.parseInt(set.getProperty("ILLUSIONIST_HP", "11"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/charsettings.properties");
        }
        finally {
            set.clear();
        }
    }
}
