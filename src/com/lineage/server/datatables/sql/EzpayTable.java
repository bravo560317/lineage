package com.lineage.server.datatables.sql;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactoryLogin;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.storage.EzpayStorage;

public class EzpayTable implements EzpayStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(EzpayTable.class);
    }
    
    @Override
    public Map<Integer, int[]> ezpayInfo(String loginName) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, int[]> list = new HashMap();
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `w_npc玩家即時領取` WHERE `玩家帳號`=? ORDER BY `流水號`";
            ps = co.prepareStatement(sqlstr);
            ps.setString(1, loginName.toLowerCase());
            rs = ps.executeQuery();
            while (rs.next()) {
                int[] value = new int[3];
                int out = rs.getInt("領取狀態");
                int ready = rs.getInt("是否手動發放");
                if (out == 0 && ready == 1) {
                    int key = rs.getInt("流水號");
                    int p_id = rs.getInt("發送物品編號");
                    int count = rs.getInt("發送物品數量");
                    value[0] = key;
                    value[1] = p_id;
                    value[2] = count;
                    list.put(key, value);
                }
            }
        }
        catch (Exception e) {
            EzpayTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return list;
    }
    
    private boolean is_holding(String loginName, int id) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `w_npc玩家即時領取` WHERE `玩家帳號`=? AND `流水號`=?";
            ps = co.prepareStatement(sqlstr);
            ps.setString(1, loginName.toLowerCase());
            ps.setInt(2, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int out = rs.getInt("領取狀態");
                if (out != 0) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            EzpayTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return true;
    }
    
    @Override
    public boolean update(String loginName, int id, String pcname, String ip) {
        if (!this.is_holding(loginName, id)) {
            return false;
        }
        else {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                Timestamp lastactive = new Timestamp(System.currentTimeMillis());
                con = DatabaseFactoryLogin.get().getConnection();
                String sqlstr = "UPDATE `w_npc玩家即時領取` SET `領取狀態`=1,`領取玩家角色`=?,`領取時間`=?,`ip`=? WHERE `流水號`=? AND `玩家帳號`=?";
                pstm = con.prepareStatement(sqlstr);
                pstm.setString(1, pcname);
                pstm.setTimestamp(2, lastactive);
                pstm.setString(3, ip);
                pstm.setInt(4, id);
                pstm.setString(5, loginName);
                pstm.execute();
                return true;
            }
            catch (Exception e) {}
            finally {
                SQLUtil.close(pstm);
                SQLUtil.close(con);
            }
            return false;
        }
    }
}
