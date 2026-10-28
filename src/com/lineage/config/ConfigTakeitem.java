package com.lineage.config;

import com.lineage.server.serverpackets.S_PacketBoxGree;
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

public class ConfigTakeitem
{
    private static final Log _log;
    private static final Map<Integer, ConfigTakeitem.Drop> _drop_msg_list;
    private static ConfigTakeitem _instance;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(ConfigTakeitem.class);
        _drop_msg_list = new HashMap();
        _random = new Random();
    }
    
    public static ConfigTakeitem get() {
        if (ConfigTakeitem._instance == null) {
            ConfigTakeitem._instance = new ConfigTakeitem();
        }
        return ConfigTakeitem._instance;
    }
    
    private ConfigTakeitem() {
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `message訊息_撿取`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("trpe");
                if (id > 5) {
                    String message = rs.getString("message");
                    ConfigTakeitem.Drop msg = new ConfigTakeitem.Drop();
                    msg._type = type;
                    msg._message = message;
                    ConfigTakeitem._drop_msg_list.put(i, msg);
                    ++i;
                }
            }
        }
        catch (SQLException e) {
            ConfigTakeitem._log.error("message訊息_撿取", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        ConfigTakeitem._log.info("message訊息_撿取公告->" + ConfigTakeitem._drop_msg_list.size());
    }
    
    public static void msg(String string1, String string2, String string3) {
        if (ConfigTakeitem._drop_msg_list.isEmpty()) {
            return;
        }
        else {
            try {
                int r = ConfigTakeitem._random.nextInt(ConfigTakeitem._drop_msg_list.size());
                String msg = (ConfigTakeitem._drop_msg_list.get((Integer)r))._message;
                if (msg != null) {
                    int type = (ConfigTakeitem._drop_msg_list.get((Integer)r))._type;
                    String out = null;
                    switch (type) {
                        case 0: {
                            out = String.format(msg, string1, string2, string3);
                            break;
                        }
                    }
                    if (ConfigOther.takeitem == 0) {
                        World.get().broadcastPacketToAll(new S_SystemMessage(out));
                    }
                    else {
                        World.get().broadcastPacketToAll(new S_PacketBoxGree(2, out));
                    }
                }
            }
            catch (Exception e) {
                ConfigTakeitem._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private class Drop
    {
        private int _type;
        private String _message;
        
        /*
        static void access$1(ConfigTakeitem.Drop drop, int type) {
            drop._type = type;
        }
        */
        
        /*
        static void access$2(ConfigTakeitem.Drop drop, String message) {
            drop._message = message;
        }
        */
    }
}
