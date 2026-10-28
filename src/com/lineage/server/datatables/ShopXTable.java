package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import org.apache.commons.logging.Log;

public class ShopXTable
{
    private static final Log _log;
    private static ShopXTable _instance;
    private static final Map<Integer, String> _notShopList;
    
    static {
        _log = LogFactory.getLog(ShopXTable.class);
        _notShopList = new HashMap();
    }
    
    public static ShopXTable get() {
        if (ShopXTable._instance == null) {
            ShopXTable._instance = new ShopXTable();
        }
        return ShopXTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `server_shopx`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemid = rs.getInt("itemid");
                if (ItemTable.get().getTemplate(itemid) == null) {
                    ShopXTable._log.error("禁止拍賣物品資料錯誤: 沒有這個編號的道具:" + itemid);
                    delete(itemid);
                }
                else {
                    String note = rs.getString("note");
                    ShopXTable._notShopList.put(new Integer(itemid), note);
                }
            }
            ShopXTable._log.info("載入禁止拍賣物品資料數量: " + ShopXTable._notShopList.size() + "(" + timer.get() + "ms)");
        }
        catch (SQLException e) {
            ShopXTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private static void delete(int item_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("DELETE FROM `server_shopx` WHERE `itemid`=?");
            ps.setInt(1, item_id);
            ps.execute();
        }
        catch (SQLException e) {
            ShopXTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
    
    public String getTemplate(int itemid) {
        return ShopXTable._notShopList.get(new Integer(itemid));
    }
    
    public Map<Integer, String> getList() {
        return ShopXTable._notShopList;
    }
    
    public int size() {
        return ShopXTable._notShopList.size();
    }
}
