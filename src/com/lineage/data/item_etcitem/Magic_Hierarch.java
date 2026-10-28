package com.lineage.data.item_etcitem;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Magic_Hierarch extends ItemExecutor
{
    private int _npcid;
    private int _itemid;
    private int _count;
    
    public Magic_Hierarch() {
        this._npcid = 0;
        this._itemid = 0;
        this._count = 0;
    }
    
    public static ItemExecutor get() {
        return new Magic_Hierarch();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (pc.isInvisble()) {
            return;
        }
        else {
            int itemobj = item.getId();
            if (pc.getHierarchs() != null) {
                if (pc.getHierarchs().getItemObjId() == itemobj) {
                    pc.getHierarchs().deleteHierarch();
                    return;
                }
                else {
                    pc.getHierarchs().deleteHierarch();
                }
            }
            if (pc.getInventory().consumeItem(this._itemid, (long)this._count)) {
                L1Npc npcTemp = NpcTable.get().getTemplate(this._npcid);
                L1HierarchInstance Hierarch = new L1HierarchInstance(npcTemp, pc, itemobj);
                Hierarch.setCurrentMp(0);
                Hierarch.onTalkAction(pc);
            }
            else {
                pc.sendPackets(new S_ServerMessage(337, "$5240"));
            }
            return;
        }
    }
    
    @Override
    public void set_set(String[] set) {
        try {
            this._npcid = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
        try {
            this._itemid = Integer.parseInt(set[2]);
        }
        catch (Exception ex2) {}
        try {
            this._count = Integer.parseInt(set[3]);
        }
        catch (Exception ex3) {}
    }
}
