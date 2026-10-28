package com.lineage.server.model;

import com.lineage.server.datatables.InnKeyTable;
import com.lineage.server.serverpackets.S_RemoveObject;
import java.util.Iterator;
import com.lineage.server.serverpackets.S_NPCPack_Item;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.timecontroller.server.ServerDeleteItemTimer;
import com.lineage.server.model.Instance.L1ItemInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1GroundInventory extends L1Inventory
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog(L1GroundInventory.class);
    }
    
    private void setTimer(L1ItemInstance item) {
        try {
            if (item.getItemId() == 40515) {
                return;
            }
            else if (L1HouseLocation.isInHouse(this.getX(), this.getY(), this.getMapId())) {
                return;
            }
            else if (!ServerDeleteItemTimer.contains(item)) {
                ServerDeleteItemTimer.add(item);
            }
        }
        catch (Exception e) {
            L1GroundInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public L1GroundInventory(int objectId, int x, int y, short map) {
        try {
            this.setId(objectId);
            this.setX(x);
            this.setY(y);
            this.setMap(map);
            World.get().addVisibleObject(this);
        }
        catch (Exception e) {
            L1GroundInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onPerceive(L1PcInstance perceivedFrom) {
        try {
            for (L1ItemInstance item : this.getItems()) {
                if (perceivedFrom.get_showId() == item.get_showId() && !perceivedFrom.knownsObject(item)) {
                    perceivedFrom.addKnownObject(item);
                    perceivedFrom.sendPackets(new S_NPCPack_Item(item));
                }
            }
        }
        catch (Exception e) {
            L1GroundInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void insertItem(L1ItemInstance item) {
        if (item.getCount() <= 0L) {
            return;
        }
        else {
            try {
                this.setTimer(item);
                for (L1PcInstance pc : World.get().getRecognizePlayer(item)) {
                    if (pc.get_showId() == item.get_showId()) {
                        pc.sendPackets(new S_NPCPack_Item(item));
                        pc.addKnownObject(item);
                    }
                }
            }
            catch (Exception e) {
                L1GroundInventory._log.error(e.getLocalizedMessage(), e);
            }
            return;
        }
    }
    
    @Override
    public void updateItem(L1ItemInstance item) {
        try {
            for (L1PcInstance pc : World.get().getRecognizePlayer(item)) {
                if (pc.get_showId() == item.get_showId()) {
                    pc.sendPackets(new S_NPCPack_Item(item));
                }
            }
        }
        catch (Exception e) {
            L1GroundInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void onRemoveItem(L1ItemInstance item) {
        try {
            for (L1PcInstance pc : World.get().getRecognizePlayer(item)) {
                pc.sendPackets(new S_RemoveObject(item));
                pc.removeKnownObject(item);
            }
            this._items.remove(item);
            if (this._items.size() == 0) {
                World.get().removeVisibleObject(this);
            }
        }
        catch (Exception e) {
            L1GroundInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    @Override
    public void deleteItem(L1ItemInstance item) {
        try {
            for (L1PcInstance pc : World.get().getRecognizePlayer(item)) {
                pc.sendPackets(new S_RemoveObject(item));
                pc.removeKnownObject(item);
            }
            int itemid = item.getItemId();
            if (itemid == 40312) {
                InnKeyTable.DeleteKey(item);
            }
            if (itemid == 82504) {
                InnKeyTable.DeleteKey(item);
            }
            this._items.remove(item);
            if (this._items.size() == 0) {
                World.get().removeVisibleObject(this);
            }
        }
        catch (Exception e) {
            L1GroundInventory._log.error(e.getLocalizedMessage(), e);
        }
    }
}
