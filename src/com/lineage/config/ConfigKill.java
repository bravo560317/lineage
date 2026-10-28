package com.lineage.config;

import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_KillMessage;
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

public class ConfigKill
{
    private static final Log _log;
    private static final Map<Integer, ConfigKill.Kill> KILL_TEXT_LIST;
    private static ConfigKill _instance;
    private static final Random _random;
    public static int KILLLEVEL;
    
    static {
        _log = LogFactory.getLog(ConfigKill.class);
        KILL_TEXT_LIST = new HashMap();
        _random = new Random();
        ConfigKill.KILLLEVEL = 90;
    }
    
    public static ConfigKill get() {
        if (ConfigKill._instance == null) {
            ConfigKill._instance = new ConfigKill();
        }
        return ConfigKill._instance;
    }
    
    private ConfigKill() {
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        int i = 0;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `message訊息_殺人`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("trpe");
                if (id > 5) {
                    String message = rs.getString("message");
                    ConfigKill.Kill msg = new ConfigKill.Kill();
                    msg._type = type;
                    msg._message = message;
                    ConfigKill.KILL_TEXT_LIST.put(i, msg);
                    ++i;
                }
            }
        }
        catch (SQLException e) {
            ConfigKill._log.error("message訊息_殺人", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        ConfigKill._log.info("message訊息_殺人公告->" + ConfigKill.KILL_TEXT_LIST.size());
    }
    
    public void msg(String fpcName, String pcName, String weaponName) {
        if (ConfigKill.KILL_TEXT_LIST.isEmpty()) {
            return;
        }
        else {
            try {
                int r = ConfigKill._random.nextInt(ConfigKill.KILL_TEXT_LIST.size());
                String msg = (ConfigKill.KILL_TEXT_LIST.get((Integer)r))._message;
                if (msg != null) {
                    int type = (ConfigKill.KILL_TEXT_LIST.get((Integer)r))._type;
                    String out = null;
                    switch (type) {
                        case 0: {
                            out = String.format(msg, fpcName, pcName);
                            break;
                        }
                        case 1: {
                            out = String.format(msg, pcName, fpcName);
                            break;
                        }
                        case 2: {
                            out = String.format(msg, fpcName, weaponName, pcName);
                            break;
                        }
                        case 3: {
                            out = String.format(msg, pcName, fpcName, weaponName);
                            break;
                        }
                    }
                    World.get().broadcastPacketToAll(new S_KillMessage(out));
                    World.get().broadcastPacketToAll(new S_PacketBoxGree(2, out));
                }
            }
            catch (Exception e) {
                ConfigKill._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    public void msgnoweapon(String fpcName, String pcName, String weaponName) {
        if (ConfigKill.KILL_TEXT_LIST.isEmpty()) {
            return;
        }
        else {
            try {
                int r = ConfigKill._random.nextInt(ConfigKill.KILL_TEXT_LIST.size());
                String msg = (ConfigKill.KILL_TEXT_LIST.get((Integer)r))._message;
                if (msg != null) {
                    int type = (ConfigKill.KILL_TEXT_LIST.get((Integer)r))._type;
                    String out = null;
                    switch (type) {
                        case 0: {
                            out = String.format(msg, fpcName, pcName);
                            break;
                        }
                        case 1: {
                            out = String.format(msg, pcName, fpcName);
                            break;
                        }
                        case 2: {
                            out = String.format(msg, fpcName, weaponName, pcName);
                            break;
                        }
                        case 3: {
                            out = String.format(msg, pcName, fpcName, "空手");
                            break;
                        }
                    }
                    if (ConfigOther.killmsg == 0) {
                        World.get().broadcastPacketToAll(new S_KillMessage(out));
                    }
                    else {
                        World.get().broadcastPacketToAll(new S_PacketBoxGree(2, out));
                    }
                }
            }
            catch (Exception e) {
                ConfigKill._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    public void msg1(String fpcName, String pcName, String weaponName) {
        if (ConfigKill.KILL_TEXT_LIST.isEmpty()) {
            return;
        }
        else {
            try {
                int r = ConfigKill._random.nextInt(ConfigKill.KILL_TEXT_LIST.size());
                String msg = (ConfigKill.KILL_TEXT_LIST.get((Integer)r))._message;
                if (msg != null) {
                    int type = (ConfigKill.KILL_TEXT_LIST.get((Integer)r))._type;
                    String out = null;
                    switch (type) {
                        case 0: {
                            out = String.format(msg, "**守護者**", pcName);
                            break;
                        }
                        case 1: {
                            out = String.format(msg, pcName, "**守護者**");
                            break;
                        }
                        case 2: {
                            out = String.format(msg, "**守護者**", weaponName, pcName);
                            break;
                        }
                        case 3: {
                            out = String.format(msg, pcName, "**守護者**", weaponName);
                            break;
                        }
                    }
                    if (ConfigOther.killmsg == 0) {
                        World.get().broadcastPacketToAll(new S_KillMessage(out));
                    }
                    else {
                        World.get().broadcastPacketToAll(new S_PacketBoxGree(2, out));
                    }
                }
            }
            catch (Exception e) {
                ConfigKill._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    private class Kill
    {
        private int _type;
        private String _message;
        
        /*
        static void access$1(ConfigKill.Kill kill, int type) {
            kill._type = type;
        }
        */
        
        /*
        static void access$2(ConfigKill.Kill kill, String message) {
            kill._message = message;
        }
        */
    }
}
