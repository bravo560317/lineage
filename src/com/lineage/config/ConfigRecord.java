package com.lineage.config;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class ConfigRecord
{
    public static boolean LOGGING_BAN_ENCHANT;
    public static boolean LOGGING_CHAT_NORMAL;
    public static boolean LOGGING_CHAT_SHOUT;
    public static boolean LOGGING_CHAT_WORLD;
    public static boolean LOGGING_CHAT_CLAN;
    public static boolean LOGGING_CHAT_WHISPER;
    public static boolean LOGGING_CHAT_PARTY;
    public static boolean LOGGING_CHAT_BUSINESS;
    public static boolean LOGGING_CHAT_COMBINED;
    public static boolean LOGGING_CHAT_CHAT_PARTY;
    private static final String RECORD_FILE = "./config/record.properties";
    
    static {
        ConfigRecord.LOGGING_BAN_ENCHANT = false;
        ConfigRecord.LOGGING_CHAT_NORMAL = false;
        ConfigRecord.LOGGING_CHAT_SHOUT = false;
        ConfigRecord.LOGGING_CHAT_WORLD = false;
        ConfigRecord.LOGGING_CHAT_CLAN = false;
        ConfigRecord.LOGGING_CHAT_WHISPER = false;
        ConfigRecord.LOGGING_CHAT_PARTY = false;
        ConfigRecord.LOGGING_CHAT_BUSINESS = false;
        ConfigRecord.LOGGING_CHAT_COMBINED = false;
        ConfigRecord.LOGGING_CHAT_CHAT_PARTY = false;
    }
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/record.properties"));
            set.load(is);
            is.close();
            ConfigRecord.LOGGING_BAN_ENCHANT = Boolean.parseBoolean(set.getProperty("LoggingBanEnchant", "false"));
            ConfigRecord.LOGGING_CHAT_NORMAL = Boolean.parseBoolean(set.getProperty("LoggingChatNormal", "false"));
            ConfigRecord.LOGGING_CHAT_SHOUT = Boolean.parseBoolean(set.getProperty("LoggingChatShout", "false"));
            ConfigRecord.LOGGING_CHAT_WORLD = Boolean.parseBoolean(set.getProperty("LoggingChatWorld", "false"));
            ConfigRecord.LOGGING_CHAT_CLAN = Boolean.parseBoolean(set.getProperty("LoggingChatClan", "false"));
            ConfigRecord.LOGGING_CHAT_WHISPER = Boolean.parseBoolean(set.getProperty("LoggingChatWhisper", "false"));
            ConfigRecord.LOGGING_CHAT_PARTY = Boolean.parseBoolean(set.getProperty("LoggingChatParty", "false"));
            ConfigRecord.LOGGING_CHAT_BUSINESS = Boolean.parseBoolean(set.getProperty("LoggingBusiness", "false"));
            ConfigRecord.LOGGING_CHAT_COMBINED = Boolean.parseBoolean(set.getProperty("LoggingChatCombined", "false"));
            ConfigRecord.LOGGING_CHAT_CHAT_PARTY = Boolean.parseBoolean(set.getProperty("LoggingChatChatParty", "false"));
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/record.properties");
        }
        finally {
            set.clear();
        }
    }
}
