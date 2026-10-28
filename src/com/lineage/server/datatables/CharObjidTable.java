package com.lineage.server.datatables;

import java.util.Iterator;
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

public class CharObjidTable
{
    private static final Log _log;
    private static final Map<Integer, String> _objList;
    private static final Map<Integer, String> _objClanList;
    private static CharObjidTable _instance;
    
    static {
        _log = LogFactory.getLog(CharObjidTable.class);
        _objList = new HashMap();
        _objClanList = new HashMap();
    }
    
    public static CharObjidTable get() {
        if (CharObjidTable._instance == null) {
            CharObjidTable._instance = new CharObjidTable();
        }
        return CharObjidTable._instance;
    }
    
    public void load() {
        this.loadPc();
        this.loadClan();
    }
    
    private void loadClan() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `clan_data`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int clan_id = rs.getInt("clan_id");
                String clan_name = rs.getString("clan_name");
                CharObjidTable._objClanList.put(clan_id, clan_name);
            }
        }
        catch (SQLException e) {
            CharObjidTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void loadPc() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `characters`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int objid = rs.getInt("objid");
                String char_name = rs.getString("char_name");
                CharObjidTable._objList.put(objid, char_name);
            }
        }
        catch (SQLException e) {
            CharObjidTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public void reChar(int objid, String char_name) {
        CharObjidTable._objList.put(objid, char_name);
    }
    
    public void addChar(int objid, String char_name) {
        int tmp = this.charObjid(char_name);
        if (tmp == 0) {
            CharObjidTable._objList.put(objid, char_name);
        }
    }
    
    public String isChar(int objid) {
        String username = CharObjidTable._objList.get((Integer)objid);
        if (username != null) {
            return username;
        }
        else {
            return null;
        }
    }
    
    public int charObjid(String char_name) {
        for (Integer integer : CharObjidTable._objList.keySet()) {
            String tgname = CharObjidTable._objList.get(integer);
            if (char_name.equalsIgnoreCase(tgname)) {
                return integer;
            }
        }
        return 0;
    }
    
    public void charRemove(String char_name) {
        int objid = this.charObjid(char_name);
        String username = CharObjidTable._objList.get((Integer)objid);
        if (username != null) {
            CharObjidTable._objList.remove((Integer)objid);
        }
    }
    
    public void addClan(int clan_id, String clan_name) {
        int tmp = this.clanObjid(clan_name);
        if (tmp == 0) {
            CharObjidTable._objClanList.put(clan_id, clan_name);
        }
    }
    
    public boolean isClan(int clan_id) {
        String username = CharObjidTable._objClanList.get((Integer)clan_id);
        return username != null;
    }
    
    public int clanObjid(String clan_name) {
        for (Integer integer : CharObjidTable._objClanList.keySet()) {
            String tgname = CharObjidTable._objClanList.get(integer);
            if (clan_name.equalsIgnoreCase(tgname)) {
                return integer;
            }
        }
        return 0;
    }
}
