package com.lineage.config;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ConfigDescs
{
    private static final Log _log;
    private static final Map<Integer, String> _show_desc;
    private static ConfigDescs _instance;
    
    static {
        _log = LogFactory.getLog(ConfigDescs.class);
        _show_desc = new HashMap();
    }
    
    public static ConfigDescs get() {
        if (ConfigDescs._instance == null) {
            ConfigDescs._instance = new ConfigDescs();
        }
        return ConfigDescs._instance;
    }
    
    private ConfigDescs() {
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `message訊息_廣播`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("message");
                ConfigDescs._show_desc.put(id, message);
            }
        }
        catch (SQLException e) {
            ConfigDescs._log.error("message訊息_廣播", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        ConfigDescs._log.info("message訊息_廣播定時公告->" + ConfigDescs._show_desc.size());
    }
    
    public static String getShow(int nameid) {
        try {
            return ConfigDescs._show_desc.get(new Integer(nameid));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static int get_show_size() {
        return ConfigDescs._show_desc.size();
    }
}
