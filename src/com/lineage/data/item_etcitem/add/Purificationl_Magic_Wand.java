package com.lineage.data.item_etcitem.add;

import java.util.Iterator;
import java.util.ArrayList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Purificationl_Magic_Wand extends ItemExecutor
{
    private Purificationl_Magic_Wand() {
    }
    
    public static ItemExecutor get() {
        return new Purificationl_Magic_Wand();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        if (item == null) {
            return;
        }
        else if (pc == null) {
            return;
        }
        else {
            if (pc.getMapId() >= 4001 && pc.getMapId() <= 4050) {
                if (item.getChargeCount() == 0) {
                    pc.getInventory().onRemoveItem(item);
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
                        pc.getInventory().onRemoveItem(item);
                    }
                    ArrayList<L1Object> list = World.get().getVisibleObjects(pc, 5);
                    if (list == null) {
                        return;
                    }
                    else {
                        for (L1Object object : list) {
                            if (object instanceof L1MonsterInstance) {
                                L1MonsterInstance dota = (L1MonsterInstance)object;
                                dota.broadcastPacketAll(new S_DoActionGFX(dota.getId(), 2));
                                dota.receiveDamage(pc, 250);
                            }
                        }
                        pc.sendPacketsAll(new S_SkillSound(pc.getId(), 12256));
                    }
                }
            }
            else {
                pc.sendPackets(new S_SystemMessage("只能在屍魂塔副本使用。"));
            }
            return;
        }
    }
}
