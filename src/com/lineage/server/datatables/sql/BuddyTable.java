package com.lineage.server.datatables.sql;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1BuddyTmp;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.BuddyStorage;

public class BuddyTable implements BuddyStorage
{
    private static final Log _log;
    private static final Map<Integer, ArrayList<L1BuddyTmp>> _buddyMap;
    
    static {
        _log = LogFactory.getLog(BuddyTable.class);
        _buddyMap = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("SELECT * FROM `character_buddys`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_id = rs.getInt("char_id");
                if (CharObjidTable.get().isChar(char_id) != null) {
                    int buddy_id = rs.getInt("buddy_id");
                    if (CharObjidTable.get().isChar(buddy_id) != null) {
                        String buddy_name = rs.getString("buddy_name");
                        L1BuddyTmp buffTmp = new L1BuddyTmp();
                        buffTmp.set_char_id(char_id);
                        buffTmp.set_buddy_id(buddy_id);
                        buffTmp.set_buddy_name(buddy_name);
                        addBuddyList(char_id, buffTmp);
                    }
                    else {
                        delete2(buddy_id);
                    }
                }
                else {
                    delete(char_id);
                }
            }
        }
        catch (SQLException e) {
            BuddyTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        BuddyTable._log.info("載入人物好友紀錄資料數量: " + BuddyTable._buddyMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_buddys` WHERE `char_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            BuddyTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void delete2(int buddy_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_buddys` WHERE `buddy_id`=?");
            ps.setInt(1, buddy_id);
            ps.execute();
        }
        catch (SQLException e) {
            BuddyTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addBuddyList(int objId, L1BuddyTmp skillTmp) {
        ArrayList<L1BuddyTmp> list = BuddyTable._buddyMap.get((Integer)objId);
        if (list == null) {
            ArrayList<L1BuddyTmp> newlist = new ArrayList();
            newlist.add(skillTmp);
            BuddyTable._buddyMap.put(objId, newlist);
        }
        else {
            list.add(skillTmp);
        }
    }
    
    @Override
    public ArrayList<L1BuddyTmp> userBuddy(int playerobjid) {
        return BuddyTable._buddyMap.get((Integer)playerobjid);
    }
    
    @Override
    public void addBuddy(int charId, int objId, String name) {
        ArrayList<L1BuddyTmp> list = BuddyTable._buddyMap.get((Integer)charId);
        if (list != null) {
            for (L1BuddyTmp buddyTmp : list) {
                if (buddyTmp.get_buddy_id() == objId) {
                    return;
                }
            }
        }
        L1BuddyTmp buffTmp = new L1BuddyTmp();
        buffTmp.set_char_id(charId);
        buffTmp.set_buddy_id(objId);
        buffTmp.set_buddy_name(name);
        addBuddyList(charId, buffTmp);
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `character_buddys` SET `char_id`=?,`buddy_id`=?,`buddy_name`=?");
            ps.setInt(1, buffTmp.get_char_id());
            ps.setInt(2, buffTmp.get_buddy_id());
            ps.setString(3, buffTmp.get_buddy_name());
            ps.execute();
        }
        catch (SQLException e) {
            BuddyTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void removeBuddy(int charId, String buddyName) {
        ArrayList<L1BuddyTmp> list = BuddyTable._buddyMap.get((Integer)charId);
        for (L1BuddyTmp buddyTmp : list) {
            if (buddyName.equalsIgnoreCase(buddyTmp.get_buddy_name())) {
                Connection co = null;
                PreparedStatement ps = null;
                try {
                    co = DatabaseFactory.get().getConnection();
                    ps = co.prepareStatement("DELETE FROM `character_buddys` WHERE `char_id`=? AND `buddy_name`=?");
                    ps.setInt(1, charId);
                    ps.setString(2, buddyName);
                    ps.execute();
                }
                catch (SQLException e) {
                    BuddyTable._log.error(e.getLocalizedMessage(), e);
                }
                finally {
                    SQLUtil.close(ps);
                    SQLUtil.close(co);
                }
                list.remove(buddyTmp);
            }
        }
    }
}
