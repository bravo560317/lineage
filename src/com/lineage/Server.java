package com.lineage;

import java.io.InputStream;
import com.lineage.server.GameServer;
import com.lineage.commons.system.LanSecurityManager;
import com.eric.gui.J_Main;
import com.lineage.list.Announcements;
import com.lineage.server.utils.DBClearAllUtil;
import com.lineage.config.ConfigOrAccessory;
import com.lineage.config.ConfigAccessory;
import com.lineage.config.ConfigServer;
import com.lineage.config.ConfigItem;
import com.lineage.config.ConfigQuest;
import com.lineage.config.ConfigPnitem;
import com.lineage.config.ConfigTakeitem;
import com.lineage.config.ConfigDescs;
import com.lineage.config.ConfigDropSkill;
import com.lineage.config.ConfigGUI;
import com.lineage.config.ConfigDrop;
import com.lineage.config.ConfigDoll;
import com.lineage.config.Configpoly;
import com.lineage.config.ConfigKill;
import com.lineage.config.ConfigBoxs;
import com.lineage.config.Configdead;
import com.lineage.config.ConfigGuaji;
import com.lineage.config.ConfigClan;
import com.lineage.config.ConfigIpCheck;
import com.lineage.config.Configtf;
import com.lineage.config.ConfigClastle;
import com.lineage.config.ConfigAi;
import com.lineage.config.Configtype;
import com.lineage.config.ConfigBad;
import com.lineage.config.ConfigWho;
import com.lineage.config.ConfigRecord;
import com.lineage.config.ConfigSQL;
import com.lineage.config.ConfigRate;
import com.lineage.config.Config_Pc_Damage;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigCharSetting;
import com.lineage.config.ConfigAlt;
import com.lineage.config.Config;
import org.apache.log4j.PropertyConfigurator;
import java.util.logging.LogManager;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.File;
import com.lineage.server.datatables.lock.VIPReading;

