package com.lineage.server.datatables.sql;

import java.sql.SQLException;
import com.lineage.DatabaseFactory;
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
import com.lineage.server.datatables.storage.shop_lxStorage;

public class shop_lx implements shop_lxStorage
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(shop_lx.class);
    }
    
    @Override
    public Map<Integer, int[]> ezpayInfo(String loginName) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, int[]> list = new HashMap();
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `character_離線擺攤` WHERE `account`=? ORDER BY `id`";
            ps = co.prepareStatement("SELECT * FROM `character_離線擺攤` WHERE `account`=? ORDER BY `id`");
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
            shop_lx._log.error(e.getLocalizedMessage(), e);
            return list;
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return list;
    }
    
    @Override
    public void updatemejs(int mac, String name) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            Timestamp lastactive = new Timestamp(System.currentTimeMillis());
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("INSERT INTO `character_離線擺攤` SET `MAC地址`=?,`角色名稱`=?,`time`=?");
            int i = 0;
            ++i;
            ps.setInt(i, mac);
            ++i;
            ps.setString(i, name);
            ++i;
            ps.setTimestamp(i, lastactive);
            ps.execute();
        }
        catch (Exception e) {
            shop_lx._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(cn);
            SQLUtil.close(ps);
        }
    }
    
    @Override
    public int[] ezpayInfo(String loginName, int id) {
        Connection co = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int[] info = new int[4];
        try {
            co = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "SELECT * FROM `shop_userhs` WHERE `account`=? AND `id`=?";
            ps = co.prepareStatement("SELECT * FROM `shop_userhs` WHERE `account`=? AND `id`=?");
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
            shop_lx._log.error(e.getLocalizedMessage(), e);
            return null;
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(co);
            SQLUtil.close(rs);
        }
        return null;
    }
    
    @Override
    public boolean update() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            Timestamp lastactive = new Timestamp(System.currentTimeMillis());
            con = DatabaseFactoryLogin.get().getConnection();
            String sqlstr = "UPDATE `shop_userhs` SET `time`=?";
            pstm = con.prepareStatement("UPDATE `shop_userhs` SET `time`=?");
            pstm.setTimestamp(1, lastactive);
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
    
    @Override
    public int count(String mac) {
        int count = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("SELECT COUNT(*) FROM character_離線擺攤 WHERE MAC地址=?");
            pstm.setString(1, mac);
            rs = pstm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            shop_lx._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        return count;
    }
    
    @Override
    public void remove(String name) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactoryLogin.get().getConnection();
            pstm = con.prepareStatement("DELETE FROM `character_離線擺攤` WHERE 角色名稱= ?");
            pstm.setString(1, name);
            pstm.execute();
        }
        catch (SQLException e) {
            shop_lx._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
}
