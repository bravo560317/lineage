package com.lineage.data.item_etcitem;

import java.util.Iterator;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1TowerInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Scroll_Resurrection extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Scroll_Resurrection();
    }
    
    @Override
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        int targObjId = data[0];
        L1Character target = (L1Character)World.get().findObject(targObjId);
        if (target == null) {
            return;
        }
        else if (target.getId() == pc.getId()) {
            return;
        }
        else if (target.getCurrentHp() > 0 && !target.isDead()) {
            return;
        }
        else {
            pc.getInventory().removeItem(item, 1L);
            if (target.isDead()) {
                if (target instanceof L1PcInstance) {
                    L1PcInstance targetPc = (L1PcInstance)target;
                    if (World.get().getVisiblePlayer(targetPc, 0).size() > 0) {
                        for (L1PcInstance visiblePc : World.get().getVisiblePlayer(targetPc, 0)) {
                            if (!visiblePc.isDead()) {
                                pc.sendPackets(new S_ServerMessage(592));
                                return;
                            }
                        }
                    }
                    if (pc.getMap().isUseResurrection()) {
                        targetPc.setTempID(pc.getId());
                        if (item.getItem().getBless() != 0) {
                            targetPc.sendPackets(new S_Message_YN(321));
                        }
                        else {
                            targetPc.sendPackets(new S_Message_YN(322));
                        }
                    }
                }
                else if (target instanceof L1NpcInstance && !(target instanceof L1TowerInstance)) {
                    L1NpcInstance npc = (L1NpcInstance)target;
                    if (npc.getNpcTemplate().isCantResurrect()) {
                        return;
                    }
                    else {
                        if (npc instanceof L1PetInstance && World.get().getVisiblePlayer(npc, 0).size() > 0) {
                            for (L1PcInstance visiblePc : World.get().getVisiblePlayer(npc, 0)) {
                                if (!visiblePc.isDead()) {
                                    pc.sendPackets(new S_ServerMessage(592));
                                    return;
                                }
                            }
                        }
                        npc.resurrect(npc.getMaxHp() / 4);
                        npc.setResurrect(true);
                        npc.setDead(false);
                    }
                }
            }
            return;
        }
    }
}