public class Server
{
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to lineage game line: 秘密天堂");
        CompressFile bean = new CompressFile();
        try {
            File file = new File("./back");
            if (!file.exists()) {
                file.mkdir();
            }
            String nowDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            bean.zip("./loginfo", "./back/" + nowDate + ".zip");
            File loginfofile = new File("./loginfo");
            String[] loginfofileList = loginfofile.list();
            String[] array;
            for (int length = (array = loginfofileList).length, i = 0; i < length; ++i) {
                String fileName = array[i];
                File readfile = new File("./loginfo/" + fileName);
                if (readfile.exists() && !readfile.isDirectory()) {
                    readfile.delete();
                }
            }
        }
        catch (IOException e) {
            System.out.println("資料夾不存在: ./back 已經自動建立!");
        }
        boolean error = false;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream("./config/logging.properties"));
            LogManager.getLogManager().readConfiguration(is);
            is.close();
        }
        catch (IOException e2) {
            System.out.println("檔案遺失: ./config/logging.properties");
            error = true;
        }
        try {
            PropertyConfigurator.configure("./config/log4j.properties");
        }
        catch (Exception e3) {
            System.out.println("檔案遺失: ./config/log4j.properties");
            System.exit(0);
        }
        try {
        	ConfigGUI.load();
            Config.load();
            ConfigAlt.load();
            ConfigCharSetting.load();
            ConfigOther.load();
            Config_Pc_Damage.load();
            ConfigRate.load();
            ConfigSQL.load();
            ConfigRecord.load();
            ConfigWho.load();
            ConfigBad.load();
            Configtype.load();
            ConfigAi.load();
            ConfigClastle.load();
            Configtf.load();
            ConfigIpCheck.load();
            ConfigClan.load();
            ConfigGuaji.load();
            Configdead.load();
        }
        catch (Exception e) {
            System.out.println("CONFIG 資料加載異常!" + e);
            error = true;
        }
        if (!error) {
            // 根據星期設定經驗倍率
            Calendar cal = Calendar.getInstance();
            // 注意：Calendar 的星期日是 1，星期一是 2，... 星期六是 7
            String dayOfWeekStr = "";
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case Calendar.MONDAY: // 星期一
                    dayOfWeekStr = "(星期一)";
                    ConfigRate.RATE_XP = ConfigRate.RATE_XP_1;
                    ConfigRate.RATE_DROP_ITEMS = ConfigRate.RATE_DROP_ITEMS_1;
                    ConfigRate.RATE_DROP_ADENA = ConfigRate.RATE_DROP_ADENA_1;
                    break;
                case Calendar.TUESDAY: // 星期二
                    dayOfWeekStr = "(星期二)";
                    ConfigRate.RATE_XP = ConfigRate.RATE_XP_2;
                    ConfigRate.RATE_DROP_ITEMS = ConfigRate.RATE_DROP_ITEMS_2;
                    ConfigRate.RATE_DROP_ADENA = ConfigRate.RATE_DROP_ADENA_2;
                    break;
                case Calendar.WEDNESDAY: // 星期三
                    dayOfWeekStr = "(星期三)";
                    ConfigRate.RATE_XP = ConfigRate.RATE_XP_3;
                    ConfigRate.RATE_DROP_ITEMS = ConfigRate.RATE_DROP_ITEMS_3;
                    ConfigRate.RATE_DROP_ADENA = ConfigRate.RATE_DROP_ADENA_3;
                    break;
                case Calendar.THURSDAY: // 星期四
                    dayOfWeekStr = "(星期四)";
                    ConfigRate.RATE_XP = ConfigRate.RATE_XP_4;
                    ConfigRate.RATE_DROP_ITEMS = ConfigRate.RATE_DROP_ITEMS_4;
                    ConfigRate.RATE_DROP_ADENA = ConfigRate.RATE_DROP_ADENA_4;
                    break;
                case Calendar.FRIDAY: // 星期五
                    dayOfWeekStr = "(星期五)";
                    ConfigRate.RATE_XP = ConfigRate.RATE_XP_5;
                    ConfigRate.RATE_DROP_ITEMS = ConfigRate.RATE_DROP_ITEMS_5;
                    ConfigRate.RATE_DROP_ADENA = ConfigRate.RATE_DROP_ADENA_5;
                    break;
                case Calendar.SATURDAY: // 星期六
                    dayOfWeekStr = "(星期六)";
                    ConfigRate.RATE_XP = ConfigRate.RATE_XP_6;
                    ConfigRate.RATE_DROP_ITEMS = ConfigRate.RATE_DROP_ITEMS_6;
                    ConfigRate.RATE_DROP_ADENA = ConfigRate.RATE_DROP_ADENA_6;
                    break;
                case Calendar.SUNDAY: // 星期日
                    dayOfWeekStr = "(星期日)";
                    ConfigRate.RATE_XP = ConfigRate.RATE_XP_7;
                    ConfigRate.RATE_DROP_ITEMS = ConfigRate.RATE_DROP_ITEMS_7;
                    ConfigRate.RATE_DROP_ADENA = ConfigRate.RATE_DROP_ADENA_7;
                    break;
                default:
                    // 若有意外情況，使用週一的倍率作為預設值
                    dayOfWeekStr = "(星期一)";
                    ConfigRate.RATE_XP = ConfigRate.RATE_XP_1;
                    ConfigRate.RATE_DROP_ITEMS = ConfigRate.RATE_DROP_ITEMS_1;
                    ConfigRate.RATE_DROP_ADENA = ConfigRate.RATE_DROP_ADENA_1;
                    break;
            }
            System.out.println("-------------------------------------------------- ");
            System.out.println("2024[Lineage3.81]版本(天堂架設10001版本)");
            System.out.println("-------------------------------------------------- ");
            System.out.println("讀取設定中...");
            Thread.sleep(2000L);
            if (error) {
                System.exit(0);
            }
            System.out.println("經驗值倍率: " + ConfigRate.RATE_XP + " 倍" + dayOfWeekStr);
            System.out.println("虛假經驗值倍率: " + (ConfigRate.RATE_XP * ConfigWho.RATE_XP_WHO) + " 倍" + dayOfWeekStr);
            System.out.println("掉寶倍率: " + ConfigRate.RATE_DROP_ITEMS + " 倍" + dayOfWeekStr);
            System.out.println("金幣倍率: " + ConfigRate.RATE_DROP_ADENA + " 倍" + dayOfWeekStr);
        }
        if (error) {
            System.exit(0);
        }
        DatabaseFactoryLogin.setDatabaseSettings();
        DatabaseFactory.setDatabaseSettings();
//      DatabaseFactoryIp.setDatabaseSettings();
        DatabaseFactoryLogin.get();
        DatabaseFactory.get();
//      DatabaseFactoryIp.get();
        ConfigBoxs.get();
        ConfigKill.get();
        ConfigDrop.get();
        ConfigDropSkill.get();
        ConfigDescs.get();
        ConfigTakeitem.get();
        ConfigPnitem.get();
        ConfigQuest.load();
        ConfigItem.load();
        ConfigServer.loadDB();
        ConfigAccessory.load();
        ConfigOrAccessory.load();
        Configpoly.load();
        ConfigDoll.load();
        if (ConfigServer.DBClearAll) {
            DBClearAllUtil.start();
        }
        if (Config.NEWS) {
            Announcements.get();
        }
        if (Config.GUI) {
            J_Main.getInstance().setVisible(true);
        }
        //LanSecurityManager securityManager = new LanSecurityManager();
        //System.setSecurityManager(securityManager);

        // ★ 在這裡補上一行，讓伺服器開機時自動把資料庫的 VIP 資料載入到記憶體中！
        com.lineage.server.datatables.sql.VIPTable.get().load();

        String osname = System.getProperties().getProperty("os.name");
        if (osname.lastIndexOf("Linux") != -1) {
            Config.ISUBUNTU = true;
        }
        GameServer.getInstance().initialize();
    }
}
