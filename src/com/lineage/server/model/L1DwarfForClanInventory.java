package com.lineage.server.model;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.lock.DwarfForClanReading;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1DwarfForClanInventory extends L1Inventory
{
    public static final Log _log;
    private static final long serialVersionUID = 1L;
    private final L1Clan _clan;
    
    static {
        _log = LogFactory.getLog(L1DwarfForClanInventory.class);
    }
    
    public L1DwarfForClanInventory(L1Clan clan) {
        this._clan = clan;
    }
    
    @Override
    public synchronized void loadItems() {
        try {
            CopyOnWriteArrayList<L1ItemInstance> items = DwarfForClanReading.get().loadItems(this._clan.getClanName());
            if (items != null) {
                this._items = (List)items;
            }
        }
        catch (Exception e) {
            L1DwarfForClanInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public synchronized void insertItem(L1ItemInstance item) {
        try {
            DwarfForClanReading.get().insertItem(this._clan.getClanName(), item);
        }
        catch (Exception e) {
            L1DwarfForClanInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public synchronized void updateItem(L1ItemInstance item) {
        try {
            DwarfForClanReading.get().updateItem(item);
        }
        catch (Exception e) {
            L1DwarfForClanInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public synchronized void deleteItem(L1ItemInstance item) {
        try {
            this._items.remove(item);
            DwarfForClanReading.get().deleteItem(this._clan.getClanName(), item);
            World.get().removeObject(item);
        }
        catch (Exception e) {
            L1DwarfForClanInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public synchronized void deleteAllItems() {
        try {
            DwarfForClanReading.get().delUserItems(this._clan.getClanName());
            this._items.clear();
        }
        catch (Exception e) {
            L1DwarfForClanInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public void writeHistory(L1PcInstance pc, L1ItemInstance item, int count, int type) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("INSERT INTO clan_warehouse_history SET clan_id=?, char_name=?, type=?, item_name=?, item_count=?, record_time=?");
            pstm.setInt(1, this._clan.getClanId());
            pstm.setString(2, pc.getName());
            pstm.setInt(3, type);
            pstm.setString(4, item.getWarehouseHistoryName());
            pstm.setInt(5, count);
            pstm.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstm.execute();
        }
        catch (SQLException e) {
            L1DwarfForClanInventory._log.error(e.getLocalizedMessage(), e);
        }
        finally {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
    }
    
    @Override
    public void onRemoveItem(L1ItemInstance item) {
        this._items.remove(item);
    }
}
