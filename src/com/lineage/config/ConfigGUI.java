package com.lineage.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 专管 GUI / 文案相关配置（UTF-8）
 * 配置文件：./config/gui.properties
 */
public class ConfigGUI {

    private static final Log _log = LogFactory.getLog(ConfigGUI.class);

    /** 伺服器名称（显示在窗口标题等处） */
    public static String GuiServerName = "天堂管理介面";

    /** 载入配置（UTF-8） */
    public static void load() {
        final String path = "./config/gui.properties";
        try (InputStreamReader isr =
                     new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)) {

            Properties p = new Properties();
            p.load(isr);

            GuiServerName = p.getProperty("GuiServerName", GuiServerName).trim();

            _log.info("ConfigGUI loaded. GuiServerName=" + GuiServerName);
        } catch (Exception e) {
            _log.error("Failed to load " + path + "，使用預設值 GuiServerName=" + GuiServerName, e);
        }
    }

    private ConfigGUI() {}
}
