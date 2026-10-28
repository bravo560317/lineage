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
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.BadBuddyStorage;

public class BadBuddyTable implements BadBuddyStorage
{
    private static final Log _log;
    private static final Map<Integer, ArrayList<String>> _buddyMap;
    
    static {
        _log = LogFactory.getLog(BadBuddyTable.class);
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
            ps = co.prepareStatement("SELECT * FROM `character_bad_buddy`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_id = rs.getInt("char_id");
                if (CharObjidTable.get().isChar(char_id) != null) {
                    int buddy_id = rs.getInt("buddy_id");
                    if (CharObjidTable.get().isChar(buddy_id) != null) {
                        String buddy_name = rs.getString("buddy_name");
                        ArrayList<String> list = BadBuddyTable._buddyMap.get((Integer)char_id);
                        if (list == null) {
                            ArrayList<String> newlist = new ArrayList();
                            newlist.add(buddy_name);
                            BadBuddyTable._buddyMap.put(char_id, newlist);
                        }
                        else {
                            list.add(buddy_name);
                        }
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
            BadBuddyTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
        BadBuddyTable._log.info("載入黑名單錄資料數量: " + BadBuddyTable._buddyMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_bad_buddy` WHERE `char_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            BadBuddyTable._log.error(e.getLocalizedMessage(), e);
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
            ps = cn.prepareStatement("DELETE FROM `character_bad_buddy` WHERE `buddy_id`=?");
            ps.setInt(1, buddy_id);
            ps.execute();
        }
        catch (SQLException e) {
            BadBuddyTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addBuddyList(int objId, String skillTmp) {
        ArrayList<String> list = BadBuddyTable._buddyMap.get((Integer)objId);
        if (list == null) {
            ArrayList<String> newlist = new ArrayList();
            newlist.add(skillTmp);
            BadBuddyTable._buddyMap.put(objId, newlist);
        }
        else {
            list.add(skillTmp);
        }
    }
    
    @Override
    public ArrayList<String> getBuddyBadList(int playerobjid) {
        return BadBuddyTable._buddyMap.get((Integer)playerobjid);
    }
    
    @Override
    public void addBuddy(int charId, int objId, String name) {
        insertBuddyBad(charId, objId, name);
        ArrayList<String> list = BadBuddyTable._buddyMap.get((Integer)charId);
        if (list == null) {
            ArrayList<String> newlist = new ArrayList();
            newlist.add(name);
            BadBuddyTable._buddyMap.put(charId, newlist);
        }
        else {
            list.add(name);
        }
    }
    
    public static void insertBuddyBad(int char_id, int buddy_id, String buddy_name) {
        Connection co = null;
        PreparedStatement ps = null;
        try {
            co = DatabaseFactory.get().getConnection();
            ps = co.prepareStatement("INSERT INTO `character_bad_buddy` SET `char_id`=?,`buddy_id`=?,`buddy_name`=?");
            ps.setInt(1, char_id);
            ps.setInt(2, buddy_id);
            ps.setString(3, buddy_name);
            ps.execute();
        }
        catch (SQLException e) {
            BadBuddyTable._log.error(e.getLocalizedMessage(), e);
            return;
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
        }
    }
    
    @Override
    public void removeBuddy(int charId, String buddyName) {
        ArrayList<String> list = BadBuddyTable._buddyMap.get((Integer)charId);
        if (list != null) {
            list.remove(buddyName);
            Connection co = null;
            PreparedStatement ps = null;
            try {
                co = DatabaseFactory.get().getConnection();
                ps = co.prepareStatement("DELETE FROM `character_bad_buddy` WHERE `char_id`=? AND `buddy_name`=?");
                ps.setInt(1, charId);
                ps.setString(2, buddyName);
                ps.execute();
            }
            catch (SQLException e) {
                BadBuddyTable._log.error(e.getLocalizedMessage(), e);
                return;
            }
            finally {
                SQLUtil.close(ps);
                SQLUtil.close(co);
            }
        }
    }
    
    @Override
    public boolean check(int charId, String name) {
        if (!BadBuddyTable._buddyMap.isEmpty()) {
            ArrayList<String> list = BadBuddyTable._buddyMap.get((Integer)charId);
            if (list != null) {
                for (String s : list) {
                    if (s.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
