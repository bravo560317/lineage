package com.lineage.server.model;

import java.util.List;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.lock.DwarfForChaReading;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public class L1DwarfForChaInventory extends L1Inventory
{
    public static final Log _log;
    private static final long serialVersionUID = 1L;
    private final L1PcInstance _owner;
    
    static {
        _log = LogFactory.getLog(L1DwarfForChaInventory.class);
    }
    
    public L1DwarfForChaInventory(L1PcInstance owner) {
        this._owner = owner;
    }
    
    @Override
    public void loadItems() {
        try {
            CopyOnWriteArrayList<L1ItemInstance> items = DwarfForChaReading.get().loadItems(this._owner.getName());
            if (items != null) {
                this._items = (List)items;
            }
        }
        catch (Exception e) {
            L1DwarfForChaInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void insertItem(L1ItemInstance item) {
        if (item.getCount() <= 0L) {
            return;
        }
        else {
            try {
                DwarfForChaReading.get().insertItem(this._owner.getName(), item);
            }
            catch (Exception e) {
                L1DwarfForChaInventory._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item) {
        try {
            DwarfForChaReading.get().updateItem(item);
        }
        catch (Exception e) {
            L1DwarfForChaInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onRemoveItem(L1ItemInstance item) {
        this._items.remove(item);
    }
    
    @Override
    public void deleteItem(L1ItemInstance item) {
        try {
            this._items.remove(item);
            DwarfForChaReading.get().deleteItem(this._owner.getName(), item);
            World.get().removeObject(item);
        }
        catch (Exception e) {
            L1DwarfForChaInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
}
