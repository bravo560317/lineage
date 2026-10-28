package com.lineage.server.datatables.sql;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1PcInstance;
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
import com.lineage.server.templates.L1PcOther3;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharOtherStorage3;

public class CharOtherTable3 implements CharOtherStorage3
{
    private static final Log _log;
    private static final Map<Integer, L1PcOther3> _otherMap;
    
    static {
        _log = LogFactory.getLog(CharOtherTable3.class);
        _otherMap = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("SELECT * FROM `character_Quest_Map`");
            rs = ps.executeQuery();
            while (rs.next()) {
                int char_obj_id = rs.getInt("char_obj_id");
                if (CharObjidTable.get().isChar(char_obj_id) != null) {
                    int type1 = rs.getInt("type1");
                    int type2 = rs.getInt("type2");
                    int type3 = rs.getInt("type3");
                    int type4 = rs.getInt("type4");
                    int type5 = rs.getInt("type5");
                    int type6 = rs.getInt("type6");
                    L1PcOther3 other = new L1PcOther3();
                    other.set_objid(char_obj_id);
                    other.set_type1(type1);
                    other.set_type2(type2);
                    other.set_type3(type3);
                    other.set_type4(type4);
                    other.set_type5(type5);
                    other.set_type6(type6);
                    addMap(char_obj_id, other);
                }
                else {
                    delete(char_obj_id);
                }
            }
        }
        catch (SQLException e) {
            CharOtherTable3._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
        CharOtherTable3._log.info("載入額外紀錄資料數量: " + CharOtherTable3._otherMap.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_Quest_Map` WHERE `char_obj_id`=?");
            ps.setInt(1, objid);
            ps.execute();
            CharOtherTable3._otherMap.remove((Integer)objid);
        }
        catch (SQLException e) {
            CharOtherTable3._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private static void addMap(int objId, L1PcOther3 other) {
        L1PcOther3 otherTmp = CharOtherTable3._otherMap.get((Integer)objId);
        if (otherTmp == null) {
            CharOtherTable3._otherMap.put(objId, other);
        }
    }
    
    @Override
    public L1PcOther3 getOther(L1PcInstance pc) {
        L1PcOther3 otherTmp = CharOtherTable3._otherMap.get((Integer)pc.getId());
        return otherTmp;
    }
    
    @Override
    public void storeOther(int objId, L1PcOther3 other) {
        L1PcOther3 otherTmp = CharOtherTable3._otherMap.get((Integer)objId);
        if (otherTmp == null) {
            addMap(objId, other);
            this.addNewOther(other);
        }
        else {
            this.updateOther(other);
        }
    }
    
    private void updateOther(L1PcOther3 other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            int type1 = other.get_type1();
            int type2 = other.get_type2();
            int type3 = other.get_type3();
            int type4 = other.get_type4();
            int type5 = other.get_type5();
            int type6 = other.get_type6();
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_Quest_Map` SET `type1`=?,`type2`=?,`type3`=?,`type4`=?,`type5`=?,`type6`=? WHERE `char_obj_id`=?");
            int i = 0;
            ps.setInt(++i, type1);
            ps.setInt(++i, type2);
            ps.setInt(++i, type3);
            ps.setInt(++i, type4);
            ps.setInt(++i, type5);
            ps.setInt(++i, type6);
            ps.setInt(++i, other.get_objid());
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable3._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    private void addNewOther(L1PcOther3 other) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            int oid = other.get_objid();
            int type1 = other.get_type1();
            int type2 = other.get_type2();
            int type3 = other.get_type3();
            int type4 = other.get_type4();
            int type5 = other.get_type5();
            int type6 = other.get_type6();
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `character_Quest_Map` SET `char_obj_id`=?,`type1`=?,`type2`=?,`type3`=?,`type4`=?,`type5`=?,`type6`=?");
            int i = 0;
            ps.setInt(++i, oid);
            ps.setInt(++i, type1);
            ps.setInt(++i, type2);
            ps.setInt(++i, type3);
            ps.setInt(++i, type4);
            ps.setInt(++i, type5);
            ps.setInt(++i, type6);
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable3._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public void tam() {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            for (L1PcOther3 l1PcOther3 : CharOtherTable3._otherMap.values()) {}
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `character_Quest_Map` SET `killCount`='0' AND `deathCount`='0'");
            ps.execute();
        }
        catch (SQLException e) {
            CharOtherTable3._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
