package com.lineage.william;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import com.lineage.DatabaseFactory;
import java.util.HashMap;
import java.util.logging.Logger;

public class ItemSummon
{
    private static Logger _log;
    private static ItemSummon _instance;
    private final HashMap<Integer, L1WilliamItemSummon> _itemIdIndex;
    
    static {
        ItemSummon._log = Logger.getLogger(ItemSummon.class.getName());
    }
    
    public static ItemSummon getInstance() {
        if (ItemSummon._instance == null) {
            ItemSummon._instance = new ItemSummon();
        }
        return ItemSummon._instance;
    }
    
    private ItemSummon() {
        this._itemIdIndex = new HashMap();
        this.loadItemSummon();
    }
    
    private void loadItemSummon() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT * FROM w_道具召喚");
            rs = pstm.executeQuery();
            this.fillItemSummon(rs);
        }
        catch (SQLException e) {
            ItemSummon._log.log(Level.SEVERE, "error while creating w_道具召喚 table", e);
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    private void fillItemSummon(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int item_id = rs.getInt("item_id");
            int checkLevel = rs.getInt("checkLevel");
            int checkClass = rs.getInt("checkClass");
            int checkItem = rs.getInt("checkItem");
            int hpConsume = rs.getInt("hpConsume");
            int mpConsume = rs.getInt("mpConsume");
            int material = rs.getInt("material");
            int material_count = rs.getInt("material_count");
            int summon_id = rs.getInt("summon_id");
            int summonCost = rs.getInt("summonCost");
            int onlyOne = rs.getInt("onlyOne");
            int removeItem = rs.getInt("removeItem");
            L1WilliamItemSummon Item_Summon = new L1WilliamItemSummon(item_id, checkLevel, checkClass, checkItem, hpConsume, mpConsume, material, material_count, summon_id, summonCost, onlyOne, removeItem);
            this._itemIdIndex.put(item_id, Item_Summon);
        }
    }
    
    public L1WilliamItemSummon getTemplate(int itemId) {
        return this._itemIdIndex.get((Integer)itemId);
    }
}
