package com.lineage.config;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ConfigDropSkill
{
    private static final Log _log;
    private static final Map<Integer, ConfigDropSkill.Drop> _drop_msg_list;
    private static ConfigDropSkill _instance;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(ConfigDropSkill.class);
        _drop_msg_list = new HashMap();
        _random = new Random();
    }
    
    public static ConfigDropSkill get() {
        if (ConfigDropSkill._instance == null) {
            ConfigDropSkill._instance = new ConfigDropSkill();
        }
        return ConfigDropSkill._instance;
    }
    
    private ConfigDropSkill() {
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `message訊息_噴技`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("trpe");
                if (id > 5) {
                    String message = rs.getString("message");
                    ConfigDropSkill.Drop msg = new ConfigDropSkill.Drop();
                    msg._type = type;
                    msg._message = message;
                    ConfigDropSkill._drop_msg_list.put(i, msg);
                    ++i;
                }
            }
        }
        catch (SQLException e) {
            ConfigDropSkill._log.error("message訊息_噴技", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        ConfigDropSkill._log.info("message訊息_噴技公告->" + ConfigDropSkill._drop_msg_list.size());
    }
    
    public static void msg(String string1, String string2) {
        if (ConfigDropSkill._drop_msg_list.isEmpty()) {
            return;
        }
        else {
            try {
                int r = ConfigDropSkill._random.nextInt(ConfigDropSkill._drop_msg_list.size());
                String msg = (ConfigDropSkill._drop_msg_list.get((Integer)r))._message;
                if (msg != null) {
                    int type = (ConfigDropSkill._drop_msg_list.get((Integer)r))._type;
                    String out = null;
                    switch (type) {
                        case 0: {
                            out = String.format(msg, string1, string2);
                            break;
                        }
                    }
                    World.get().broadcastPacketToAll(new S_SystemMessage(out));
                }
            }
            catch (Exception e) {
                ConfigDropSkill._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private class Drop
    {
        private int _type;
        private String _message;
        
        /*
        static void access$1(ConfigDropSkill.Drop drop, int type) {
            drop._type = type;
        }
        */
        
        /*
        static void access$2(ConfigDropSkill.Drop drop, String message) {
            drop._message = message;
        }
        */
    }
}
