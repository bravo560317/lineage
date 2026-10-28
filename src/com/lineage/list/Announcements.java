package com.lineage.list;

import com.lineage.server.world.World;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1PcInstance;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class Announcements
{
    private static final Log _log;
    private static Announcements _instance;
    private final ArrayList<String> _announcements;
    
    static {
        _log = LogFactory.getLog(Announcements.class);
    }
    
    public static Announcements get() {
        if (Announcements._instance == null) {
            Announcements._instance = new Announcements();
        }
        return Announcements._instance;
    }
    
    private Announcements() {
        this._announcements = new ArrayList();
        this.load();
    }
    
    private void load() {
        Connection co = null;
        PreparedStatement pm = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            pm = co.prepareStatement("SELECT * FROM `message訊息_登入`");
            rs = pm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                if (id >= 5) {
                    String message = rs.getString("message");
                    this._announcements.add(message);
                }
            }
        }
        catch (SQLException e) {
            Announcements._log.error("message_notice", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pm);
            SQLUtil.close(co);
        }
        Announcements._log.info("message訊息_登入公告->" + this._announcements.size());
    }
    
    public void showAnnouncements(L1PcInstance showTo) {
        for (String msg : this._announcements) {
            showTo.sendPackets(new S_SystemMessage(msg));
        }
    }
    
    public void announceToAll(String msg) {
        World.get().broadcastServerMessage(msg);
    }
    
    public ArrayList<String> list() {
        return this._announcements;
    }
}
