package com.lineage.config;

import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_BoxMessage;
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

public class ConfigDrop
{
    private static final Log _log;
    private static final Map<Integer, ConfigDrop.Drop> _drop_msg_list;
    private static ConfigDrop _instance;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(ConfigDrop.class);
        _drop_msg_list = new HashMap();
        _random = new Random();
    }
    
    public static ConfigDrop get() {
        if (ConfigDrop._instance == null) {
            ConfigDrop._instance = new ConfigDrop();
        }
        return ConfigDrop._instance;
    }
    
    private ConfigDrop() {
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `message訊息_掉寶`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("trpe");
                if (id > 5) {
                    String message = rs.getString("message");
                    ConfigDrop.Drop msg = new ConfigDrop.Drop();
                    msg._type = type;
                    msg._message = message;
                    ConfigDrop._drop_msg_list.put(i, msg);
                    ++i;
                }
            }
        }
        catch (SQLException e) {
            ConfigDrop._log.error("message訊息_掉寶", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        ConfigDrop._log.info("message訊息_掉寶公告->" + ConfigDrop._drop_msg_list.size());
    }
    
    /**
     * 掉寶廣播
     * @param string1 玩家名稱
     * @param string2 怪物名稱
     * @param string3 物品名稱
     * @param string4 武器名稱(或其他字串)
     */
    public static void msg(final String string1, final String string2, final String string3, final String string4) {
        if (ConfigDrop._drop_msg_list.isEmpty()) {
            return;
        }
        else {
            try {
                int r = ConfigDrop._random.nextInt(ConfigDrop._drop_msg_list.size());
                String msg = (ConfigDrop._drop_msg_list.get((Integer)r))._message;
                if (msg != null) {
                    int type = (ConfigDrop._drop_msg_list.get((Integer)r))._type;
                    String out = null;
                    switch (type) {
                        case 0: {
                            out = String.format(msg, string1, string2, string3);
                            break;
                        }
                        case 1: {
                            out = String.format(msg, string1, string2, string3);
                            break;
                        }
                        case 2: {
                            out = String.format(msg, string1, string4, string2, string3);
                            break;
                        }
                        case 3: {
                            out = String.format(msg, string2, string1, string4, string3);
                            break;
                        }
                    }
                    if (ConfigOther.dropmsg == 0) {
                        World.get().broadcastPacketToAll(new S_SystemMessage(out));
                    }
                    else {
                        World.get().broadcastPacketToAll(new S_PacketBoxGree(2, out));
                    }
                }
            }
            catch (Exception e) {
                ConfigDrop._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private class Drop
    {
        private int _type;
        private String _message;
        
        /*
        static void access$1(ConfigDrop.Drop drop, int type) {
            drop._type = type;
        }
        */
        
        /*
        static void access$2(ConfigDrop.Drop drop, String message) {
            drop._message = message;
        }
        */
    }
}
