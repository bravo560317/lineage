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
import com.lineage.server.datatables.storage.EzpayStorage1;

public class EzpayTable1 implements EzpayStorage1
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(EzpayTable1.class);
    }
    
    @Override
    public Map<Integer, int[]> ezpayInfo(String loginName) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, int[]> list = new HashMap();
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `shop_user` WHERE `account`=? ORDER BY `id`";
            ps = co.prepareStatement("SELECT * FROM `shop_user` WHERE `account`=? ORDER BY `id`");
            ps.setString(1, loginName.toLowerCase());
            rs = ps.executeQuery();
            while (rs.next()) {
                int[] value = new int[3];
                int state = rs.getInt("isget");
                if (state == 0) {
                    int key = rs.getInt("id");
                    int p_id = rs.getInt("p_id");
                    int count = rs.getInt("count");
                    value[0] = key;
                    value[1] = p_id;
                    value[2] = count;
                    list.put(key, value);
                }
            }
        }
        catch (Exception e) {
            EzpayTable1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return list;
    }
    
    @Override
    public int[] ezpayInfo(String loginName, int id) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int[] info = new int[4];
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `shop_user` WHERE `account`=? AND `id`=?";
            ps = co.prepareStatement("SELECT * FROM `shop_user` WHERE `account`=? AND `id`=?");
            ps.setString(1, loginName.toLowerCase());
            ps.setInt(2, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int state = rs.getInt("isget");
                if (state == 0) {
                    int p_id = rs.getInt("p_id");
                    int count = rs.getInt("count");
                    info[0] = id;
                    info[1] = p_id;
                    info[2] = count;
                    return info;
                }
            }
        }
        catch (Exception e) {
            EzpayTable1._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return null;
    }
    
    private boolean is(String loginName, int id) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `shop_user` WHERE `account`=? AND `id`=?";
            ps = co.prepareStatement("SELECT * FROM `shop_user` WHERE `account`=? AND `id`=?");
            ps.setString(1, loginName.toLowerCase());
            ps.setInt(2, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int state = rs.getInt("isget");
                if (state != 0) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            EzpayTable1._log.error(e.getLocalizedMessage(), e);
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
        if (!this.is(loginName, id)) {
            return false;
        }
        else {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                Timestamp lastactive = new Timestamp(System.currentTimeMillis());
                con = DatabaseFactoryLogin.get().getConnection();
                String sqlstr = "UPDATE `shop_user` SET `isget`=1,`play`=?,`time`=?,`ip`=? WHERE `id`=? AND `account`=?";
                pstm = con.prepareStatement("UPDATE `shop_user` SET `isget`=1,`play`=?,`time`=?,`ip`=? WHERE `id`=? AND `account`=?");
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
