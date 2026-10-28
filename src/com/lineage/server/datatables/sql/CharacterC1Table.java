package com.lineage.server.datatables.sql;

import com.lineage.data.event.CampSet;
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
import com.lineage.server.templates.L1User_Power;
import java.util.Map;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.CharacterC1Storage;

public class CharacterC1Table implements CharacterC1Storage
{
    private static final Log _log;
    private static final Map<Integer, L1User_Power> _userPowers;
    
    static {
        _log = LogFactory.getLog(CharacterC1Table.class);
        _userPowers = new HashMap();
    }
    
    @Override
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `character_c1`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int object_id = rs.getInt("object_id");
                if (CharObjidTable.get().isChar(object_id) != null) {
                    int c1_type = rs.getInt("c1_type");
                    String note = rs.getString("note");
                    L1User_Power power = new L1User_Power();
                    power.set_object_id(object_id);
                    power.set_c1_type(c1_type);
                    power.set_note(note);
                    CharacterC1Table._userPowers.put(object_id, power);
                }
                else {
                    delete(object_id);
                }
            }
        }
        catch (SQLException e) {
            CharacterC1Table._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        CharacterC1Table._log.info("載入人物陣營紀錄資料數量: " + CharacterC1Table._userPowers.size() + "(" + timer.get() + "ms)");
    }
    
    private static void delete(int objid) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `character_c1` WHERE `object_id`=?");
            ps.setInt(1, objid);
            ps.execute();
        }
        catch (SQLException e) {
            CharacterC1Table._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    @Override
    public L1User_Power get(int objectId) {
        return CharacterC1Table._userPowers.get((Integer)objectId);
    }
    
    @Override
    public void storeCharacterC1(L1PcInstance pc) {
        if (!CampSet.CAMPSTART) {
            CharacterC1Table._log.error("陣營系統並未啟動!");
            return;
        }
        else {
            L1User_Power power = pc.get_c_power();
            CharacterC1Table._userPowers.put(pc.getId(), power);
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("INSERT INTO `character_c1` SET `object_id`=?,`c1_type`=?,`note`=?");
                int i = 0;
                pstm.setInt(++i, power.get_object_id());
                pstm.setInt(++i, power.get_c1_type());
                pstm.setString(++i, power.get_note());
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterC1Table._log.error(e.getLocalizedMessage(), e);
            }
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
            return;
        }
    }
    
    @Override
    public void updateCharacterC1(int object_id, int c1_type, String note) {
        L1User_Power power = CharacterC1Table._userPowers.get((Integer)object_id);
        power.set_c1_type(c1_type);
        power.set_note(note);
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("UPDATE `character_c1` SET `c1_type`=?,`note`=? WHERE `object_id`=?");
            int i = 0;
            pstm.setInt(++i, power.get_c1_type());
            pstm.setString(++i, power.get_note());
            pstm.setInt(++i, power.get_object_id());
            pstm.execute();
        }
        catch (SQLException e) {
            CharacterC1Table._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
