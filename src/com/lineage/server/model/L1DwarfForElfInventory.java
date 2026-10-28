package com.lineage.server.model;

import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.lock.DwarfForElfReading;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class L1DwarfForElfInventory extends L1Inventory
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    private final L1PcInstance _owner;
    
    static {
        _log = LogFactory.getLog(L1DwarfForElfInventory.class);
    }
    
    public L1DwarfForElfInventory(L1PcInstance owner) {
        this._owner = owner;
    }
    
    @Override
    public void loadItems() {
        try {
            CopyOnWriteArrayList items = DwarfForElfReading.get().loadItems(this._owner.getAccountName());
            if (items != null) {
                this._items = items;
            }
        }
        catch (Exception e) {
            L1DwarfForElfInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void insertItem(L1ItemInstance item) {
        if (item.getCount() <= 0L) {
            return;
        }
        else {
            try {
                DwarfForElfReading.get().insertItem(this._owner.getAccountName(), item);
            }
            catch (Exception e) {
                L1DwarfForElfInventory._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item) {
        try {
            DwarfForElfReading.get().updateItem(item);
        }
        catch (Exception e) {
            L1DwarfForElfInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void deleteItem(L1ItemInstance item) {
        try {
            this._items.remove(item);
            DwarfForElfReading.get().deleteItem(this._owner.getAccountName(), item);
            World.get().removeObject(item);
        }
        catch (Exception e) {
            L1DwarfForElfInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onRemoveItem(L1ItemInstance item) {
        this._items.remove(item);
    }
}
