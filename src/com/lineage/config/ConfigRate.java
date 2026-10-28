package com.lineage.config;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class ConfigRate
{
    public static int ran1;
    public static int ran2;
    public static int ran3;
    public static int ran4;
    public static int ran5;
    public static int ran6;
    public static int ran7;
    public static int ran8;
    public static int ran9;
    public static int ran10;
    public static int ran11;
    public static int ran12;
    public static int ran13;
    public static int ran14;
    public static double RATE_XP; // 當前伺服器運行的經驗倍率
    public static double RATE_XP_1; // 星期一
    public static double RATE_XP_2; // 星期二
    public static double RATE_XP_3; // 星期三
    public static double RATE_XP_4; // 星期四
    public static double RATE_XP_5; // 星期五
    public static double RATE_XP_6; // 星期六
    public static double RATE_XP_7; // 星期日
    public static double RATE_LA;
    public static double RATE_KARMA;
    public static double RATE_DROP_ADENA; // 當前伺服器運行的金幣倍率
    public static double RATE_DROP_ADENA_1; // 星期一
    public static double RATE_DROP_ADENA_2; // 星期二
    public static double RATE_DROP_ADENA_3; // 星期三
    public static double RATE_DROP_ADENA_4; // 星期四
    public static double RATE_DROP_ADENA_5; // 星期五
    public static double RATE_DROP_ADENA_6; // 星期六
    public static double RATE_DROP_ADENA_7; // 星期日
    public static double RATE_DROP_ITEMS; // 當前伺服器運行的掉寶倍率
    public static double RATE_DROP_ITEMS_1; // 星期一
    public static double RATE_DROP_ITEMS_2; // 星期二
    public static double RATE_DROP_ITEMS_3; // 星期三
    public static double RATE_DROP_ITEMS_4; // 星期四
    public static double RATE_DROP_ITEMS_5; // 星期五
    public static double RATE_DROP_ITEMS_6; // 星期六
    public static double RATE_DROP_ITEMS_7; // 星期日
    public static int ENCHANT_CHANCE_WEAPON;
    public static int ENCHANT_CHANCE_ARMOR;
    public static int ATTR_ENCHANT_CHANCE;
    public static double RATE_WEIGHT_LIMIT;
    public static double RATE_WEIGHT_LIMIT_PET;
    public static double RATE_SHOP_SELLING_PRICE;
    public static double RATE_SHOP_PURCHASING_PRICE;
    public static int CREATE_CHANCE_DIARY;
    public static int CREATE_CHANCE_RECOLLECTION;
    public static int CREATE_CHANCE_MYSTERIOUS;
    public static int CREATE_CHANCE_PROCESSING;
    public static int CREATE_CHANCE_PROCESSING_DIAMOND;
    public static int CREATE_CHANCE_DANTES;
    public static int CREATE_CHANCE_ANCIENT_AMULET;
    public static int CREATE_CHANCE_HISTORY_BOOK;
    public static boolean dolllength;
    public static boolean DeleItemlog;
    public static boolean MagicNpcDamage;
    public static boolean PetNpcDamage;
    private static final String RATES_CONFIG_FILE = "./config/rates.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/rates.properties"));
            set.load(is);
            is.close();
            ConfigRate.RATE_XP_1 = Double.parseDouble(set.getProperty("RateXp1", "600.0"));
            ConfigRate.RATE_XP_2 = Double.parseDouble(set.getProperty("RateXp2", "600.0"));
            ConfigRate.RATE_XP_3 = Double.parseDouble(set.getProperty("RateXp3", "600.0"));
            ConfigRate.RATE_XP_4 = Double.parseDouble(set.getProperty("RateXp4", "600.0"));
            ConfigRate.RATE_XP_5 = Double.parseDouble(set.getProperty("RateXp5", "600.0"));
            ConfigRate.RATE_XP_6 = Double.parseDouble(set.getProperty("RateXp6", "600.0"));
            ConfigRate.RATE_XP_7 = Double.parseDouble(set.getProperty("RateXp7", "600.0"));
            ConfigRate.RATE_LA = Double.parseDouble(set.getProperty("RateLawful", "1.0"));
            ConfigRate.RATE_KARMA = Double.parseDouble(set.getProperty("RateKarma", "1.0"));
            ConfigRate.RATE_DROP_ADENA_1 = Double.parseDouble(set.getProperty("RateDropAdena1", "1.0"));
            ConfigRate.RATE_DROP_ADENA_2 = Double.parseDouble(set.getProperty("RateDropAdena2", "1.0"));
            ConfigRate.RATE_DROP_ADENA_3 = Double.parseDouble(set.getProperty("RateDropAdena3", "1.0"));
            ConfigRate.RATE_DROP_ADENA_4 = Double.parseDouble(set.getProperty("RateDropAdena4", "1.0"));
            ConfigRate.RATE_DROP_ADENA_5 = Double.parseDouble(set.getProperty("RateDropAdena5", "1.0"));
            ConfigRate.RATE_DROP_ADENA_6 = Double.parseDouble(set.getProperty("RateDropAdena6", "1.0"));
            ConfigRate.RATE_DROP_ADENA_7 = Double.parseDouble(set.getProperty("RateDropAdena7", "1.0"));
            ConfigRate.RATE_DROP_ITEMS_1 = Double.parseDouble(set.getProperty("RateDropItems1", "1.0"));
            ConfigRate.RATE_DROP_ITEMS_2 = Double.parseDouble(set.getProperty("RateDropItems2", "1.0"));
            ConfigRate.RATE_DROP_ITEMS_3 = Double.parseDouble(set.getProperty("RateDropItems3", "1.0"));
            ConfigRate.RATE_DROP_ITEMS_4 = Double.parseDouble(set.getProperty("RateDropItems4", "1.0"));
            ConfigRate.RATE_DROP_ITEMS_5 = Double.parseDouble(set.getProperty("RateDropItems5", "1.0"));
            ConfigRate.RATE_DROP_ITEMS_6 = Double.parseDouble(set.getProperty("RateDropItems6", "1.0"));
            ConfigRate.RATE_DROP_ITEMS_7 = Double.parseDouble(set.getProperty("RateDropItems7", "1.0"));
            ConfigRate.RATE_DROP_ADENA = Double.parseDouble(set.getProperty("RateDropAdena", "1.0")); // 為了相容舊設定，保留讀取
            ConfigRate.RATE_DROP_ITEMS = Double.parseDouble(set.getProperty("RateDropItems", "1.0")); // 為了相容舊設定，保留讀取
            ConfigRate.ENCHANT_CHANCE_WEAPON = Integer.parseInt(set.getProperty("EnchantChanceWeapon", "68"));
            ConfigRate.ENCHANT_CHANCE_ARMOR = Integer.parseInt(set.getProperty("EnchantChanceArmor", "52"));
            ConfigRate.ATTR_ENCHANT_CHANCE = Integer.parseInt(set.getProperty("AttrEnchantChance", "10"));
            ConfigRate.RATE_WEIGHT_LIMIT = Double.parseDouble(set.getProperty("RateWeightLimit", "1"));
            ConfigRate.RATE_WEIGHT_LIMIT_PET = Double.parseDouble(set.getProperty("RateWeightLimitforPet", "1"));
            ConfigRate.RATE_SHOP_SELLING_PRICE = Double.parseDouble(set.getProperty("RateShopSellingPrice", "1.0"));
            ConfigRate.RATE_SHOP_PURCHASING_PRICE = Double.parseDouble(set.getProperty("RateShopPurchasingPrice", "1.0"));
            ConfigRate.CREATE_CHANCE_DIARY = Integer.parseInt(set.getProperty("CreateChanceDiary", "33"));
            ConfigRate.CREATE_CHANCE_RECOLLECTION = Integer.parseInt(set.getProperty("CreateChanceRecollection", "90"));
            ConfigRate.CREATE_CHANCE_MYSTERIOUS = Integer.parseInt(set.getProperty("CreateChanceMysterious", "90"));
            ConfigRate.CREATE_CHANCE_PROCESSING = Integer.parseInt(set.getProperty("CreateChanceProcessing", "90"));
            ConfigRate.CREATE_CHANCE_PROCESSING_DIAMOND = Integer.parseInt(set.getProperty("CreateChanceProcessingDiamond", "90"));
            ConfigRate.CREATE_CHANCE_DANTES = Integer.parseInt(set.getProperty("CreateChanceDantes", "50"));
            ConfigRate.CREATE_CHANCE_ANCIENT_AMULET = Integer.parseInt(set.getProperty("CreateChanceAncientAmulet", "90"));
            ConfigRate.CREATE_CHANCE_HISTORY_BOOK = Integer.parseInt(set.getProperty("CreateChanceHistoryBook", "5"));
            ConfigRate.ran1 = Integer.parseInt(set.getProperty("ran1", "10"));
            ConfigRate.ran2 = Integer.parseInt(set.getProperty("ran2", "10"));
            ConfigRate.ran3 = Integer.parseInt(set.getProperty("ran3", "10"));
            ConfigRate.ran4 = Integer.parseInt(set.getProperty("ran4", "10"));
            ConfigRate.ran5 = Integer.parseInt(set.getProperty("ran5", "10"));
            ConfigRate.ran6 = Integer.parseInt(set.getProperty("ran6", "10"));
            ConfigRate.ran7 = Integer.parseInt(set.getProperty("ran7", "10"));
            ConfigRate.ran8 = Integer.parseInt(set.getProperty("ran8", "10"));
            ConfigRate.ran9 = Integer.parseInt(set.getProperty("ran9", "10"));
            ConfigRate.ran10 = Integer.parseInt(set.getProperty("ran10", "10"));
            ConfigRate.ran11 = Integer.parseInt(set.getProperty("ran11", "10"));
            ConfigRate.ran12 = Integer.parseInt(set.getProperty("ran12", "10"));
            ConfigRate.ran13 = Integer.parseInt(set.getProperty("ran13", "10"));
            ConfigRate.ran14 = Integer.parseInt(set.getProperty("ran14", "10"));
            ConfigRate.dolllength = Integer.parseInt(set.getProperty("dolllength", "0")) == 1; // 1=True, 0=False娃娃合成判定 0:4張不同  1:無限制
            ConfigRate.DeleItemlog = Integer.parseInt(set.getProperty("DeleItemlog", "0")) == 1; // 1=True, 0=False紀錄_角色_刪物 0:啟用  1:關閉
            ConfigRate.MagicNpcDamage = Integer.parseInt(set.getProperty("MagicNpcDamage", "0")) == 1; // 1=True, 0=Falsew_怪物擊殺系統魔法傷害 0:啟用  1:關閉
            ConfigRate.PetNpcDamage = Integer.parseInt(set.getProperty("PetNpcDamage", "0")) == 1; // 1=True, 0=Falsew_怪物擊殺系統寵物傷害 0:啟用  1:關閉

        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/rates.properties");
        }
        finally {
            set.clear();
        }
    }
}
