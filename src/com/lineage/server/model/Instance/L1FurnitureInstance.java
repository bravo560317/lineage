package com.lineage.server.model.Instance;

import java.util.Iterator;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1FurnitureInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private int _itemObjId;
    
    static {
        _log = LogFactory.getLog(L1FurnitureInstance.class);
    }
    
    public L1FurnitureInstance(L1Npc template) {
        super(template);
    }
    
    @Override
    public void onAction(L1PcInstance player) {
    }
    
    @Override
    public void deleteMe() {
        try {
            this._destroyed = true;
            if (this.getInventory() != null) {
                this.getInventory().clearItems();
            }
            World.get().removeVisibleObject(this);
            World.get().removeObject(this);
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                pc.removeKnownObject(this);
                pc.sendPackets(new S_RemoveObject(this));
            }
            this.removeAllKnownObjects();
        }
        catch (Exception e) {
            L1FurnitureInstance._log.error(e.getLocalizedMessage(), e);
        }
    }
    
    public int getItemObjId() {
        return this._itemObjId;
    }
    
    public void setItemObjId(int i) {
        this._itemObjId = i;
    }
}
