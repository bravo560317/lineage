package com.lineage.server.datatables;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.templates.L1Blend;
import java.util.HashMap;
import org.apache.commons.logging.Log;

public final class BlendTable
{
    private static final Log _log;
    private static BlendTable _instance;
    private final HashMap<Integer, L1Blend> _itemIdIndex;
    
    static {
        _log = LogFactory.getLog(BlendTable.class);
    }
    
    public BlendTable() {
        this._itemIdIndex = new HashMap();
    }
    
    public static BlendTable getInstance() {
        if (BlendTable._instance == null) {
            BlendTable._instance = new BlendTable();
        }
        return BlendTable._instance;
    }
    
    public final void load() {
        PerformanceTimer timer = new PerformanceTimer();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseFactory.get().getConnection();
            pstm = conn.prepareStatement("SELECT * FROM w_物品融合db化");
            rs = pstm.executeQuery();
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                int checkLevel = rs.getInt("checkLevel");
                int checkClass = rs.getInt("checkClass");
                int rnd = rs.getInt("rnd");
                int checkItem = rs.getInt("checkItem");
                int hpConsume = rs.getInt("hpConsume");
                int mpConsume = rs.getInt("mpConsume");
                int material = rs.getInt("material");
                int material_count = rs.getInt("material_count");
                int material_2 = rs.getInt("material_2");
                int material_2_count = rs.getInt("material_2_count");
                int material_3 = rs.getInt("material_3");
                int material_3_count = rs.getInt("material_3_count");
                int material_4 = rs.getInt("material_4");
                int material_4_count = rs.getInt("material_4_count");
                int material_5 = rs.getInt("material_5");
                int material_5_count = rs.getInt("material_5_count");
                int new_item = rs.getInt("new_item");
                int new_item_counts = rs.getInt("new_item_counts");
                int new_Enchantlvl_SW = rs.getInt("new_Enchantlvl_SW");
                int new_item_Enchantlvl = rs.getInt("new_item_Enchantlvl");
                int removeItem = rs.getInt("removeItem");
                String message = rs.getString("message");
                int item_Html = rs.getInt("item_Html");
                int gfxid = rs.getInt("特效");
                int checkmsg = rs.getInt("是否全廣");
                String checkmsgall = rs.getString("全廣文字");
                L1Blend Item_Blend = new L1Blend(item_id, checkLevel, checkClass, rnd, checkItem, hpConsume, mpConsume, material, material_count, material_2, material_2_count, material_3, material_3_count, material_4, material_4_count, material_5, material_5_count, new_item, new_item_counts, new_Enchantlvl_SW, new_item_Enchantlvl, removeItem, message, item_Html, gfxid, checkmsg, checkmsgall);
                this._itemIdIndex.put(item_id, Item_Blend);
            }
        }
        catch (SQLException e) {
            BlendTable._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(conn);
        }
        BlendTable._log.info("讀取->w_物品融合db化資料數量: " + this._itemIdIndex.size() + "(" + timer.get() + "ms)");
    }
    
    public final L1Blend getTemplate(int itemId) {
        return this._itemIdIndex.get((Integer)itemId);
    }
}
