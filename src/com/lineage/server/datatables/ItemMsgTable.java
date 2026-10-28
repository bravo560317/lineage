package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

public class ItemMsgTable
{
    private static final Log _log;
    private static final ArrayList<Integer> _idList;
    private static ItemMsgTable _instance;
    
    static {
        _log = LogFactory.getLog(ItemMsgTable.class);
        _idList = new ArrayList();
    }
    
    public static ItemMsgTable get() {
        if (ItemMsgTable._instance == null) {
            ItemMsgTable._instance = new ItemMsgTable();
        }
        return ItemMsgTable._instance;
    }
    
    public void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM `系統_掉落物品廣播`");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("itemid");
                if (!ItemMsgTable._idList.contains((Integer)item_id)) {
                    ItemMsgTable._idList.add(item_id);
                }
                String note = rs.getString("名稱");
                if (!note.contains("=>")) {
                    updata_name(item_id);
                }
            }
        }
        catch (SQLException e) {
            ItemMsgTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        ItemMsgTable._log.info("載入打寶公告物編號數量: " + ItemMsgTable._idList.size() + "(" + timer.get() + "ms)");
    }
    
    public boolean contains(int item_id) {
        return ItemMsgTable._idList.contains((Integer)item_id);
    }
    
    private static void updata_name(int item_id) {
        Connection cn = null;
        PreparedStatement ps = null;
        String itemname = ItemTable.get().getTemplate(item_id).getName();
        try {
            cn = DatabaseFactory.get().getConnection();
            ps = cn.prepareStatement("UPDATE `系統_掉落物品廣播` SET `名稱`=? WHERE `itemid`=?");
            int i = 0;
            ps.setString(++i, "公告->" + itemname);
            ps.setInt(++i, item_id);
            ps.execute();
        }
        catch (SQLException e) {
            ItemMsgTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}
