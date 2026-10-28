package com.lineage.data.item_etcitem.wand;

import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Npc_Removal_Wand extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Npc_Removal_Wand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int spellsc_objid = data[0];
        this.useNpcRemovalWand(pc, spellsc_objid, item);
    }
    
    private void useNpcRemovalWand(L1PcInstance pc, int targetId, L1ItemInstance item) {
        L1Object target = World.get().findObject(targetId);
        if (target == null) {
            return;
        }
        else {
            pc.sendPacketsX8(new S_DoActionGFX(pc.getId(), 17));
            int newchargecount = item.getChargeCount() - 1;
            item.setChargeCount(newchargecount);
            pc.getInventory().updateItem(item, 128);
            if (newchargecount <= 0) {
                pc.getInventory().deleteItem(item);
            }
            if (target != null && (target instanceof L1MonsterInstance || target instanceof L1NpcInstance)) {
                L1NpcInstance tgnpc = (L1NpcInstance)target;
                tgnpc.deleteMe();
            }
            return;
        }
    }
}
