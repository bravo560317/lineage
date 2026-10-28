package com.lineage.data.item_etcitem.wand;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Purification_Magic_Wand extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Purification_Magic_Wand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item.getChargeCount() == 0) {
            pc.getInventory().deleteItem(item);
            return;
        }
        else if (pc.getMapId() != 1936) {
            L1ItemInstance[] itemlist = pc.getInventory().findItemsId(92142);
            for (int i = 0; i < itemlist.length; ++i) {
                pc.getInventory().removeItem(itemlist[i]);
            }
            itemlist = pc.getInventory().findItemsId(92143);
            for (int i = 0; i < itemlist.length; ++i) {
                pc.getInventory().removeItem(itemlist[i]);
            }
            return;
        }
        else {
            pc.sendPacketsX8(new S_DoActionGFX(pc.getId(), 17));
            int useCount = item.getChargeCount() - 1;
            if (useCount > 0) {
                item.setChargeCount(useCount);
                pc.getInventory().updateItem(item, 128);
            }
            else {
                pc.getInventory().deleteItem(item);
            }
            ArrayList<L1Object> list = World.get().getVisibleObjects(pc, 5);
            if (list == null) {
                return;
            }
            else {
                for (L1Object object : list) {
                    if (object instanceof L1MonsterInstance) {
                        L1MonsterInstance dota = (L1MonsterInstance)object;
                        if ((dota.getNpcId() >= 95131 && dota.getNpcId() <= 95143) || dota.getNpcId() == 95145) {
                            dota.broadcastPacketAll(new S_DoActionGFX(dota.getId(), 2));
                            dota.receiveDamage(pc, 700);
                        }
                        else {
                            continue;
                        }
                    }
                }
                pc.sendPacketsAll(new S_SkillSound(pc.getId(), 3934));
                return;
            }
        }
    }
}
