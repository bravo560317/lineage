package com.lineage.config;

import com.lineage.server.serverpackets.S_PacketBoxGree;
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

public class ConfigBoxs
{
    private static final Log _log;
    private static final Map<Integer, ConfigBoxs.Box> _box_msg_list;
    private static ConfigBoxs _instance;
    private static final Random _random;
    
    static {
        _log = LogFactory.getLog(ConfigBoxs.class);
        _box_msg_list = new HashMap();
        _random = new Random();
    }
    
    public static ConfigBoxs get() {
        if (ConfigBoxs._instance == null) {
            ConfigBoxs._instance = new ConfigBoxs();
        }
        return ConfigBoxs._instance;
    }
    
    private ConfigBoxs() {
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `message訊息_寶相`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("trpe");
                if (id > 5) {
                    String message = rs.getString("message");
                    ConfigBoxs.Box msg = new ConfigBoxs.Box();
                    msg._type = type;
                    msg._message = message;
                    ConfigBoxs._box_msg_list.put(i, msg);
                    ++i;
                }
            }
        }
        catch (SQLException e) {
            ConfigBoxs._log.error("message訊息_寶相", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        ConfigBoxs._log.info("message訊息_寶相開盒公告->" + ConfigBoxs._box_msg_list.size());
    }
    
    public static void msg(String string1, String string2, String string3) {
        if (ConfigBoxs._box_msg_list.isEmpty()) {
            return;
        }
        else {
            try {
                int r = ConfigBoxs._random.nextInt(ConfigBoxs._box_msg_list.size());
                String msg = (ConfigBoxs._box_msg_list.get((Integer)r))._message;
                if (msg != null) {
                    int type = (ConfigBoxs._box_msg_list.get((Integer)r))._type;
                    String out = null;
                    switch (type) {
                        case 1: {
                            out = String.format(msg, string1, string2, string3);
                            break;
                        }
                    }
                    if (ConfigOther.boxsmsg == 0) {
                        World.get().broadcastPacketToAll(new S_BoxMessage(out));
                    }
                    else {
                        World.get().broadcastPacketToAll(new S_PacketBoxGree(2, out));
                    }
                }
            }
            catch (Exception e) {
                ConfigBoxs._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private class Box
    {
        private int _type;
        private String _message;
        
        /*
        static void access$1(ConfigBoxs.Box box, int type) {
            box._type = type;
        }
        */
        
        /*
        static void access$2(ConfigBoxs.Box box, String message) {
            box._message = message;
        }
        */
    }
}
