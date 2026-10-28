package com.lineage.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public final class ConfigSQL
{
    public static String DB_DRIVER;
    public static String DB_URL1_LOGIN;
    public static String DB_URL2_LOGIN;
    public static String DB_URL3_LOGIN;
    public static String DB_LOGIN_LOGIN;
    public static String DB_PASSWORD_LOGIN;
    public static String DB_URL1;
    public static String DB_URL2;
    public static String DB_URL3;
    public static String DB_LOGIN;
    public static String DB_PASSWORD;
    private static final String SQL_CONFIG = "./config/sql.properties";
    
    public static void load() throws ConfigErrorException {
        Properties set = new Properties();
        try {
            InputStream is = new FileInputStream(new File("./config/sql.properties"));
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            set.load(isr);
            is.close();
            ConfigSQL.DB_DRIVER = set.getProperty("Driver", "com.mysql.jdbc.Driver");
            ConfigSQL.DB_URL1_LOGIN = set.getProperty("URL1_LOGIN", "jdbc:mysql://localhost/");
            ConfigSQL.DB_URL2_LOGIN = set.getProperty("URL2_LOGIN", "l1jsrc");
            ConfigSQL.DB_URL3_LOGIN = set.getProperty("URL3_LOGIN", "?useUnicode=true&characterEncoding=UTF8");
            ConfigSQL.DB_LOGIN_LOGIN = set.getProperty("Login_LOGIN", "root");
            ConfigSQL.DB_PASSWORD_LOGIN = set.getProperty("Password_LOGIN", "123456");
            ConfigSQL.DB_URL1 = set.getProperty("URL1", "jdbc:mysql://localhost/");
            ConfigSQL.DB_URL2 = set.getProperty("URL2", "l1jsrc");
            ConfigSQL.DB_URL3 = set.getProperty("URL3", "?useUnicode=true&characterEncoding=UTF8");
            ConfigSQL.DB_LOGIN = set.getProperty("Login", "root");
            ConfigSQL.DB_PASSWORD = set.getProperty("Password", "123456");
        }
        catch (Exception e) {
            throw new ConfigErrorException("設置檔案遺失: ./config/sql.properties");
        }
        finally {
            set.clear();
        }
    }
}
