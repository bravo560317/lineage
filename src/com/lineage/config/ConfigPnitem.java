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

public class ConfigPnitem
{
    private static final Log _log;
    private static final Map<Integer, ConfigPnitem.Drop> _drop_msg_list;
    private static ConfigPnitem _instance;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(ConfigPnitem.class);
        _drop_msg_list = new HashMap();
        _random = new Random();
    }
    
    public static ConfigPnitem get() {
        if (ConfigPnitem._instance == null) {
            ConfigPnitem._instance = new ConfigPnitem();
        }
        return ConfigPnitem._instance;
    }
    
    private ConfigPnitem() {
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `message訊息_噴裝`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("trpe");
                if (id > 5) {
                    String message = rs.getString("message");
                    ConfigPnitem.Drop msg = new ConfigPnitem.Drop();
                    msg._type = type;
                    msg._message = message;
                    ConfigPnitem._drop_msg_list.put(i, msg);
                    ++i;
                }
            }
        }
        catch (SQLException e) {
            ConfigPnitem._log.error("message訊息_噴裝", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        ConfigPnitem._log.info("message訊息_噴裝公告->" + ConfigPnitem._drop_msg_list.size());
    }
    
    public static void msg(String string1, String string2) {
        if (ConfigPnitem._drop_msg_list.isEmpty()) {
            return;
        }
        else {
            try {
                int r = ConfigPnitem._random.nextInt(ConfigPnitem._drop_msg_list.size());
                String msg = (ConfigPnitem._drop_msg_list.get((Integer)r))._message;
                if (ConfigOther.pnitem == 0) {
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format(msg, string1, string2)));
                }
                else {
                    World.get().broadcastPacketToAll(new S_PacketBoxGree(2, String.format(msg, string1, string2)));
                }
            }
            catch (Exception e) {
                ConfigPnitem._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private class Drop
    {
        private int _type;
        private String _message;
        
        /*
        static void access$1(ConfigPnitem.Drop drop, int type) {
            drop._type = type;
        }
        */
        
        /*
        static void access$2(ConfigPnitem.Drop drop, String message) {
            drop._message = message;
        }
        */
    }
}
